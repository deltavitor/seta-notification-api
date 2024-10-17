package edu.costavitor.setanotificationapi.notifications;

import java.util.Date;

public interface NotificationSummaryProjection {

    String getNuNotificacao();

    Date getDtNotificacao();

    Date getDtDiagnosticoSintoma();

    String getTpClassificacaoFinal();

    String getTpCriterioConfirmacao();
}
