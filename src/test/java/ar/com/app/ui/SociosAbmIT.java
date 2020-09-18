/**
 *
 */
package ar.com.app.ui;

import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import static org.hamcrest.CoreMatchers.is;

import ar.com.app.ui.pages.SocioNewPage;
import ar.com.app.ui.pages.SocioPage;

/**
 * @author MT31181098
 *
 */
@RunWith(Arquillian.class)
public class SociosAbmIT {

	@Drone
	private WebDriver browser;

	@ArquillianResource
	private URL deploymentUrl;

	@Deployment(testable=false)
	public static WebArchive createDeployment() {
		WebArchive jar = ShrinkWrap.createFromZipFile(WebArchive.class, new File("target/App.war"));
		System.out.println(jar.toString(true));
		return jar;
	}

	@Test
	@RunAsClient
	public void testBotonCrearSocioOK(@InitialPage SocioPage socioPage, @Page SocioNewPage socioNewPage) {
		Assume.assumeThat(System.getProperty("run.ui.tests"), is("true"));
		//Given
	    assumeTrue(socioPage.isVisible());

	    //When
	    socioPage.createSocio();

	    //Then
	    assertTrue(socioNewPage.isVisible());
	}

	@Test
	@RunAsClient
	public void testSaveNewSocioOK(@InitialPage SocioNewPage socioNewPage, @Page SocioPage socioPage) {
		Assume.assumeThat(System.getProperty("run.ui.tests"), is("true"));
		//Given
	    assumeTrue(socioNewPage.isVisible());

	    //When
	    socioNewPage
	    	.nombre("un nombre")
	    	.nacimiento("01-01-1990")
	    	.documento("11111111")
	    	.tipo("Directo")
	    	.plan("210")
	    	.filial("Metropolitana")
	    	.alta("01-01-1990")
	    	.baja("31-12-2050")
	    	.saveSocio();

	    //Then
	    assertTrue(socioPage.isVisible());
	    assertTrue(socioPage.getSuccessMessage().contains("El socio ha sido creado exitosamente."));
	}

}
