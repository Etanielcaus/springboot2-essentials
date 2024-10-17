package academy.devdojo.spring_boot2.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BadRequestExceptionDetail {
    private String title;
    private LocalDateTime timeStamp;
    private String message;
    private String details;
    private int status;
}
