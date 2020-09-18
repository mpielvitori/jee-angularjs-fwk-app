package ar.com.app.model;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.util.Calendar;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SocioTest {

	@Mock private Socio socio;
	ValidatorFactory factory;
	Validator validator;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@After
	public void cleanUp() {
		factory.close();
	}

    @Test
    public void testEmptySocio() {

        Set<ConstraintViolation<Socio>> validaciones = validator.validate(socio);

        assertEquals("Validaciones incorrectas", 7, validaciones.size());
    }

    @Test
    public void testValidSocio() {
        Set<ConstraintViolation<Socio>> validaciones = validator.validate(crearSocio());

        assertEquals("Validaciones correctas", 0, validaciones.size());
    }

    @Test
    public void testValidacionNombre() {
        Socio socio = crearSocio();
        socio.setNombre("Jose Luis-Diego");

        Set<ConstraintViolation<Socio>> validaciones = validator.validate(socio);

        assertEquals("Validaciones encontradas", 1, validaciones.size());
        assertEquals("Nombre inválido", "Sólo letras y espacios están permitidos.", validaciones.iterator().next()
            .getMessage());
    }

    private Socio crearSocio() {
        Socio socio = new Socio();
        socio.setNombre("Pedro");
        socio.setAlta(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
        socio.setDocumento("12345678");
        socio.setFilial("METROPOLITANA");
        Calendar nacimiento = Calendar.getInstance();
        nacimiento.set(1982, 7, 29);
		socio.setNacimiento(new Date(nacimiento.getTimeInMillis()));
        socio.setPlan("210");
        socio.setTipo("Directo");

        return socio;
    }

}
