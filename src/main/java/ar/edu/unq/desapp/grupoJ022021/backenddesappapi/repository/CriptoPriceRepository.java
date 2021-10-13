package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoPrice;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriptoPriceRepository extends JpaRepository<CriptoPrice, Long> {
}
