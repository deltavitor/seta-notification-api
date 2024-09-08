package edu.costavitor.setanotificationapi.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EntityNotFoundException extends RuntimeException {

    private String message;
}
