package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.controller;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.aspects.ExceptionAspect;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoPrice;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.User;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service.CriptoPriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(tags = "Cotization")
public class CriptoPriceController {

    @Autowired
    private CriptoPriceService criptoPriceService;

    @ApiOperation(value = "get cotizations", authorizations = { @Authorization(value="JWT") })
    @ExceptionAspect
    @GetMapping(value="/api/cotization")
    public ResponseEntity<List<CriptoPrice>> getCotizations() {
        List<CriptoPrice> cotizations = criptoPriceService.getCotizations();
        return ResponseEntity.ok().body(cotizations);
    }

    @ApiOperation(value = "get cotization by symbol", authorizations = { @Authorization(value="JWT") })
    @ExceptionAspect
    @GetMapping(value="/api/cotization/{symbol}")
    public ResponseEntity<CriptoPrice> getCotizationBySymbol(@PathVariable String symbol) {
        CriptoPrice cotization = criptoPriceService.getCotizationBySymbol(symbol);
        return ResponseEntity.ok().body(cotization);
    }

}
