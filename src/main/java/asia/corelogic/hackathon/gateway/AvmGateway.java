package asia.corelogic.hackathon.gateway;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Component
public class AvmGateway {
    public static final String GATEWAY_PATH = "/v1/property/avm.json";
    private final RestTemplate restTemplate;
    private final String path;
//
//    {
//        "propertyId":0,
//        "targetPropertyAvmInput":{
//        "bathrooms":0,
//            "bedrooms":0,
//            "carSpaces":0,
//            "craftsmanshipQuality":"MUCH_BETTER",
//            "floorAreaM2":0,
//            "landAreaM2":0,
//            "propertyType":"COMMERCIAL",
//            "saleDate":"string",
//            "salePrice":0,
//            "valuationDate":"string",
//            "yearBuilt":0
//    }
//    }

    @Autowired
    public AvmGateway(RestTemplate restTemplate,
                      @Value("${corelogic.suggestion.uri}") String propertyUrl) {
        this.restTemplate = restTemplate;
        this.path = propertyUrl;
    }

    public FaresDetails getAvm(int propertyId) {
        String payload = String.format(
            "{  \"propertyId\": %s}", propertyId);
        JsonNode jsonNode = restTemplate.postForObject(postUrl(propertyId), new Avm(propertyId), JsonNode.class);
        JsonNode details = jsonNode.path("avmFaresDetail");
        return new FaresDetails(
            details.get("faresValueEstimate").asInt(),
            details.get("faresValueHigh").asInt(),
            details.get("faresValueLow").asInt()
            );
    }

    private static class Avm {
        private int propertyId;

        public Avm() {
        }

        public Avm(int propertyId) {

            this.propertyId = propertyId;
        }

        public int getPropertyId() {
            return propertyId;
        }

        public void setPropertyId(int propertyId) {
            this.propertyId = propertyId;
        }
    }

    private URI postUrl(int propertyId) {
        return fromHttpUrl(path)
            .path(GATEWAY_PATH)
            .build()
            .toUri();
    }

    public static class FaresDetails {
        private int faresValueEstimate;
        private int faresValueHigh;
        private int faresValueLow;

        public FaresDetails() {
        }

        public FaresDetails(int faresValueEstimate, int faresValueHigh, int faresValueLow) {
            this.faresValueEstimate = faresValueEstimate;
            this.faresValueHigh = faresValueHigh;
            this.faresValueLow = faresValueLow;
        }

        public int getFaresValueEstimate() {
            return faresValueEstimate;
        }

        public void setFaresValueEstimate(int faresValueEstimate) {
            this.faresValueEstimate = faresValueEstimate;
        }

        public int getFaresValueHigh() {
            return faresValueHigh;
        }

        public void setFaresValueHigh(int faresValueHigh) {
            this.faresValueHigh = faresValueHigh;
        }

        public int getFaresValueLow() {
            return faresValueLow;
        }

        public void setFaresValueLow(int faresValueLow) {
            this.faresValueLow = faresValueLow;
        }
    }
}