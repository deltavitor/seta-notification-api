package edu.costavitor.setanotificationapi.notifications;

import edu.costavitor.setanotificationapi.common.StringToBooleanConverter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = StringToBooleanConverter.class)
public interface NotificationMapper {

    @Mapping(target = "numeroNotificacao", source = "nuNotificacao")
    @Mapping(target = "tipoNotificacao", source = "tpNotificacao")
    @Mapping(target = "codigoAgravo", source = "coCid")
    @Mapping(target = "dataNotificacao", source = "dtNotificacao")
    @Mapping(target = "semanaEpidemiologicaNotificacao", source = "dsSemanaNotificacao")
    @Mapping(target = "codigoUfNotificacao", source = "coUfNotificacao")
    @Mapping(target = "codigoMunicipioNotificacao", source = "coMunicipioNotificacao")
    @Mapping(target = "codigoUnidadeNotificacao", source = "coUnidadeNotificacao")
    @Mapping(target = "dataDiagnosticoSintoma", source = "dtDiagnosticoSintoma")
    @Mapping(target = "semanaDiagnosticoSintoma", source = "dsSemanaSintoma")
    @Mapping(target = "nomePaciente", source = "noNomePaciente")
    @Mapping(target = "dataNascimento", source = "dtNascimento")
    @Mapping(target = "idade", source = "nuIdade")
    @Mapping(target = "sexo", source = "tpSexo")
    @Mapping(target = "idadeGestacional", source = "tpGestante")
    @Mapping(target = "racaCor", source = "tpRacaCor")
    @Mapping(target = "escolaridade", source = "tpEscolaridade")
    @Mapping(target = "numeroCartaoSus", source = "nuCartaoSus")
    @Mapping(target = "nomeMae", source = "noNomeMae")
    @Mapping(target = "codigoUfResidencia", source = "coUfResidencia")
    @Mapping(target = "codigoMunicipioResidencia", source = "coMunicipioResidencia")
    @Mapping(target = "codigoDistritoResidencia", source = "coDistritoResidencia")
    @Mapping(target = "codigoBairroResidencia", source = "coBairroResidencia")
    @Mapping(target = "bairroResidencia", source = "noBairroResidencia")
    @Mapping(target = "codigoLogradouroResidencia", source = "coLogradouroResidencia")
    @Mapping(target = "logradouroResidencia", source = "noLogradouroResidencia")
    @Mapping(target = "numeroResidencia", source = "nuResidencia")
    @Mapping(target = "complementoResidencia", source = "dsComplementoResidencia")
    @Mapping(target = "cepResidencia", source = "nuCepResidencia")
    @Mapping(target = "dddResidencia", source = "nuDddResidencia")
    @Mapping(target = "telefoneResidencia", source = "nuTelefoneResidencia")
    @Mapping(target = "zonaResidencia", source = "tpZonaResidencia")
    @Mapping(target = "codigoPaisResidencia", source = "coPaisResidencia")
    @Mapping(target = "dataInvestigacao", source = "dtInvestigacao")
    @Mapping(target = "classificacaoFinal", source = "tpClassificacaoFinal")
    @Mapping(target = "criterioConfirmacao", source = "tpCriterioConfirmacao")
    @Mapping(target = "febre", expression = "java(StringToBooleanConverter.stringToBoolean(source.getFebre()))")
    @Mapping(target = "mialgia", expression = "java(StringToBooleanConverter.stringToBoolean(source.getMialgia()))")
    @Mapping(target = "cefaleia", expression = "java(StringToBooleanConverter.stringToBoolean(source.getCefaleia()))")
    @Mapping(target = "exantema", expression = "java(StringToBooleanConverter.stringToBoolean(source.getExantema()))")
    @Mapping(target = "vomito", expression = "java(StringToBooleanConverter.stringToBoolean(source.getVomito()))")
    @Mapping(target = "nausea", expression = "java(StringToBooleanConverter.stringToBoolean(source.getNausea()))")
    @Mapping(target = "dorCostas", expression = "java(StringToBooleanConverter.stringToBoolean(source.getDorCostas()))")
    @Mapping(target = "conjuntivite", expression = "java(StringToBooleanConverter.stringToBoolean(source.getConjuntivite()))")
    @Mapping(target = "artrite", expression = "java(StringToBooleanConverter.stringToBoolean(source.getArtrite()))")
    @Mapping(target = "artralgia", expression = "java(StringToBooleanConverter.stringToBoolean(source.getArtralgia()))")
    @Mapping(target = "petequia", expression = "java(StringToBooleanConverter.stringToBoolean(source.getPetequia()))")
    @Mapping(target = "leucopenia", expression = "java(StringToBooleanConverter.stringToBoolean(source.getLeucopenia()))")
    @Mapping(target = "laco", expression = "java(StringToBooleanConverter.stringToBoolean(source.getLaco()))")
    @Mapping(target = "dorRetro", expression = "java(StringToBooleanConverter.stringToBoolean(source.getDorRetro()))")
    @Mapping(target = "diabetes", expression = "java(StringToBooleanConverter.stringToBoolean(source.getDiabetes()))")
    @Mapping(target = "hematologicas", expression = "java(StringToBooleanConverter.stringToBoolean(source.getHematologicas()))")
    @Mapping(target = "hepatopatias", expression = "java(StringToBooleanConverter.stringToBoolean(source.getHepatopatias()))")
    @Mapping(target = "renal", expression = "java(StringToBooleanConverter.stringToBoolean(source.getRenal()))")
    @Mapping(target = "hipertensao", expression = "java(StringToBooleanConverter.stringToBoolean(source.getHipertensao()))")
    @Mapping(target = "acidoPeptica", expression = "java(StringToBooleanConverter.stringToBoolean(source.getAcidoPeptica()))")
    @Mapping(target = "autoImune", expression = "java(StringToBooleanConverter.stringToBoolean(source.getAutoImune()))")
    @Mapping(target = "dataColetaExame", source = "dtColetaExame")
    @Mapping(target = "resultadoExame", source = "tpResultExame")
    @Mapping(target = "dataColetaNs1", source = "dtColetaNs1")
    @Mapping(target = "resultadoNs1", source = "tpResultNs1")
    @Mapping(target = "dataColetaIsolamento", source = "dtColetaIsolamento")
    @Mapping(target = "resultadoIsolamento", source = "tpResultIsolamento")
    @Mapping(target = "dataColetaRtpcr", source = "dtColetaRtpcr")
    @Mapping(target = "resultadoRtpcr", source = "tpResultRtpcr")
    @Mapping(target = "sorotipo", source = "tpSorotipo")
    @Mapping(target = "evolucaoCaso", source = "tpEvolucaoCaso")
    @Mapping(target = "dataObito", source = "dtObito")
    @Mapping(target = "dataEncerramento", source = "dtEncerramento")
    @Mapping(target = "observacao", source = "dsObservacao")
    @Mapping(target = "latitude", source = "latitude")
    @Mapping(target = "longitude", source = "longitude")
    Notification mapToNotification(NotificationEntity source);

}
