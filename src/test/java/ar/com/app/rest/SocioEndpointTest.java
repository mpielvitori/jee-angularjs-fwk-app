/**
 *
 */
package ar.com.app.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import ar.com.app.model.Socio;

/**
 * @author MT31181098
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SocioEndpointTest {

	@Test
	public void testCreateOk() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();

		//When
		Response create = endpoint.create(new Socio());

		//Then
		assertEquals(Response.Status.CREATED.getStatusCode(), create.getStatus());
	}

	@Test(expected=ValidationException.class)
	public void testCreateError() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();
		doThrow(ValidationException.class).when(em).persist(any(Socio.class));

		//When
		endpoint.create(new Socio());

		//Then ValidationException
	}

	@Test
	public void testDeleteByIdOk() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();
		Socio socio = new Socio();
		doReturn(socio).when(em).find(Socio.class, 1l);

		//When
		Response res = endpoint.deleteById(1l);

		//Then
		verify(em).remove(any(Socio.class));
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), res.getStatus());
	}

	@Test
	public void testDeleteByIdNotFound() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		//When
		Response res = endpoint.deleteById(1l);

		//Then
		verify(em).find(Socio.class, 1l);
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), res.getStatus());
	}

	@Test
	public void testFindByIdOk() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();
		Socio socio = new Socio();
		doReturn(socio).when(em).find(Socio.class, 1l);

		//When
		Response res = endpoint.findById(1l);

		//Then
		verify(em).find(Socio.class, 1l);
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	public void testFindByIdNotFound() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		//When
		Response res = endpoint.findById(1l);

		//Then
		verify(em).find(Socio.class, 1l);
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), res.getStatus());
	}

	@Test
	public void testUpdateOk() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		Socio socio = new Socio();
		socio.setId(1l);

		doReturn(socio).when(em).find(Socio.class, 1l);

		//When
		Response res = endpoint.update(socio.getId(), socio);

		//Then
		verify(em).find(Socio.class, 1l);
		verify(em).merge(socio);
		verifyZeroInteractions(em);
		assertEquals(Response.Status.NO_CONTENT.getStatusCode(), res.getStatus());
	}

	@Test
	public void testUpdateConflict() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		Socio socio = new Socio();
		socio.setId(1l);

		//When
		Response res = endpoint.update(2l, socio);

		//Then
		verifyZeroInteractions(em);
		assertEquals(Response.Status.CONFLICT.getStatusCode(), res.getStatus());
	}

	@Test
	public void testUpdateBasRequestSocioNull() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		//When
		Response res = endpoint.update(1l, null);

		//Then
		verifyZeroInteractions(em);
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
	}

	@Test
	public void testUpdateBadRequestIdNull() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		Socio socio = new Socio();
		socio.setId(1l);

		//When
		Response res = endpoint.update(null, socio);

		//Then
		verifyZeroInteractions(em);
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), res.getStatus());
	}

	@Test
	public void testUpdateConflictLockException() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		Socio socio = new Socio();
		socio.setId(1l);

		doReturn(socio).when(em).find(Socio.class, 1l);
		doThrow(OptimisticLockException.class).when(em).merge(socio);

		//When
		Response res = endpoint.update(socio.getId(), socio);

		//Then
		verify(em).find(Socio.class, 1l);
		verify(em).merge(socio);
		verifyZeroInteractions(em);
		assertEquals(Response.Status.CONFLICT.getStatusCode(), res.getStatus());
	}

	@Test
	public void testUpdateNotFound() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		Socio socio = new Socio();
		socio.setId(1l);

		doReturn(null).when(em).find(Socio.class, 1l);

		//When
		Response res = endpoint.update(socio.getId(), socio);

		//Then
		verify(em).find(Socio.class, 1l);
		verifyZeroInteractions(em);
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), res.getStatus());
	}

	@Test
	public void testFindByParamOk() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		@SuppressWarnings("unchecked")
		TypedQuery<Socio> query = mock(TypedQuery.class);
		doReturn(query).when(em).createNamedQuery(Socio.QUERY_BY_DOCUMENTO, Socio.class);

		when(query.setParameter(Socio.QUERY_BY_DOCUMENTO_PARAM, "11.111.111")).thenAnswer(RETURNS_SELF);
		when(query.getResultList()).thenReturn(new ArrayList<Socio>());

		//When
		Response res = endpoint.findByParam("documento", "11.111.111");

		//Then
		verify(em).createNamedQuery(Socio.QUERY_BY_DOCUMENTO, Socio.class);
		verifyZeroInteractions(em);
		verify(query).setParameter(Socio.QUERY_BY_DOCUMENTO_PARAM, "11.111.111");
		verify(query).getResultList();
		verifyZeroInteractions(query);

		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	public void testFindByParamNotImplemented() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		//When
		Response res = endpoint.findByParam("nombre", "pepe");

		//Then
		verifyZeroInteractions(em);
		assertEquals(Response.Status.NOT_IMPLEMENTED.getStatusCode(), res.getStatus());
	}

	@Test(expected=NullPointerException.class)
	public void testFindByParamNullPointer() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();

		//When
		endpoint.findByParam(null, "11.111.111");

		//Then NullPointerException
	}

	@Test
	public void testListAllOk() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
		@SuppressWarnings("unchecked")
		CriteriaQuery<Socio> criteriaQuery = mock(CriteriaQuery.class);
		@SuppressWarnings("unchecked")
		Root<Socio> rootList = mock(Root.class);
		@SuppressWarnings("unchecked")
		TypedQuery<Socio> query = mock(TypedQuery.class);

		doReturn(criteriaBuilder).when(em).getCriteriaBuilder();
		doReturn(criteriaQuery).when(criteriaBuilder).createQuery(Socio.class);
		doReturn(rootList).when(criteriaQuery).from(Socio.class);
		doReturn(query).when(em).createQuery(criteriaQuery);
		when(query.setFirstResult(0)).then(RETURNS_SELF);
		when(query.setMaxResults(20)).then(RETURNS_SELF);
		doReturn(new ArrayList<Socio>()).when(query).getResultList();

		//When
		Response res = endpoint.listAll(0, 20);

		//Then
		verify(em).getCriteriaBuilder();
		verify(criteriaBuilder).createQuery(Socio.class);
		verify(criteriaQuery).from(Socio.class);
		verify(criteriaQuery).select(rootList);
		verify(em).createQuery(criteriaQuery);
		verify(query).setFirstResult(0);
		verify(query).setMaxResults(20);
		verify(query).getResultList();
		verifyZeroInteractions(em);
		verifyZeroInteractions(criteriaBuilder);
		verifyZeroInteractions(criteriaQuery);
		verifyZeroInteractions(query);
		verifyZeroInteractions(rootList);
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	public void testListAllOkMaxResultNull() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
		@SuppressWarnings("unchecked")
		CriteriaQuery<Socio> criteriaQuery = mock(CriteriaQuery.class);
		@SuppressWarnings("unchecked")
		Root<Socio> rootList = mock(Root.class);
		@SuppressWarnings("unchecked")
		TypedQuery<Socio> query = mock(TypedQuery.class);

		doReturn(criteriaBuilder).when(em).getCriteriaBuilder();
		doReturn(criteriaQuery).when(criteriaBuilder).createQuery(Socio.class);
		doReturn(rootList).when(criteriaQuery).from(Socio.class);
		doReturn(query).when(em).createQuery(criteriaQuery);
		when(query.setFirstResult(0)).then(RETURNS_SELF);
		doReturn(new ArrayList<Socio>()).when(query).getResultList();

		//When
		Response res = endpoint.listAll(0, null);

		//Then
		verify(em).getCriteriaBuilder();
		verify(criteriaBuilder).createQuery(Socio.class);
		verify(criteriaQuery).from(Socio.class);
		verify(criteriaQuery).select(rootList);
		verify(em).createQuery(criteriaQuery);
		verify(query).setFirstResult(0);
		verify(query).getResultList();
		verifyZeroInteractions(em);
		verifyZeroInteractions(criteriaBuilder);
		verifyZeroInteractions(criteriaQuery);
		verifyZeroInteractions(query);
		verifyZeroInteractions(rootList);
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	public void testListAllOkFirstResultNull() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
		@SuppressWarnings("unchecked")
		CriteriaQuery<Socio> criteriaQuery = mock(CriteriaQuery.class);
		@SuppressWarnings("unchecked")
		Root<Socio> rootList = mock(Root.class);
		@SuppressWarnings("unchecked")
		TypedQuery<Socio> query = mock(TypedQuery.class);

		doReturn(criteriaBuilder).when(em).getCriteriaBuilder();
		doReturn(criteriaQuery).when(criteriaBuilder).createQuery(Socio.class);
		doReturn(rootList).when(criteriaQuery).from(Socio.class);
		doReturn(query).when(em).createQuery(criteriaQuery);
		when(query.setMaxResults(20)).then(RETURNS_SELF);
		doReturn(new ArrayList<Socio>()).when(query).getResultList();

		//When
		Response res = endpoint.listAll(null, 20);

		//Then
		verify(em).getCriteriaBuilder();
		verify(criteriaBuilder).createQuery(Socio.class);
		verify(criteriaQuery).from(Socio.class);
		verify(criteriaQuery).select(rootList);
		verify(em).createQuery(criteriaQuery);
		verify(query).setMaxResults(20);
		verify(query).getResultList();
		verifyZeroInteractions(em);
		verifyZeroInteractions(criteriaBuilder);
		verifyZeroInteractions(criteriaQuery);
		verifyZeroInteractions(query);
		verifyZeroInteractions(rootList);
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	@Test
	public void testListAllOkFirstResultAndMaxResultsNull() {
		//Given
		SocioEndpoint endpoint = createSocioEndpointMock();
		EntityManager em = endpoint.getEm();

		CriteriaBuilder criteriaBuilder = mock(CriteriaBuilder.class);
		@SuppressWarnings("unchecked")
		CriteriaQuery<Socio> criteriaQuery = mock(CriteriaQuery.class);
		@SuppressWarnings("unchecked")
		Root<Socio> rootList = mock(Root.class);
		@SuppressWarnings("unchecked")
		TypedQuery<Socio> query = mock(TypedQuery.class);

		doReturn(criteriaBuilder).when(em).getCriteriaBuilder();
		doReturn(criteriaQuery).when(criteriaBuilder).createQuery(Socio.class);
		doReturn(rootList).when(criteriaQuery).from(Socio.class);
		doReturn(query).when(em).createQuery(criteriaQuery);
		doReturn(new ArrayList<Socio>()).when(query).getResultList();

		//When
		Response res = endpoint.listAll(null, null);

		//Then
		verify(em).getCriteriaBuilder();
		verify(criteriaBuilder).createQuery(Socio.class);
		verify(criteriaQuery).from(Socio.class);
		verify(criteriaQuery).select(rootList);
		verify(em).createQuery(criteriaQuery);
		verify(query).getResultList();
		verifyZeroInteractions(em);
		verifyZeroInteractions(criteriaBuilder);
		verifyZeroInteractions(criteriaQuery);
		verifyZeroInteractions(query);
		verifyZeroInteractions(rootList);
		assertEquals(Response.Status.OK.getStatusCode(), res.getStatus());
	}

	private static SocioEndpoint createSocioEndpointMock() {
		SocioEndpoint endpoint = new SocioEndpoint();
		endpoint.setEm(mock(EntityManager.class));

		return endpoint;
	}

}
