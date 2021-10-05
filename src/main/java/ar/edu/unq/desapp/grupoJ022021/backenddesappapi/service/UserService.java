package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserRegisterDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.exceptions.UserAlreadyExistsException;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.exceptions.UserDoesntExistException;
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

import java.util.List;


@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserRepository userRepository;


    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Boolean existUser(String email){
        return  getUserByEmail(email) != null;
    }


    public UserDetails registerUser(UserRegisterDto userRegisterDto) throws Exception {
        if(existUser(userRegisterDto.getEmail())){
            throw new UserAlreadyExistsException("Error user already exist");
        }
        else{
            User user = new User(userRegisterDto.getName(),
                    userRegisterDto.getLastName(),userRegisterDto.getEmail(),
                    userRegisterDto.getAddress(),userRegisterDto.getPassword(),
                    userRegisterDto.getCvu(),userRegisterDto.getAddrWallet());
            userRepository.save(user);

            return loadUserByUsername(user.getName());
        }

    }

    public boolean isValidUser(String email,String password){
        return (existUser(email) && password==getUserByEmail(email).getPassword());

    }

    public List<User> getUsers() {
      return  userRepository.findAll();
    }

    public User login(String email, String password) throws Exception {
        if(isValidUser(email, password)){
            return getUserByEmail(email);
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
}
