package ar.com.app.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.servlet.annotation.WebServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebService(name="SocioServiceEndpoint", portName="SocioServiceEndpointPort")
@WebServlet(urlPatterns="webservices/SocioServiceEndpoint")
public class SocioService {

	private final static Logger logger = LoggerFactory.getLogger(SocioService.class);

	@WebMethod(operationName = "getSocioInfo")
    public String getInfo() {
		logger.info("Llamada al WS getInfo de SocioService");
        return "Info obtenida desde un WebService (SocioService)";
    }

}
