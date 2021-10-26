package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoActivity;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CriptoActivityRepository extends JpaRepository<CriptoActivity, Long> {

   Optional<CriptoActivity> findById(Long id);

   @Query("SELECT a FROM CriptoActivity a JOIN a.user u WHERE a.id =:actId AND u.id =:userId")
   CriptoActivity findByIdAndUser(@Param("actId")Long id,@Param("userId") Long userId);

   List<CriptoActivity> findAllByUser(User user);

   List<CriptoActivity> findByCriptoNameAndActivityType(String criptoName , String activityType);
}
