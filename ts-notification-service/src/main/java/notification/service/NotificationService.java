package notification.service;

import notification.domain.NotifyInfo;

/**
 * Created by Wenyi on 2017/6/15.
 */
public interface NotificationService {
    boolean notify(NotifyInfo info);
}
