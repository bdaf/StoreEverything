package pl.team.marking.projectjavaweb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;


@Component
public class RestConnector {

    @Autowired
    RestTemplate connector;

    String url="http://localhost:8070/categories";

    public boolean checkCategory(String category) throws JsonProcessingException {
        ResponseEntity<String> response = connector.getForEntity(url,String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        if(root.toString().toLowerCase(Locale.ROOT).contains(category.toLowerCase(Locale.ROOT))){
            return true;
        }
        return false;
    }

}
