package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.ActivityDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.ActivityResultDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service.CriptoActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CriptoActivityController {

@Autowired
    CriptoActivityService activityService;



    @PostMapping("/api/activity/create")
    public HttpStatus createActivity(@RequestHeader("Authorization")String token, @RequestBody ActivityDto act){
        activityService.createNewActivity(act,token);
        return HttpStatus.CREATED;
    }

    @GetMapping("/api/activity/{type}/cripto/{cripto}")
    public ResponseEntity<List<ActivityResultDto>> getActByTypeAndCripto (@PathVariable String type ,
                                                                          @PathVariable String cripto){
        return  ResponseEntity.ok().body(activityService.getActivitiesByTickerAndType(cripto,type));
    }

}
