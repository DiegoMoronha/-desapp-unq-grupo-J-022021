package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.UserRegisterDto;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.UserRepository;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.wrapper.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepository userRepository;


    public User getUserByNameAndPassword(String name,String password) {
        return userRepository.findByNameAndPassword(name, password);
    }

    public Boolean existUser(String name,String password){
        return  getUserByNameAndPassword(name,password) != null;
    }


    public UserDetails registerUser(UserRegisterDto userRegisterDto) throws Exception {
        if(existUser(userRegisterDto.getName(),userRegisterDto.getPassword())){
            throw new Exception("error user already exist");
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

    public List<User> getUsers() {
      return  userRepository.findAll();
    }

    public User login(String name, String password) throws Exception {
        if(!existUser(name,password)){
        throw  new Exception("user not exist");
    }
        return getUserByNameAndPassword(name,password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username);
        return new UserDetail(user.getName(),user.getPassword(), user.getId());
    }
}
