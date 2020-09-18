package ar.com.app.ws;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AboutServiceTest {

    @InjectMocks
    AboutService service;

	@Test
	public void testGetInfoService() {
		assertEquals("Info obtenida desde un WebService", service.getInfo());
	}

}
