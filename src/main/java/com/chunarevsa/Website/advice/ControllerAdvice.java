package com.chunarevsa.Website.advice;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.chunarevsa.Website.exception.AlredyUseException;
import com.chunarevsa.Website.exception.InvalidAmountFormat;
import com.chunarevsa.Website.exception.InvalidTokenRequestException;
import com.chunarevsa.Website.exception.MailSendException;
import com.chunarevsa.Website.exception.NotEnoughResourcesException;
import com.chunarevsa.Website.exception.ResourceNotFoundException;
import com.chunarevsa.Website.exception.UserLoginException;
import com.chunarevsa.Website.exception.UserLogoutException;
import com.chunarevsa.Website.exception.UserRegistrationException;
import com.chunarevsa.Website.payload.ApiResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// Обработка исключений
// TODO: логирование 
@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {
	
	private static final Logger logger = LogManager.getLogger(ControllerAdvice.class);

	private final MessageSource messageSource;

	@Autowired
	public ControllerAdvice(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(value = AlredyUseException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ApiResponse handleAlredyUseException(AlredyUseException ex, WebRequest request) {
		return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
	}

	@ExceptionHandler(value = InvalidAmountFormat.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResponse handleInvalidAmountFormat(InvalidAmountFormat ex, WebRequest request) {
		return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
	}

	@ExceptionHandler(value = InvalidTokenRequestException.class)
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	public ApiResponse handleInvalidTokenRequestException(InvalidTokenRequestException ex, WebRequest request) {
		return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
	}

	@ExceptionHandler(value = MailSendException.class)
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ResponseBody
	public ApiResponse handleMailSendException(MailSendException ex, WebRequest request) {
		return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
	}

	@ExceptionHandler(value = NotEnoughResourcesException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResponse handleNotEnoughResourcesException(NotEnoughResourcesException ex, WebRequest request) {
		return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
	}

	@ExceptionHandler(value = ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ApiResponse handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
	}

	@ExceptionHandler(value = UserLoginException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public ApiResponse handleUserLoginException(UserLoginException ex, WebRequest request) {
		return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
	}

	@ExceptionHandler(value = UserLogoutException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResponse handleUserLogoutException(UserLogoutException ex, WebRequest request) {
		return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
	}

	@ExceptionHandler(value = UserRegistrationException.class)
	@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
	@ResponseBody
	public ApiResponse handleUserRegistrationException(UserRegistrationException ex, WebRequest request) {
		return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
	}

	@ExceptionHandler(value = UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ApiResponse handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
		return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
	}

	@ExceptionHandler(value = UnsupportedOperationException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ApiResponse handleUnsupportedOperationException(UnsupportedOperationException ex, WebRequest request) {
		return new ApiResponse(false, ex.getMessage(), ex.getClass().getName(), resolvePathFromWebRequest(request));
	}

	private String resolvePathFromWebRequest(WebRequest request) {
		try {
			return ((ServletWebRequest) request).getRequest().getAttribute("javax.servlet.forward.request_uri").toString();
		} catch (Exception ex) {
			return null;
		} 
	} 

	/* @ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ApiResponse processValidationError(MethodArgumentNotValidException ex, WebRequest request) {
		//System.err.println("processValidationError");
		BindingResult result = ex.getBindingResult();
		List<ObjectError> errors = result.getAllErrors();
		System.err.println(errors);
		String data = processAllerrors(errors).stream().collect(Collectors.joining("\n"));
		return new ApiResponse(false, data, ex.getClass().getName(), resolvePathFromWebRequest(request));
  } */

  /**
	* Список ошибок 
   */
	/* private List<String> processAllerrors(List<ObjectError> errors) {
		return errors.stream().map(this::resolveLocalizedErrorMessage).collect(Collectors.toList());
	}
 */
	/**
	 * Создание сообщения ошибки
	 */
	/* private String resolveLocalizedErrorMessage(ObjectError objectError) {
		Locale currentLocale = LocaleContextHolder.getLocale();
		String localizedErrorMessage = messageSource.getMessage(objectError, currentLocale);
		logger.info(localizedErrorMessage);
		return localizedErrorMessage;
	} */

	// UnsupportedOperationException
	// UsernameNotFoundException 

} 