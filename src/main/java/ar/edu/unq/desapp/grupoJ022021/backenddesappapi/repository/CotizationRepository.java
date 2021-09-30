package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotizationRepository extends JpaRepository<CriptoPrice, Long> {

}
