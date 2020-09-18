/**
 *
 */
package ar.com.app.ws;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author MT31181098
 *
 */
@RunWith(Arquillian.class)
public class AboutServiceIT {

	@ArquillianResource
	URL deploymentUrl;

	@Deployment
	public static JavaArchive createDeployment() {
		JavaArchive jar = null;
		try {
		jar = ShrinkWrap
				.create(JavaArchive.class)
				.addPackages(true, AboutService.class.getPackage(),
						AboutServiceService.class.getPackage())
				.addAsResource("META-INF/wsdl/AboutServiceEndpoint.wsdl")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
		System.out.println(jar.toString(true));
		} catch(Throwable t) {
			t.printStackTrace();
		}
		return jar;
	}

	/**
	 * Prueba de llamado exitoso a la operación getSocioInfo del servicio
	 * AboutServiceService.
	 *
	 * En la prueba, se despliega el servicio al jboss y se llaman las
	 * operaciones desde un cliente, sin utilizar mocks: se realiza comunicación
	 * por red, marshal y unmarshal de mensajes, aplicación de handlers, etc.
	 *
	 * En esta prueba se crea el port manualmente en lugar de utilizar el
	 * serviceFactory, para ejemplificar como se probarían las operaciones en
	 * una aplicación que expone servicios y no es cliente de los mismos, ya que
	 * no es común que una aplicación se cliente de sus propios servicios como
	 * ocurre en esta aplicación de ejemplo.
	 *
	 * @throws MalformedURLException
	 */
	@Test
	public void testGetInfoService() throws MalformedURLException {
		final QName serviceName = new QName("http://ws.app.com.ar/",
				"AboutServiceService");
		final URL wsdlURL = new URL(deploymentUrl, "webservices/AboutServiceEndpoint?wsdl");
		final Service service = Service.create(wsdlURL, serviceName);
		final AboutServiceEndpoint port = service
				.getPort(AboutServiceEndpoint.class);
		assertEquals("Info obtenida desde un WebService", port.getSocioInfo());
	}

}
