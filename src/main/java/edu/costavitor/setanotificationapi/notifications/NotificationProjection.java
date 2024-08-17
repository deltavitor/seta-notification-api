package edu.costavitor.setanotificationapi.notifications;

import java.sql.Date;

public interface NotificationProjection {

    String getNuNotificacao();

    void setNuNotificacao(String nuNotificacao);

    String getTpNotificacao();

    void setTpNotificacao(String tpNotificacao);

    String getCoCid();

    void setCoCid(String coCid);

    Date getDtNotificacao();

    void setDtNotificacao(Date dtNotificacao);

    String getDsSemanaNotificacao();

    void setDsSemanaNotificacao(String dsSemanaNotificacao);

    String getCoUfNotificacao();

    void setCoUfNotificacao(String coUfNotificacao);

    String getCoMunicipioNotificacao();

    void setCoMunicipioNotificacao(String coMunicipioNotificacao);

    Integer getCoUnidadeNotificacao();

    void setCoUnidadeNotificacao(Integer coUnidadeNotificacao);

    Date getDtDiagnosticoSintoma();

    void setDtDiagnosticoSintoma(Date dtDiagnosticoSintoma);

    String getDsSemanaSintoma();

    void setDsSemanaSintoma(String dsSemanaSintoma);

    String getNoNomePaciente();

    void setNoNomePaciente(String noNomePaciente);

    Date getDtNascimento();

    void setDtNascimento(Date dtNascimento);

    Integer getNuIdade();

    void setNuIdade(Integer nuIdade);

    String getTpSexo();

    void setTpSexo(String tpSexo);

    String getTpGestante();

    void setTpGestante(String tpGestante);

    String getTpRacaCor();

    void setTpRacaCor(String tpRacaCor);

    String getTpEscolaridade();

    void setTpEscolaridade(String tpEscolaridade);

    String getNuCartaoSus();

    void setNuCartaoSus(String nuCartaoSus);

    String getNoNomeMae();

    void setNoNomeMae(String noNomeMae);

    String getCoUfResidencia();

    void setCoUfResidencia(String coUfResidencia);

    String getCoMunicipioResidencia();

    void setCoMunicipioResidencia(String coMunicipioResidencia);

    String getCoDistritoResidencia();

    void setCoDistritoResidencia(String coDistritoResidencia);

    Integer getCoBairroResidencia();

    void setCoBairroResidencia(Integer coBairroResidencia);

    String getNoBairroResidencia();

    void setNoBairroResidencia(String noBairroResidencia);

    Integer getCoLogradouroResidencia();

    void setCoLogradouroResidencia(Integer coLogradouroResidencia);

    String getNoLogradouroResidencia();

    void setNoLogradouroResidencia(String noLogradouroResidencia);

    String getNuResidencia();

    void setNuResidencia(String nuResidencia);

    String getDsComplementoResidencia();

    void setDsComplementoResidencia(String dsComplementoResidencia);

    String getNuCepResidencia();

    void setNuCepResidencia(String nuCepResidencia);

    String getNuDddResidencia();

    void setNuDddResidencia(String nuDddResidencia);

    String getNuTelefoneResidencia();

    void setNuTelefoneResidencia(String nuTelefoneResidencia);

    String getTpZonaResidencia();

    void setTpZonaResidencia(String tpZonaResidencia);

    String getCoPaisResidencia();

    void setCoPaisResidencia(String coPaisResidencia);

    Date getDtInvestigacao();

    void setDtInvestigacao(Date dtInvestigacao);

    String getTpClassificacaoFinal();

    void setTpClassificacaoFinal(String tpClassificacaoFinal);

    String getTpCriterioConfirmacao();

    void setTpCriterioConfirmacao(String tpCriterioConfirmacao);

    String getFebre();

    void setFebre(String febre);

    String getMialgia();

    void setMialgia(String mialgia);

    String getCefaleia();

    void setCefaleia(String cefaleia);

    String getExantema();

    void setExantema(String exantema);

    String getVomito();

    void setVomito(String vomito);

    String getNausea();

    void setNausea(String nausea);

    String getDorCostas();

    void setDorCostas(String dorCostas);

    String getConjuntivite();

    void setConjuntivite(String conjuntivite);

    String getArtrite();

    void setArtrite(String artrite);

    String getArtralgia();

    void setArtralgia(String artralgia);

    String getPetequia();

    void setPetequia(String petequia);

    String getLeucopenia();

    void setLeucopenia(String leucopenia);

    String getLaco();

    void setLaco(String laco);

    String getDorRetro();

    void setDorRetro(String dorRetro);

    String getDiabetes();

    void setDiabetes(String diabetes);

    String getHematologicas();

    void setHematologicas(String hematologicas);

    String getHepatopatias();

    void setHepatopatias(String hepatopatias);

    String getRenal();

    void setRenal(String renal);

    String getHipertensao();

    void setHipertensao(String hipertensao);

    String getAcidoPeptica();

    void setAcidoPeptica(String acidoPeptica);

    String getAutoImune();

    void setAutoImune(String autoImune);

    Date getDtColetaExame();

    void setDtColetaExame(Date dtColetaExame);

    String getTpResultExame();

    void setTpResultExame(String tpResultExame);

    Date getDtColetaNs1();

    void setDtColetaNs1(Date dtColetaNs1);

    String getTpResultNs1();

    void setTpResultNs1(String tpResultNs1);

    Date getDtColetaIsolamento();

    void setDtColetaIsolamento(Date dtColetaIsolamento);

    String getTpResultIsolamento();

    void setTpResultIsolamento(String tpResultIsolamento);

    Date getDtColetaRtpcr();

    void setDtColetaRtpcr(Date dtColetaRtpcr);

    String getTpResultRtpcr();

    void setTpResultRtpcr(String tpResultRtpcr);

    String getTpSorotipo();

    void setTpSorotipo(String tpSorotipo);

    String getTpEvolucaoCaso();

    void setTpEvolucaoCaso(String tpEvolucaoCaso);

    Date getDtObito();

    void setDtObito(Date dtObito);

    Date getDtEncerramento();

    void setDtEncerramento(Date dtEncerramento);

    String getDsObservacao();

    void setDsObservacao(String dsObservacao);

    Double getLatitude();

    void setLatitude(Double latitude);

    Double getLongitude();

    void setLongitude(Double longitude);
}
