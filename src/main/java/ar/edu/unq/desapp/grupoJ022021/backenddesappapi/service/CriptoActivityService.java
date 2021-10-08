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

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CriptoActivityService {

    @Autowired
    private CriptoActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    public void createNewActivity(ActivityDto act,String token){
        String hour = dateHour();
        Long userID = KeyValueSaver.getUserIdLogged(token);
        User user =userRepository.findById(userID);
        CriptoActivity activity = new CriptoActivity(hour, act.getCriptoName(),
                act.getValueCripto(),act.getAmountInArs(),
                act.getActivityType(),user);
        user.addActivity(activity);
        userRepository.save(user);
    }

    public String dateHour() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime today = LocalTime.now();
        String timeString = today.format(formatter);
        return timeString;
    }

    public List<CriptoActivity> getActivitiesByUser(String token){
        Long userID = KeyValueSaver.getUserIdLogged(token);
        User user =userRepository.findById(userID);
        return activityRepository.findAllByUser(user);
    }


    public List<ActivityResultDto> getActivitiesByTickerAndType(String ticker ,String  actType){

        List<ActivityResultDto> res = new ArrayList();
        List<CriptoActivity> activities =activityRepository.findByCriptoNameAndActivityType(ticker,actType);
        for (CriptoActivity act : activities){
            ActivityResultDto activity =new ActivityResultDto(act.getHour(),act.getCriptoName(),
                    act.getValueCripto(),act.getAmountInArs(),
                    act.getUser().getName(),act.getUser().getLastName(),act.getUser().getOperations(),
                    act.getUser().getReputation());
            res.add(activity);
        }
        return res;
    }

}
