package edu.costavitor.setanotificationapi.notifications;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.Date;

@RedisHash
@Getter
@Setter
@Builder
@FieldNameConstants
public class NotificationEntity {

    @Id
    private String nuNotificacao;

    private String tpNotificacao;

    private String coCid;

    private Date dtNotificacao;

    private String dsSemanaNotificacao;

    private String coUfNotificacao;

    private String coMunicipioNotificacao;

    private Integer coUnidadeNotificacao;

    private Date dtDiagnosticoSintoma;

    private String dsSemanaSintoma;

    private String noNomePaciente;

    private Date dtNascimento;

    private Integer nuIdade;

    private String tpSexo;

    private String tpGestante;

    private String tpRacaCor;

    private String tpEscolaridade;

    private String nuCartaoSus;

    private String noNomeMae;

    private String coUfResidencia;

    private String coMunicipioResidencia;

    private String coDistritoResidencia;

    private Integer coBairroResidencia;

    private String noBairroResidencia;

    private Integer coLogradouroResidencia;

    private String noLogradouroResidencia;

    private String nuResidencia;

    private String dsComplementoResidencia;

    private String nuCepResidencia;

    private String nuDddResidencia;

    private String nuTelefoneResidencia;

    private String tpZonaResidencia;

    private String coPaisResidencia;

    private Date dtInvestigacao;

    private String tpClassificacaoFinal;

    private String tpCriterioConfirmacao;

    private String febre;

    private String mialgia;

    private String cefaleia;

    private String exantema;

    private String vomito;

    private String nausea;

    private String dorCostas;

    private String conjuntivite;

    private String artrite;

    private String artralgia;

    private String petequia;

    private String leucopenia;

    private String laco;

    private String dorRetro;

    private String diabetes;

    private String hematologicas;

    private String hepatopatias;

    private String renal;

    private String hipertensao;

    private String acidoPeptica;

    private String autoImune;

    private Date dtColetaExame;

    private String tpResultExame;

    private Date dtColetaNs1;

    private String tpResultNs1;

    private Date dtColetaIsolamento;

    private String tpResultIsolamento;

    private Date dtColetaRtpcr;

    private String tpResultRtpcr;

    private String tpSorotipo;

    private String tpEvolucaoCaso;

    private Date dtObito;

    private Date dtEncerramento;

    private String dsObservacao;

    private Double latitude;

    private Double longitude;

}
