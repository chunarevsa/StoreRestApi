package com.chunarevsa.website.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;


@Component
public class JwtAuthenricationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger logger = LogManager.getLogger(JwtAuthenricationEntryPoint.class);

	private final HandlerExceptionResolver resolver;

	@Autowired
	public JwtAuthenricationEntryPoint(@Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
		this.resolver = resolver;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse httpServletResponse, 
						AuthenticationException ex) throws IOException, ServletException {
		
		logger.error("Пользователь не авторизован");
		
		if (request.getAttribute("javax.servlet.error.exception") != null) {
			Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
			resolver.resolveException(request, httpServletResponse, null, (Exception) throwable);
		}
		if (!httpServletResponse.isCommitted()) {
			httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		}
	}
}
