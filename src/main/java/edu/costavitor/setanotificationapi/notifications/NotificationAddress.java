package edu.costavitor.setanotificationapi.notifications;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@Builder
public class NotificationAddress {

    private String logradouro;

    private String numero;

    private String bairro;

    private String municipio;

    private String uf;

    @Override
    public String toString() {
        return Stream.of("RUA", logradouro, numero, bairro, municipio, uf)
                .filter(value -> value != null && !value.isBlank())
                .collect(Collectors.joining(" "));
    }
}
