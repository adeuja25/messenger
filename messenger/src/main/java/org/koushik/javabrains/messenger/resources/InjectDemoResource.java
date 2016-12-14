package org.koushik.javabrains.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/injectdemo")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectDemoResource {
	
	@GET
	@Path("annotations")
	// to get the values from query instead of ? we use ;
	// HeaderParam mostly used to send metadata about the request
	// CookieParam to see the cookie values
	public String getParamsUsingAnnotation(@MatrixParam("param") String matrixParam,
											@HeaderParam("customHeaderValue") String header,
											@CookieParam("JSESSIONID") String cookie){
		return "Matrix Param: " + matrixParam + "Header Param: " + header + "Cookie " + cookie ;
	}
	
	@GET
	@Path("context")
	// context is used to get uriinfo and httpheaders
	public String getParamsUsingContext(@Context UriInfo uriInfo, @Context HttpHeaders headers){//needs UriInfo to be injected
		String path = uriInfo.getAbsolutePath().toString();
		String cookies = headers.getCookies().toString();
		return "Path " + path + " Cookies " + cookies;
	}

}