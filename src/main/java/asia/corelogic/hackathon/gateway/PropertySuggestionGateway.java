package asia.corelogic.hackathon.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Component
public class PropertySuggestionGateway {
    public static final String V1_SUGGEST_JSON = "/v1/suggest.json";
    public static final String SUGGESTION_TYPE_ADDRESS = "address";
    private final RestTemplate jsonRestTemplateWithOAuthToken;
    private final String propertySuggestionUri;

    @Autowired
    public PropertySuggestionGateway(@Qualifier(value = "corelogicTemplate") RestTemplate jsonRestTemplateWithOAuthToken,
                                     @Value("${corelogic.suggestion.uri}") String propertySuggestionUri) {
        this.jsonRestTemplateWithOAuthToken = jsonRestTemplateWithOAuthToken;
        this.propertySuggestionUri = propertySuggestionUri;
    }

    public List<PropertySuggestion> findSuggestions(String addressQuery, int maxResult) {
        AddressSuggestion addressSuggestion = jsonRestTemplateWithOAuthToken.getForObject(getUrl(addressQuery, maxResult), AddressSuggestion.class);
        return addressSuggestion.getSuggestions().stream()
            .map(suggest -> new PropertySuggestion(suggest.propertyId, suggest.suggestion))
            .collect(Collectors.toList());
    }

    private URI getUrl(String addressQuery, int maxResult) {
        return fromHttpUrl(propertySuggestionUri)
            .path(V1_SUGGEST_JSON)
            .queryParam("q", addressQuery)
            .queryParam("limit", maxResult)
            .queryParam("suggestionTypes", SUGGESTION_TYPE_ADDRESS)
            .build()
            .toUri();
    }

    private static class AddressSuggestion {
        private List<Suggestion> suggestions;

        public List<Suggestion> getSuggestions() {
            return suggestions;
        }

        public void setSuggestions(List<Suggestion> suggestions) {
            this.suggestions = suggestions;
        }
    }

    private static class Suggestion {
        private long propertyId;
        private String suggestion;

        public void setPropertyId(long propertyId) {
            this.propertyId = propertyId;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }
    }
}
