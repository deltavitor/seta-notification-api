package edu.costavitor.setanotificationapi.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@AllArgsConstructor
public class ExceptionWrapper {

    private HttpStatus status;

    private String message;
}
