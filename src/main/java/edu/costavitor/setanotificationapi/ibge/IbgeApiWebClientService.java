package edu.costavitor.setanotificationapi.ibge;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class IbgeApiWebClientService {

    @Qualifier("ibgeApiWebClient")
    private WebClient ibgeApiWebClient;

    // TODO update API calls to not use block()
    public Municipio getMunicipioByCodigoMunicipio(String codigoMunicipio) {

        String completeCodigoMunicipio = codigoMunicipio.length() == 6 ? appendDigitoVerificador(codigoMunicipio) : codigoMunicipio;

        return ibgeApiWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/localidades/municipios/{codigoMunicipio}")
                        .build(completeCodigoMunicipio))
                .retrieve()
                .bodyToMono(Municipio.class)
                .block();
    }

    public UF getUFByCodigoUF(String codigoUF) {

        return ibgeApiWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v1/localidades/estados/{codigoUF}")
                        .build(codigoUF))
                .retrieve()
                .bodyToMono(UF.class)
                .block();
    }

    private String appendDigitoVerificador(String codigoMunicipio) {

        Integer[] digits = new Integer[6];
        for (int i = 0; i < codigoMunicipio.length(); i++)
            digits[i] = Character.getNumericValue(codigoMunicipio.charAt(i));

        digits[1] = (digits[1] * 2) % 10 + (digits[1] * 2) / 10;
        digits[3] = (digits[3] * 2) % 10 + (digits[3] * 2) / 10;
        digits[5] = (digits[5] * 2) % 10 + (digits[5] * 2) / 10;
        Integer sum = Arrays.stream(digits).reduce(0, Integer::sum);
        return codigoMunicipio + (10 - sum % 10) % 10;
    }
}
