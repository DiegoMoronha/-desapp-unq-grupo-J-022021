package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.JwtConfig.JwtTokenUtil;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserRegisterDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.JwtResponse;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service.CriptoPriceService;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service.UserService;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.wrapper.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/api/auth/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody UserRegisterDto user)  {
       try {
           UserDetail userDetail = (UserDetail) userService.registerUser(user);
           String token = jwtTokenUtil.generateToken(userDetail);
           JwtResponse response= new JwtResponse(userDetail.getId(),userDetail.getUsername(),token);
           return ResponseEntity.ok().body(response);
       }
       catch (Exception e){

           return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
       }

    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers() {
     List<User> users= userService.getUsers();
        return ResponseEntity.ok().body(users);
    }



}
