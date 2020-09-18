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
@Location("#/Socios/new")
public class SocioNewPage {

	@Drone
    private WebDriver browser;

	@ArquillianResource
	private URL deploymentUrl;

	@FindBy(id="nombre")
	private WebElement nombre;

	@FindBy(id="nacimiento")
	private WebElement nacimiento;

	@FindBy(id="documento")
	private WebElement documento;

	@FindBy(id="tipo")
	private WebElement tipo;

	@FindBy(id="plan")
	private WebElement plan;

	@FindBy(id="filial")
	private WebElement filial;

	@FindBy(id="alta")
	private WebElement alta;

	@FindBy(id="baja")
	private WebElement baja;

	@FindBy(id="saveSocio")
	private WebElement saveSocioButton;

	@FindBy(id="cancel")
	private WebElement cancelButton;

	@FindBy(id="deleteSocio")
	private WebElement deleteSocioButton;

	/**
	 * @param deploymentUrl the deploymentUrl to set
	 */
	public void setDeploymentUrl(URL deploymentUrl) {
		this.deploymentUrl = deploymentUrl;
	}

	/**
	 * @param nombre
	 * @return
	 */
	public SocioNewPage nombre(String nombre) {
		this.nombre.sendKeys(nombre);
		return this;
	}

	/**
	 * @param nacimiento
	 * @return
	 */
	public SocioNewPage nacimiento(String nacimiento) {
		this.nacimiento.sendKeys(nacimiento);
		return this;
	}

	/**
	 * @param documento
	 * @return
	 */
	public SocioNewPage documento(String documento) {
		this.documento.sendKeys(documento);
		return this;
	}

	/**
	 * @param tipo
	 * @return
	 */
	public SocioNewPage tipo(String tipo) {
		this.tipo.sendKeys(tipo);
		return this;
	}

	/**
	 * @param plan
	 * @return
	 */
	public SocioNewPage plan(String plan) {
		this.plan.sendKeys(plan);
		return this;
	}

	/**
	 * @param filial
	 * @return
	 */
	public SocioNewPage filial(String filial) {
		this.filial.sendKeys(filial);
		return this;
	}

	/**
	 * @param alta
	 * @return
	 */
	public SocioNewPage alta(String alta) {
		this.alta.sendKeys(alta);
		return this;
	}

	/**
	 * @param baja
	 * @return
	 */
	public SocioNewPage baja(String baja) {
		this.baja.sendKeys(baja);
		return this;
	}

	/**
	 * @return the saveSocioButton
	 */
	public WebElement getSaveSocioButton() {
		return saveSocioButton;
	}

	/**
	 * @return the cancelButton
	 */
	public WebElement getCancelButton() {
		return cancelButton;
	}

	/**
	 * @return the deleteSocioButton
	 */
	public WebElement getDeleteSocioButton() {
		return deleteSocioButton;
	}

	/**
	 *
	 */
	public SocioNewPage cancel() {
		guardAjax(this.cancelButton).click();
		return this;
	}

	/**
	 *
	 */
	public SocioNewPage saveSocio() {
		guardAjax(this.saveSocioButton).click();
		return this;
	}

	/**
	 *
	 */
	public SocioNewPage deleteSocio() {
		guardAjax(this.deleteSocioButton).click();
		return this;
	}

	public boolean isVisible() {
		return this.browser.getCurrentUrl().startsWith(this.deploymentUrl.toString() + "#/Socios/new");
	}
}
