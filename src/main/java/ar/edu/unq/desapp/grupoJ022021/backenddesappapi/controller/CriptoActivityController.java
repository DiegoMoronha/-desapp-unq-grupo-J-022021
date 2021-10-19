package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.ActivityDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.ActivityResultDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service.CriptoActivityService;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.wrapper.UserDetail;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CriptoActivityController {

@Autowired
    CriptoActivityService activityService;


    @ApiOperation(value = "", authorizations = { @Authorization(value="JWT") })
    @PostMapping("/api/activity/create")
    public HttpStatus createActivity(@RequestBody ActivityDto act){
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        activityService.createNewActivity(act,userDetail.getId());
        return HttpStatus.CREATED;
    }

    @ApiOperation(value = "", authorizations = { @Authorization(value="JWT") })
    @GetMapping("/api/activity/{type}/cripto/{cripto}")
    public ResponseEntity<List<ActivityResultDto>> getActByTypeAndCripto (@PathVariable String type ,
                                                                          @PathVariable String cripto){
        return  ResponseEntity.ok().body(activityService.getActivitiesByTickerAndType(cripto,type));
    }
}
