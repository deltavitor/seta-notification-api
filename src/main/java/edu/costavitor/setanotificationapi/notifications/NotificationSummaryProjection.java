package edu.costavitor.setanotificationapi.notifications;

import java.util.Date;

public interface NotificationSummaryProjection {

    String getNuNotificacao();

    Date getDtNotificacao();

    String getTpClassificacaoFinal();

    String getTpCriterioConfirmacao();
}
