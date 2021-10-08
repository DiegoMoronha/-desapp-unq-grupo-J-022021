package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoPrice;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service.CriptoPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CriptoPriceController {

    @Autowired
    private CriptoPriceService criptoPriceService;


    @GetMapping("/api/cotization")
    public ResponseEntity<List<CriptoPrice>> getCotizations() {
        List<CriptoPrice> cotizations = criptoPriceService.getCotizations();
        return ResponseEntity.ok().body(cotizations);
    }

}
