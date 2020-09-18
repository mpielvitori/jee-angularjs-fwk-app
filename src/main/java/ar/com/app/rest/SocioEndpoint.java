package ar.com.app.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.com.app.model.Socio;
import ar.com.app.ws.AboutServiceEndpoint;
import ar.com.app.ws.SocioServiceEndpoint;

/**
 *
 */
@Stateless
@Path("/socios")
public class SocioEndpoint {

	@PersistenceContext(unitName = "appbackend-persistence-unit")
	private EntityManager em;

	private final static Logger logger = LoggerFactory.getLogger(SocioEndpoint.class);

	@Inject
	private AboutServiceEndpoint aboutServicePort;

	@Inject
	private SocioServiceEndpoint socioServicePort;

	@POST
	@Consumes("application/json")
	public Response create(@Valid Socio entity) {
		getEntityManager().persist(entity);
		return Response.created(
				UriBuilder.fromResource(SocioEndpoint.class)
						.path(String.valueOf(entity.getId())).build()).build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Socio entity = getEntityManager().find(Socio.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		getEntityManager().remove(entity);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		Socio entity = getEntityManager().find(Socio.class, id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(entity).build();
	}

	@GET
	@Produces("application/json")
	public Response listAll(@QueryParam("start") Integer startPosition,
			@QueryParam("max") Integer maxResult) {

		final List<Socio> results = listSocios(startPosition, maxResult);

		return Response.ok(results).build();
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, Socio entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!id.equals(entity.getId())) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		if (getEntityManager().find(Socio.class, id) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		try {
			entity = getEntityManager().merge(entity);
		} catch (OptimisticLockException e) {
			return Response.status(Response.Status.CONFLICT)
					.entity(e.getEntity()).build();
		}

		return Response.noContent().build();
	}


	/*
	 * Search by NamedQuery
	 */
	@GET
	@Path("/{filtro}/{param}")
	@Produces("application/json")
	public Response findByParam(@PathParam("filtro") String filtro, @PathParam("param") String parametro) {
		switch (filtro) {
		case Socio.FILTRO_DOCUMENTO:
			return Response.ok(findSocioByDocumento(parametro)).build();
		default:
			return Response.status(Status.NOT_IMPLEMENTED).build();
		}
	}

	/*
	 * SOAP Client example
	 */
	@GET
	@Path("/aboutService")
	@Produces("application/raw")
	public Response aboutService() {
		logger.info("execute About WS");
		return Response.ok(aboutServicePort.getSocioInfo()).build();
	}

	/*
	 * SOAP Client example 2
	 */
	@GET
	@Path("/socioService")
	@Produces("application/raw")
	public Response socioService() {
		logger.info("execute Socio WS");
		return Response.ok(socioServicePort.getSocioInfo()).build();
	}

	private List<Socio> findSocioByDocumento(String documento){
		return getEntityManager().createNamedQuery(Socio.QUERY_BY_DOCUMENTO, Socio.class)
	            .setParameter(Socio.QUERY_BY_DOCUMENTO_PARAM, documento)
	            .getResultList();
	}

	/*
	 * Search by JPA Criteria
	 */
	private List<Socio> listSocios(Integer startPosition, Integer maxResult){
		logger.info("List all socios by JPA");
		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Socio> listCriteria = builder.createQuery(Socio.class);
        Root<Socio> listRoot = listCriteria.from(Socio.class);
        listCriteria.select(listRoot);
        TypedQuery<Socio> query = getEntityManager().createQuery(listCriteria);

		if (startPosition != null) {
			query.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			query.setMaxResults(maxResult);
		}

        return query.getResultList();
	}

	private EntityManager getEntityManager() {
		return getEm();
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
