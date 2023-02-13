package demo.springboot.rest.api.exception;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.core.style.ToStringCreator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ErrorResponse {

    private final int status;
    private final String error;

    @JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private final LocalDateTime timestamp;

    @JsonInclude(value = Include.NON_EMPTY)
    private String path;

    @JsonInclude(value = Include.NON_EMPTY)
    private String message;

    public ErrorResponse(int status, String error) {
        this.status = status;
        this.error = error;
        this.timestamp = LocalDateTime.now(ZoneOffset.UTC);
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("status", getStatus())
                .append("error", getError())
                .append("path", getPath())
                .append("message", getMessage())
                .append("timestamp", getTimestamp())
                .toString();

    }
}