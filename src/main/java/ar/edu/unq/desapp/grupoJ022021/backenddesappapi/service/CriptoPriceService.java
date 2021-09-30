package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.DollarPrice;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoPrice;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.repository.CotizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.*;


@Service
@Transactional
public class CriptoPriceService {

    @Autowired
    CotizationRepository cotizationRepository;

    LocalDateTime actual = LocalDateTime.now();
    LocalDateTime tenMinutes;

    private RestTemplate restTemplate= new RestTemplate();

    public CriptoPrice saveCriptoBySymbol(String symbol){
        String url = "https://api1.binance.com/api/v3/ticker/price?symbol="+symbol;
        CriptoPrice cripto =restTemplate.getForObject(url, CriptoPrice.class);
        cotizationRepository.save(cripto);
        Double priceCriptoInUsd = Double.parseDouble(cripto.getPrice());
        String priceArs =String.valueOf(priceCriptoInUsd* priceUsd());
        cripto.setPriceArs(priceArs);
        return cripto;

    }


    public void saveCotizations(){
    List<String> criptoParities = Arrays.asList("ALICEUSDT","MATICUSDT","AXSUSDT","AAVEUSDT","ATOMUSDT","NEOUSDT",
        "DOTUSDT","ETHUSDT","CAKEUSDT","BTCUSDT","BNBUSDT");
    if(tenMinutes ==null || actual.isAfter(tenMinutes)) {
        for (String criptoParity : criptoParities) {
            saveCriptoBySymbol(criptoParity);
        }
        System.out.println( actual.isAfter(tenMinutes));
        System.out.println(actual.getMinute());
        tenMinutes=actual.plusMinutes(10);
        System.out.println(tenMinutes.getMinute());
    }
    }

    public List<CriptoPrice> getCotizations(){
        saveCotizations();
        return cotizationRepository.findAll();
    }


    public Double priceUsd(){
        String authToken ="eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NjQ0MTkzMjQsInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJkaWVnb21vcm9uaGFAZ21haWwuY29tIn0.Tr24F-ipWDyXqbi-Z_rT-KAavVFE-OGqQxmBq9Fr-8ibNnhP6RMsuEz1vcbjPoySKEOPwm1VcGvqzoXGt2zI1g";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "BEARER "+ authToken );
       String url ="https://api.estadisticasbcra.com/usd";
        ResponseEntity<List<DollarPrice>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                new ParameterizedTypeReference<List<DollarPrice>>(){});
        List<DollarPrice> result =response.getBody();
        return result.get(result.size()-1).getV();
    }

}
