package pl.team.marking.projectjavaweb.service;

import pl.team.marking.projectjavaweb.entity.UserApp;

public interface RemindService {

    boolean ifHasRemindInformation(UserApp user);
}
