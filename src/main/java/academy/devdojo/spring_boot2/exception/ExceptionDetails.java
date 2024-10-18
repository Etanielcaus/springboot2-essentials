package academy.devdojo.spring_boot2.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails{
    protected String title;
    protected LocalDateTime timeStamp;
    protected String message;
    protected String details;
    protected int status;
}
