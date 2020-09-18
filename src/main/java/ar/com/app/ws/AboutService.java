package ar.com.app.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(name="AboutServiceEndpoint", portName="AboutServiceEndpointPort")
@WebServlet(urlPatterns="webservices/AboutServiceEndpoint")
public class AboutService {

	private final static Logger logger = LoggerFactory.getLogger(AboutService.class);

	@WebMethod(operationName = "getSocioInfo")
    public String getInfo() {
		logger.info("Llamada al WS getInfo");
        return "Info obtenida desde un WebService";
    }

}
