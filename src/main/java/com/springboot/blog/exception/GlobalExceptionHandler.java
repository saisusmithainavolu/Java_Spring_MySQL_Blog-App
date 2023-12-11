package com.springboot.blog.exception;

import com.springboot.blog.dto.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler{

    // handle specific exceptions
    @ExceptionHandler(ResourceNotFoundExceptionHandler.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundExceptionHandler exception, WebRequest webrequest){
        ErrorDetails errordetails =
                new ErrorDetails(new Date(),exception.getMessage(), webrequest.getDescription(false));
        return new ResponseEntity<>(errordetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIExceptionHandler.class)
    public ResponseEntity<ErrorDetails> handleCommentNotInPostExceptionException(APIExceptionHandler exception, WebRequest webrequest){
        ErrorDetails errordetails =
                new ErrorDetails(new Date(),exception.getMessage(), webrequest.getDescription(false));
        return new ResponseEntity<>(errordetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException exception, WebRequest webrequest){
        ErrorDetails errordetails =
                new ErrorDetails(new Date(),exception.getMessage(), webrequest.getDescription(false));
        return new ResponseEntity<>(errordetails, HttpStatus.UNAUTHORIZED);
    }
    // handle global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception exception, WebRequest webrequest){
        ErrorDetails errordetails =
                new ErrorDetails(new Date(),exception.getMessage(), webrequest.getDescription(false));
        return new ResponseEntity<>(errordetails, HttpStatus.BAD_REQUEST);
    }


}
