package mystore.exceptions;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityExistsException;

import static mystore.exceptions.ApiErrorResponse.ApiErrorResponseBuilder;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public final ApiErrorResponse handleUserNotFoundException(Exception exception, WebRequest request) {
        ApiErrorResponse errorResponse = ApiErrorResponseBuilder.anApiErrorResponse()
                .withStatus(FORBIDDEN)
                .withMessage(exception.getLocalizedMessage())
                .withDetail(exception.getMessage())
                .build();
        return errorResponse;
    }

    @ExceptionHandler({IllegalArgumentException.class, EntityExistsException.class})
    public final ApiErrorResponse handleIllegalArgumentException(Exception exception, WebRequest request) {
        ApiErrorResponse errorResponse = ApiErrorResponseBuilder.anApiErrorResponse()
                .withStatus(BAD_REQUEST)
                .withMessage(exception.getLocalizedMessage())
                .withDetail(exception.getMessage())
                .build();
        return errorResponse;
    }
}