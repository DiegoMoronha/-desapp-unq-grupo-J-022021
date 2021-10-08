package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoActivity;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CriptoActivityRepository extends JpaRepository<CriptoActivity, Long> {

   List<CriptoActivity> findAllByUser(User user);

   List<CriptoActivity> findByCriptoNameAndActivityType(String criptoName , String activityType);
}
