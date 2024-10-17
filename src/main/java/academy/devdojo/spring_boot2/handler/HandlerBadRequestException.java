package academy.devdojo.spring_boot2.handler;

import academy.devdojo.spring_boot2.exception.BadRequestException;
import academy.devdojo.spring_boot2.exception.BadRequestExceptionDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class HandlerBadRequestException {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetail> brd(BadRequestException brddd){
        return new ResponseEntity<>(BadRequestExceptionDetail.builder().title("Not Find Anime")
                .message("Not find the anime, check documentatio again")
                .timeStamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value())
                .details(brddd.getMessage())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
