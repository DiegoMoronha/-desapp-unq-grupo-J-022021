package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.configKeyValue.KeyValueSaver;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.ActivityDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.ActivityResultDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoActivity;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.CriptoActivityRepository;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CriptoActivityService {

    @Autowired
    private CriptoActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    public void createNewActivity(ActivityDto act,Long userID){
        LocalDateTime hour = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
        User user =userRepository.findById(userID);
        CriptoActivity activity = new CriptoActivity(hour, act.getCriptoName(),
                act.getNominals(),
                act.getValueCripto(),act.getAmountInArs(),
                act.getActivityType(),user);
        user.addActivity(activity);
        userRepository.save(user);
    }


    public List<CriptoActivity> getActivitiesByUser(Long userID){
        User user =userRepository.findById(userID);
        return activityRepository.findAllByUser(user);
    }

    public CriptoActivity getActivityByUser(Long userID){
        User user =userRepository.findById(userID);
        return activityRepository.findByUser(user);
    }

    public List<ActivityResultDto> getActivitiesByTickerAndType(String ticker ,String  actType){

        List<ActivityResultDto> res = new ArrayList();
        List<CriptoActivity> activities =activityRepository.findByCriptoNameAndActivityType(ticker,actType);

        for (CriptoActivity act : activities){
            User user =act.getUser();
            ActivityResultDto activity =new ActivityResultDto(act.getHour(),act.getId(),user.getId(),act.getCriptoName(),
                    act.getValueCripto(),act.getNominals(),act.getAmountInArs(),
                   user.getName(),act.getUser().getLastName(),user.getOperations(),
                    user.getReputation());
            res.add(activity);
        }
        return res;
    }

    public void clearDatabase(){
        activityRepository.deleteAllInBatch();
    }

}
