package ar.com.app.ws;

import javax.ejb.Singleton;
import javax.enterprise.inject.Produces;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceRef;

import org.apache.deltaspike.core.api.config.ConfigResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ServiceFactory {

	private final static Logger logger = LoggerFactory.getLogger(ServiceFactory.class);

	public static final String ID_NAMESPACE = "service.framework.app.com.ar";
	public static final String FIELD_USER_NAME = "userName";
	public static final String FIELD_APP_NAME = "appName";
	public static final String FIELD_CREDENTIALS = "credentials";
	public static final String FIELD_TRACE_ID = "traceId";

	public static final String SECURITY_SUBJECT = "javax.security.auth.Subject.container";

	@WebServiceRef(AboutServiceService.class)
	private AboutServiceEndpoint aboutService;

	@Produces
	public AboutServiceEndpoint createAboutService() {
		String endpoint = ConfigResolver.getPropertyValue("about.service.endpoint");
		logger.info("Creando cliente para servicio: "+endpoint);
		((BindingProvider) aboutService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
		return aboutService;
	}

	@WebServiceRef(SocioServiceService.class)
	private SocioServiceEndpoint socioService;

	@Produces
	public SocioServiceEndpoint createSocioService() {
		String endpoint = ConfigResolver.getPropertyValue("socio.service.endpoint");
		logger.info("Creando cliente para servicio: "+endpoint);
		((BindingProvider) socioService).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
		return socioService;
	}
}
