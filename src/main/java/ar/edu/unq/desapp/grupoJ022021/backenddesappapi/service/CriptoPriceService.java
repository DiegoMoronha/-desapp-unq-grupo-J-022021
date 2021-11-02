package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.DollarPrice;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.CriptoPriceDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;


@Service
public class CriptoPriceService {

    List<String> criptoParities = Arrays.asList("ALICEUSDT","MATICUSDT","AXSUSDT","AAVEUSDT","ATOMUSDT","NEOUSDT",
            "DOTUSDT","ETHUSDT","CAKEUSDT","BTCUSDT","BNBUSDT");


    private RestTemplate restTemplate= new RestTemplate();


    public CriptoPriceDto getCotizationBySymbol(String symbol) {
        Double dollar = priceUsd();
        LocalDateTime hour = LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
        String url = "https://api1.binance.com/api/v3/ticker/price?symbol=" + symbol;
        CriptoPriceDto cripto = restTemplate.getForObject(url, CriptoPriceDto.class);
        Double priceCriptoInUsd = cripto.getPriceUsd();
        Double priceArs = priceCriptoInUsd * dollar;
        cripto.setPriceArs(priceArs);
        cripto.setHourCotization(hour);

        return cripto;
    }

    public List<CriptoPriceDto> getCriptos(Double dollar, LocalDateTime hour){
        List<CriptoPriceDto> criptosCotizations= new ArrayList<CriptoPriceDto>();
        String url = "https://api1.binance.com/api/v3/ticker/price";
        ResponseEntity<List<CriptoPriceDto>> response = restTemplate.exchange(url,HttpMethod.GET,null,
                new ParameterizedTypeReference<List<CriptoPriceDto>>(){});

        List<CriptoPriceDto> criptos =response.getBody();

        for (CriptoPriceDto cripto : criptos){
           if(criptoParities.contains(cripto.getSymbol())) {
                Double priceCriptoInUsd = cripto.getPriceUsd();
                Double priceArs =priceCriptoInUsd * dollar;
                cripto.setPriceArs(priceArs);
                cripto.setHourCotization(hour);
                criptosCotizations.add(cripto);
            }
      }
           return criptosCotizations;
    }


    public List<CriptoPriceDto> criptoCotizations(){
            Double dollar = priceUsd();
            LocalDateTime hour =  LocalDateTime.now(ZoneId.of("America/Buenos_Aires"));
         return  getCriptos(dollar, hour);
        }

    @Cacheable("cotization")
    public List<CriptoPriceDto> getCotizations(){

        return criptoCotizations();
    }


    public Double priceUsd(){
       String url ="https://api-dolar-argentina.herokuapp.com/api/dolarblue";
        DollarPrice dolarPrice = restTemplate.getForObject(url,DollarPrice.class);



        return dolarPrice.getVenta();
    }
}
