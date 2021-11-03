package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.exceptions.UserAlreadyExistsException;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.exceptions.UserDoesntExistException;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.LoginUserDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserRegisterDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserResultDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.UserRepository;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.wrapper.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserRepository userRepository;


    public User getUserByName(String username) {
        return userRepository.findByName(username);
    }

    public User getUserByEmail(String email)
    {
        return userRepository.findByEmail(email);
    }

    public User getUserByAddrWallet(String addrWallet) {

        return userRepository.findByAddrWallet(addrWallet);
    }


    public Boolean existUserByUsername(String username){
        return  getUserByName(username) != null;
    }

    public Boolean existUserByEmail(String email)
    {
        return  getUserByEmail(email) != null;

    }

    public Boolean existUserByAddrWallet(String addrWallet){

        return  getUserByAddrWallet(addrWallet) != null;
    }


    public void verifyUserExist(String username , String email, String addrWallet){
        if(existUserByUsername(username)||existUserByEmail(email)||
                existUserByAddrWallet(addrWallet)){

            throw new UserAlreadyExistsException("Error user with this data already exist");
        }

    }

    public UserDetails registerUser(UserRegisterDto userRegisterDto) throws Exception {

            verifyUserExist(userRegisterDto.getName(),userRegisterDto.getEmail(),userRegisterDto.getAddrWallet());

            User user = new User(userRegisterDto.getName(),
                    userRegisterDto.getLastName(),userRegisterDto.getEmail(),
                    userRegisterDto.getAddress(),userRegisterDto.getPassword(),
                    userRegisterDto.getCvu(),userRegisterDto.getAddrWallet());
            userRepository.save(user);

            return loadUserByUsername(user.getName());
        }



    public boolean isValidUser(String username,String password){
        return existUserByUsername(username) && password.equals(getUserByName(username).getPassword());

    }

    public List<UserResultDto> getUsers() {
      List<UserResultDto> users = new ArrayList<>();
        for (User user :userRepository.findAll()){
        UserResultDto  userR = new UserResultDto(user.getName(),user.getLastName(),
                user.getOperations(),user.getReputation());
            users.add(userR);
        }

        return users ;
    }

    public UserDetails login(LoginUserDto user) throws Exception {
        if(isValidUser(user.getUsername(),user.getPassword())){
            return loadUserByUsername(user.getUsername());
    }
        else{
            throw  new UserDoesntExistException("User not exist");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        return new UserDetail(user.getName(),user.getPassword(), user.getId());
    }

    public void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new Exception("USER_DISABLED");
        }
    }

    public User getUserById(Long id){
        return userRepository.findById(id);
    }

    public void clearDatabase(){
        userRepository.deleteAllInBatch();
    }
}
