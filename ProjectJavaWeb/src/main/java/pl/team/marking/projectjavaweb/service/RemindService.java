package pl.team.marking.projectjavaweb.service;

import pl.team.marking.projectjavaweb.entity.UserApp;

public interface RemindService {

    String ifHasRemindInformation(UserApp user);
}
