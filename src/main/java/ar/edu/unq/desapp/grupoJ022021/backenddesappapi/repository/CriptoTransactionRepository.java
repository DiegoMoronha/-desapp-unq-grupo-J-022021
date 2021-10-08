package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CriptoTransactionRepository extends JpaRepository<CriptoTransaction, Long> {

}
