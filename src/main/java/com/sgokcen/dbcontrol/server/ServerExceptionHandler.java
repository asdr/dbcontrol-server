package com.sgokcen.dbcontrol.server;

import java.util.Date;

import com.sgokcen.dbcontrol.server.controller.model.res.ExceptionResponseModel;
import com.sgokcen.dbcontrol.server.exception.ResourceAlreadyExistsException;
import com.sgokcen.dbcontrol.server.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sgokcen.dbcontrol.server.security.auth.InvalidAuthTokenException;

@ControllerAdvice
public class ServerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<ExceptionResponseModel> handleAuthorizationException(Exception ex, WebRequest request) {
        LOGGER.info("Access denied on the request: {}", request);
        LOGGER.info("Exception thrown is as the following: ", ex);

        ExceptionResponseModel responseModel = new ExceptionResponseModel(new Date(), ex.getMessage(), "Check log files for the stack trace.");
        return new ResponseEntity<>(responseModel, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler({InvalidAuthTokenException.class})
    public final ResponseEntity<ExceptionResponseModel> handleAuthenticationException(Exception ex, WebRequest request) {
        LOGGER.info("Unauthorized request: {}", request);
        LOGGER.info("Exception thrown is as the following: ", ex);

        ExceptionResponseModel responseModel = new ExceptionResponseModel(new Date(), ex.getMessage(), "Check log files for the stack trace.");
        return new ResponseEntity<>(responseModel, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler({ResourceNotFoundException.class})
    public final ResponseEntity<ExceptionResponseModel> handleResourceNotFound(Exception ex, WebRequest request) {
        LOGGER.info("Resource is missing: {}", ex);
        
        ExceptionResponseModel responseModel = new ExceptionResponseModel(new Date(), "Resource Not Found", "Check log files for the stack trace.");
        return new ResponseEntity<>(responseModel, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler({ResourceAlreadyExistsException.class})
    public final ResponseEntity<ExceptionResponseModel> handleResourceAlreadyExists(Exception ex, WebRequest request) {
        LOGGER.info("Resource already exists: {}", ex);
        
        ExceptionResponseModel responseModel = new ExceptionResponseModel(new Date(), "Resource already exists", "Check log files for the stack trace.");
        return new ResponseEntity<>(responseModel, HttpStatus.NOT_FOUND);
    }


}
