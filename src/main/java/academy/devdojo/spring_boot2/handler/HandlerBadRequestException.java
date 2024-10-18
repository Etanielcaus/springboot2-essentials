package academy.devdojo.spring_boot2.handler;

import academy.devdojo.spring_boot2.exception.BadRequestException;
import academy.devdojo.spring_boot2.exception.BadRequestExceptionDetail;
import academy.devdojo.spring_boot2.exception.ExceptionDetails;
import academy.devdojo.spring_boot2.exception.ValidationExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> methodArgumentNotValidExceptionResponseEntity(
            MethodArgumentNotValidException exception){
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(ValidationExceptionDetails.builder().title("Fields Error")
                .message("Not find the anime, check documentatio again")
                .timeStamp(LocalDateTime.now()).status(HttpStatus.BAD_REQUEST.value())
                .details(exception.getMessage())
                .fields(fields)
                .fieldsMessages(fieldsMessage)
                .build(), HttpStatus.BAD_REQUEST);
    }
}
