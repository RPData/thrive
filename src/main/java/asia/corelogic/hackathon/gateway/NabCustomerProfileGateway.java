package asia.corelogic.hackathon.gateway;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Component
public class NabCustomerProfileGateway {
    public static final String GATEWAY_PATH = "/v2/customer/profile";
    private final RestTemplate restTemplate;
    private final String path;

    @Autowired
    public NabCustomerProfileGateway(@Qualifier(value = "nabTemplate") RestTemplate restTemplate,
                                 @Value("${nab.uri}") String nabUrl) {
        this.restTemplate = restTemplate;
        this.path = nabUrl;
    }

    public Map<Object, Object> customerProfile() {

        URI url = getUrl();

        JsonNode node = restTemplate.getForObject(url, JsonNode.class);

        return ImmutableMap.builder()
            .put("title", node.path("customerDetailsResponse").path("person").get("prefixTitle").asText())
            .put("firstName", node.path("customerDetailsResponse").path("person").get("firstName").asText())
            .put("lastName", node.path("customerDetailsResponse").path("person").get("lastName").asText())
            .put("dateOfBirth", node.path("customerDetailsResponse").path("person").get("dateOfBirth").asText())
            .put("email", node.path("customerDetailsResponse").path("email").get("id").asText())
            .put("addressLine", node.path("customerDetailsResponse").path("physicalAddress").get("line1").asText() + " " + node.path("customerDetailsResponse").path("physicalAddress").get("line2").asText() )
            .put("city", node.path("customerDetailsResponse").path("physicalAddress").get("city").asText())
            .put("state", node.path("customerDetailsResponse").path("physicalAddress").get("state").asText())
            .build();
    }

    private URI getUrl() {
        return fromHttpUrl(path)
            .path(GATEWAY_PATH)
            .queryParam("v", 1)
            .build()
            .toUri();
    }


//    {
//        "customerDetailsResponse": {
//        "apiStructType": "person",
//            "person": {
//                "firstName": "Leonard",
//                "lastName": "Xavier",
//                "prefixTitle": "Dr.",
//                "shortFirstName": "Xav",
//                "middleNames": "S",
//                "otherName": "Leon",
//                "suffixTitle": "Jr",
//                "preferredName": "Leo",
//                "nabEmployeeType": "Contract",
//                "dateOfBirth": "1978-01-25",
//                "maritalStatus": "Married"
//        },
//        "phone": {
//            "id": "0345673444",
//                "idType": "home",
//                "doNotDisturb": {
//                "dndStart": "0700",
//                    "dndEnd": "1800"
//            }
//        },
//        "mobile": {
//            "id": "0416585432",
//                "idType": "mobile",
//                "doNotDisturb": {
//                "dndStart": "0700",
//                    "dndEnd": "1800"
//            }
//        },
//        "email": {
//            "id": "leo@test.com",
//                "idType": "work"
//        },
//        "physicalAddress": {
//            "addressType": "physicalAddress",
//                "line1": "800 Bourke Street",
//                "line2": "Street 1",
//                "city": "Docklands",
//                "state": "Victoria",
//                "postCode": "3008",
//                "country": "Australia"
//        },
//        "postalAddress": {
//            "addressType": "postalAddress",
//                "line1": "55 Collins Street",
//                "city": "Docklands",
//                "state": "Victoria",
//                "postCode": "3008",
//                "country": "Australia"
//        },
//        "customerType": "Person",
//            "bankerDetails": {
//            "code": "B445",
//                "name": "John Doe",
//                "phone": "02987645934",
//                "mobile": "041645679999",
//                "fax": "0412345698"
//        },
//        "customerStatus": "Active",
//            "marketSegment": "Market Segment",
//            "industryCode": "4321",
//            "industryDescription": "addressBrief",
//            "preferredContactType": "phone",
//            "contacts": [
//        {
//            "apiStructType": "phone",
//            "phone": {
//            "id": "0345673444",
//                "idType": "home",
//                "doNotDisturb": {
//                "dndStart": "0700",
//                    "dndEnd": "1800"
//            }
//        },
//            "preferredMethodOfCommunication": true,
//            "receiveMarketingInfo": true
//        },
//        {
//            "apiStructType": "phone",
//            "phone": {
//            "id": "0416585432",
//                "idType": "mobile",
//                "doNotDisturb": {
//                "dndStart": "0700",
//                    "dndEnd": "1800"
//            }
//        },
//            "preferredMethodOfCommunication": true,
//            "receiveMarketingInfo": true
//        },
//        {
//            "apiStructType": "phone",
//            "phone": {
//            "id": "0345673447",
//                "idType": "work",
//                "doNotDisturb": {
//                "dndStart": "0700",
//                    "dndEnd": "1800"
//            }
//        },
//            "preferredMethodOfCommunication": true,
//            "receiveMarketingInfo": true
//        },
//        {
//            "apiStructType": "email",
//            "email": {
//            "id": "leo@test.com",
//                "idType": "home"
//        },
//            "preferredMethodOfCommunication": true,
//            "receiveMarketingInfo": true
//        },
//        {
//            "apiStructType": "email",
//            "email": {
//            "id": "leo2@test.com",
//                "idType": "work"
//        },
//            "preferredMethodOfCommunication": true,
//            "receiveMarketingInfo": true
//        },
//        {
//            "apiStructType": "facebook",
//            "facebook": {
//            "id": "leohoang"
//        },
//            "preferredMethodOfCommunication": true,
//            "receiveMarketingInfo": true
//        },
//        {
//            "apiStructType": "twitter",
//            "twitter": {
//            "id": "lhoang@twitter.com"
//        },
//            "preferredMethodOfCommunication": true,
//            "receiveMarketingInfo": true
//        },
//        {
//            "apiStructType": "skype",
//            "skype": {
//            "id": "leo765237"
//        },
//            "preferredMethodOfCommunication": true,
//            "receiveMarketingInfo": true
//        },
//        {
//            "apiStructType": "addressBrief",
//            "addressBrief": {
//            "addressType": "physicalAddress",
//                "line1": "800 Bourke Street",
//                "city": "Docklands",
//                "state": "Victoria",
//                "postCode": "3008",
//                "country": "Australia"
//        },
//            "preferredMethodOfCommunication": true,
//            "receiveMarketingInfo": true
//        },
//        {
//            "apiStructType": "addressBrief",
//            "addressBrief": {
//            "addressType": "postalAddress",
//                "line1": "55 Collins Street",
//                "city": "Docklands",
//                "state": "Victoria",
//                "postCode": "3008",
//                "country": "Australia"
//        },
//            "preferredMethodOfCommunication": true,
//            "receiveMarketingInfo": true
//        }
//    ]}
//    }

}
