package asia.corelogic.hackathon.gateway;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ComparableSalesGateway {
    private final RestTemplate restTemplate;
    private final String comparableSalesUri;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ComparableSalesGateway(@Qualifier(value = "corelogicTemplate") RestTemplate restTemplate,
                                  @Value("${corelogic.comparable.uri}") String comparableSalesUri) {
        this.restTemplate = restTemplate;
        this.comparableSalesUri = comparableSalesUri;
    }

    public ComparableSalesResponse findComparableSales() {
        URI searchUri = getSearchUri();
        System.err.println(searchUri.toString());
        JsonNode jsonNode = restTemplate.getForObject(searchUri, JsonNode.class);
        List<JsonNode> jsonPropertySummaries = jsonNode.path("_embedded").path("propertySummaryList").findValues("propertySummary");
        JsonNode page = jsonNode.path("page");
        List<ComparableSale> comparableSales = jsonPropertySummaries.stream()
            .map(propertySummary -> objectMapper.convertValue(propertySummary, PropertySummary.class))
            .map(this::createComparableSaleFrom)
            .collect(Collectors.toList());
        ComparableSalesResponse comparableSalesResponse = new ComparableSalesResponse();
        comparableSalesResponse.setComparableSales(comparableSales);
        comparableSalesResponse.setPage(page.get("number").asInt());
        comparableSalesResponse.setTotalPages(page.get("totalPages").asInt());
        return comparableSalesResponse;
    }

    private ComparableSale createComparableSaleFrom(PropertySummary propertySummary) {
        ComparableSale comparableSale = new ComparableSale(propertySummary.id);
        comparableSale.setSingleLineAddress(propertySummary.address.singleLineAddress);
        comparableSale.setPropertyPhotoUri(propertySummary.propertyPhoto.mediumPhotoUrl);
        comparableSale.setPriceDescription(propertySummary.otmForSaleDetail.priceDescription);
        return comparableSale;
    }

    private URI getSearchUri() {
        return UriComponentsBuilder.fromHttpUrl(comparableSalesUri).build().toUri();
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class PropertySummary {
        private Long id;
        private Address address;
        private OtmForSaleDetail otmForSaleDetail;
        private PropertyPhoto propertyPhoto;
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Address {
        private String singleLineAddress;
    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class OtmForSaleDetail {
        private String priceDescription;

    }

    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class PropertyPhoto {
        private String mediumPhotoUrl;
    }
}
