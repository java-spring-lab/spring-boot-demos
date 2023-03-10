package demo.springboot.taskmgr.exception;

import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse taskNotFoundException(TaskNotFoundException ex, WebRequest req) {

        return buildError(HttpStatus.NOT_FOUND, req, ex.getMessage());
    }

    @ExceptionHandler(NoteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse taskNotFoundException(NoteNotFoundException ex, WebRequest req) {

        return buildError(HttpStatus.NOT_FOUND, req, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest req) {

        log.error("Uncaught Exception", ex);

        String exMsg = ex.getMessage();
        String message = null;
        if (Objects.nonNull(exMsg)) {
            message = exMsg.substring(0, exMsg.indexOf(":"));
        }
        ErrorResponse respBody = buildError(status, req, message);

        return super.handleExceptionInternal(ex, respBody, headers, status, req);
    }

    private ErrorResponse buildError(HttpStatus status, WebRequest req, String message) {

        ErrorResponse errResp = new ErrorResponse(status.value(), status.getReasonPhrase());
        errResp.setPath(req.getDescription(false));
        errResp.setMessage(message);

        log.error("errResp={}", errResp);
        return errResp;
    }
}