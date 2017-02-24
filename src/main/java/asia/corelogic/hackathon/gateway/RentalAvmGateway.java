package asia.corelogic.hackathon.gateway;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Component
public class RentalAvmGateway {
    public static final String GATEWAY_PATH = "/v1/property/{propertyId}/rentalAvm.json";
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
    public RentalAvmGateway(@Qualifier(value = "corelogicTemplate") RestTemplate restTemplate,
                            @Value("${corelogic.suggestion.uri}") String propertyUrl) {
        this.restTemplate = restTemplate;
        this.path = propertyUrl;
    }

    public AvmResult getAvm(int propertyId) {
        String payload = String.format(
            "{  \"propertyId\": %s}", propertyId);
        JsonNode jsonNode = restTemplate.getForObject(getUrl(propertyId), JsonNode.class);
        JsonNode details = jsonNode.path("rentalAvmDetail");
        return new AvmResult(
            details.get("rentalAvmEstimate").asInt(),
            details.get("rentalAvmEstimateHigh").asInt(),
            details.get("rentalAvmEstimateLow").asInt(),
            details.get("rentalAvmPeriod").asText()
        );
    }

    private URI getUrl(int propertyId) {
        return fromHttpUrl(path)
            .path(GATEWAY_PATH)
            .buildAndExpand(propertyId)
            .toUri();
    }

    public static class AvmResult {
        private int rentalAvmEstimate;
        private int rentalAvmEstimateHigh;
        private int rentalAvmEstimateLow;
        private String rentalAvmPeriod;

        public AvmResult() {
        }

        public AvmResult(int rentalAvmEstimate, int rentalAvmEstimateHigh, int rentalAvmEstimateLow, String rentalAvmPeriod) {
            this.rentalAvmEstimate = rentalAvmEstimate;
            this.rentalAvmEstimateHigh = rentalAvmEstimateHigh;
            this.rentalAvmEstimateLow = rentalAvmEstimateLow;
            this.rentalAvmPeriod = rentalAvmPeriod;
        }

        public int getRentalAvmEstimate() {
            return rentalAvmEstimate;
        }

        public int getRentalAvmEstimateHigh() {
            return rentalAvmEstimateHigh;
        }

        public int getRentalAvmEstimateLow() {
            return rentalAvmEstimateLow;
        }

        public String getRentalAvmPeriod() {
            return rentalAvmPeriod;
        }
    }
}