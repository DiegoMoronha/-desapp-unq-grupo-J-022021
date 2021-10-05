package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

        Optional<User> findById(Integer id);

         User findByName(String username);

         User findByEmail(String email);
}