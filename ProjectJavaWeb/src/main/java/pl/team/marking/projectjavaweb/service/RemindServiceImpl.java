package pl.team.marking.projectjavaweb.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.team.marking.projectjavaweb.entity.UserApp;
import pl.team.marking.projectjavaweb.repository.InformationRepository;

@Service
@AllArgsConstructor
public class RemindServiceImpl implements RemindService {

    InformationRepository informationRepository;

    @Override
    public boolean ifHasRemindInformation(UserApp user) {
        Integer ifHasRemind = informationRepository.countRemindInformationByUser(user);
        if (ifHasRemind > 0)
            return true;
        return false;
    }
}
