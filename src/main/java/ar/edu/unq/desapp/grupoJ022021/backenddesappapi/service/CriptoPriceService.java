package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.service;

import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.dto.DollarPrice;
import ar.edu.unq.desapp.grupoJ022021.backenddesappapi.model.CriptoPrice;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class CriptoPriceService {

    List<CriptoPrice> criptosCotizations= new ArrayList<CriptoPrice>();
    List<String> criptoParities = Arrays.asList("ALICEUSDT","MATICUSDT","AXSUSDT","AAVEUSDT","ATOMUSDT","NEOUSDT",
            "DOTUSDT","ETHUSDT","CAKEUSDT","BTCUSDT","BNBUSDT");


    private RestTemplate restTemplate= new RestTemplate();

    public CriptoPrice getCriptoBySymbol(String symbol,Double dollar,String hour){
        String url = "https://api1.binance.com/api/v3/ticker/price?symbol="+symbol;
        CriptoPrice cripto =restTemplate.getForObject(url, CriptoPrice.class);
        Double priceCriptoInUsd = Double.parseDouble(cripto.getPriceUsd());
        String priceArs =String.valueOf(priceCriptoInUsd* dollar);
        cripto.setPriceArs(priceArs);
        cripto.setHourCotization(hour);
        return cripto;

    }

    public void getCriptos(Double dollar,String hour){
        String url = "https://api1.binance.com/api/v3/ticker/price";
        ResponseEntity<List<CriptoPrice>> response = restTemplate.exchange(url,HttpMethod.GET,null,
                new ParameterizedTypeReference<List<CriptoPrice>>(){});

        List<CriptoPrice> criptos =response.getBody();

        for (CriptoPrice cripto : criptos){
           if(criptoParities.contains(cripto.getSymbol())) {
                Double priceCriptoInUsd = Double.parseDouble(cripto.getPriceUsd());
                String priceArs = String.valueOf(priceCriptoInUsd * dollar);
                cripto.setPriceArs(priceArs);
                cripto.setHourCotization(hour);
                criptosCotizations.add(cripto);
            }
      }

    }


    public void criptoCotizations(){
        criptosCotizations.clear();
        Double dollar =priceUsd();
        String hour = dateHour();
        getCriptos(dollar,hour);
    }

    public List<CriptoPrice> getCotizations(){
        criptoCotizations();
        return criptosCotizations;
    }


    public Double priceUsd(){
        String authToken ="eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE2NjQ0MTkzMjQsInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJkaWVnb21vcm9uaGFAZ21haWwuY29tIn0.Tr24F-ipWDyXqbi-Z_rT-KAavVFE-OGqQxmBq9Fr-8ibNnhP6RMsuEz1vcbjPoySKEOPwm1VcGvqzoXGt2zI1g";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "BEARER "+ authToken );
       String url ="https://api.estadisticasbcra.com/usd";
        ResponseEntity<List<DollarPrice>> response = restTemplate.exchange(url, HttpMethod.GET,
                new HttpEntity<>("parameters", headers),
                new ParameterizedTypeReference<List<DollarPrice>>(){});

        List<DollarPrice> result =response.getBody();
        return result.get(result.size()-1).getV();
    }

    public String dateHour() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime today = LocalTime.now();
        String timeString = today.format(formatter);
        return timeString;
    }
}
