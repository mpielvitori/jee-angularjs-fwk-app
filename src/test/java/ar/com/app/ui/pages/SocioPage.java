/**
 *
 */
package ar.com.app.ui.pages;

import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import java.net.URL;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * @author MT31181098
 *
 */
@Location("#/Socios")
public class SocioPage {

	@Drone
    private WebDriver browser;

	@ArquillianResource
	private URL deploymentUrl;

	@FindBy(id="Create")
	private WebElement createButton;

	@FindBy(id="Search")
	private WebElement searchButton;

	@FindBy(id="SocioSearch:nombre")
	private WebElement nombre;

	@FindBy(id="SocioSearch:tipo")
	private WebElement tipo;

	@FindBy(id="SocioSearch:plan")
	private WebElement plan;

	@FindBy(id="SocioSearch:filial")
	private WebElement filial;

	@FindBy(id="SocioSearch:documento")
	private WebElement documento;

	@FindBy(css=".alert-success")
	private WebElement successMessage;

	/**
	 * @return the createButton
	 */
	public WebElement getCreateButton() {
		return createButton;
	}

	/**
	 * @return the searchButton
	 */
	public WebElement getSearchButton() {
		return searchButton;
	}

	/**
	 * @return the nombre
	 */
	public WebElement getNombre() {
		return nombre;
	}

	/**
	 * @return the tipo
	 */
	public WebElement getTipo() {
		return tipo;
	}

	/**
	 * @return the plan
	 */
	public WebElement getPlan() {
		return plan;
	}

	/**
	 * @return the filial
	 */
	public WebElement getFilial() {
		return filial;
	}

	/**
	 * @return the documento
	 */
	public WebElement getDocumento() {
		return documento;
	}

	public void createSocio() {
		guardAjax(createButton).click();
	}

	public void searchSocioByCriteria(String nombre, String tipo, String plan, String filial, String documento) {
		if (nombre != null) {
			this.nombre.sendKeys(nombre);
		}
		if (tipo != null) {
			this.tipo.sendKeys(tipo);
		}
		if (plan != null) {
			this.plan.sendKeys(plan);
		}
		if (filial != null) {
			this.filial.sendKeys(filial);
		}
		if (documento != null) {
			this.documento.sendKeys(documento);
		}
		guardAjax(searchButton).click();
	}

	public boolean isVisible() {
		return this.browser.getCurrentUrl().startsWith(this.deploymentUrl.toString() + "#/Socios");
	}

	public String getSuccessMessage() {
		if (this.successMessage != null) {
			return this.successMessage.getText();
		}
		return null;
	}

}
