package edu.costavitor.setanotificationapi.notifications;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Notification {

    private String numeroNotificacao;

    private String tipoNotificacao;

    private String codigoAgravo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataNotificacao;

    private String semanaEpidemiologicaNotificacao;

    private String codigoUfNotificacao;

    private String codigoMunicipioNotificacao;

    private Integer codigoUnidadeNotificacao;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataDiagnosticoSintoma;

    private String semanaDiagnosticoSintoma;

    private String nomePaciente;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    private Integer idade;

    private String sexo;

    private String idadeGestacional;

    private String racaCor;

    private String escolaridade;

    private String numeroCartaoSus;

    private String nomeMae;

    private String codigoUfResidencia;

    private String codigoMunicipioResidencia;

    private String codigoDistritoResidencia;

    private Integer codigoBairroResidencia;

    private String bairroResidencia;

    private Integer codigoLogradouroResidencia;

    private String logradouroResidencia;

    private String numeroResidencia;

    private String complementoResidencia;

    private String cepResidencia;

    private String dddResidencia;

    private String telefoneResidencia;

    private String zonaResidencia;

    private String codigoPaisResidencia;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataInvestigacao;

    private String classificacaoFinal;

    private String criterioConfirmacao;

    private Boolean febre;

    private Boolean mialgia;

    private Boolean cefaleia;

    private Boolean exantema;

    private Boolean vomito;

    private Boolean nausea;

    private Boolean dorCostas;

    private Boolean conjuntivite;

    private Boolean artrite;

    private Boolean artralgia;

    private Boolean petequia;

    private Boolean leucopenia;

    private Boolean laco;

    private Boolean dorRetro;

    private Boolean diabetes;

    private Boolean hematologicas;

    private Boolean hepatopatias;

    private Boolean renal;

    private Boolean hipertensao;

    private Boolean acidoPeptica;

    private Boolean autoImune;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataColetaExame;

    private String resultadoExame;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataColetaNs1;

    private String resultadoNs1;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataColetaIsolamento;

    private String resultadoIsolamento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataColetaRtpcr;

    private String resultadoRtpcr;

    private String sorotipo;

    private String evolucaoCaso;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataObito;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataEncerramento;

    private String observacao;

    private Double latitude;

    private Double longitude;
}
