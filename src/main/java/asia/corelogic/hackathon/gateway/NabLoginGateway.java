package asia.corelogic.hackathon.gateway;

import asia.corelogic.hackathon.config.NabToken;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Component
public class NabLoginGateway {
    public static final String GATEWAY_PATH = "/v2/auth";
    private final RestTemplate restTemplate;
    private final String path;

    @Autowired
    public NabLoginGateway(@Qualifier(value = "nabTemplate") RestTemplate restTemplate,
                           @Value("${nab.uri}") String nabUrl) {
        this.restTemplate = restTemplate;
        this.path = nabUrl;
    }

    public String token() {
        URI url = getUrl();
        JsonNode jsonNode = restTemplate.postForObject(url, new LoginPayload(), JsonNode.class);
        List<JsonNode> tokens = jsonNode.path("loginResponse").findValues("tokens");
        JsonNode jsonNode1 = tokens.get(0);
        String token = jsonNode1.get(0).get("value").asText();
        NabToken.TOKEN.set(token);
        return token;
    }


    private URI getUrl() {
        return fromHttpUrl(path)
            .path(GATEWAY_PATH)
            .queryParam("v", 1)
            .build()
            .toUri();
    }

    private static class LoginPayload {
        private LoginRequest loginRequest = new LoginRequest();

        public LoginRequest getLoginRequest() {
            return loginRequest;
        }

        public void setLoginRequest(LoginRequest loginRequest) {
            this.loginRequest = loginRequest;
        }
    }

    private static class LoginRequest {
        String brand = "nab";
        String lob = "nab";
        Creds credentials = new Creds();

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getLob() {
            return lob;
        }

        public void setLob(String lob) {
            this.lob = lob;
        }

        public Creds getCredentials() {
            return credentials;
        }

        public void setCredentials(Creds credentials) {
            this.credentials = credentials;
        }
    }

    private static class Creds {
        private String apiStructType = "usernamePassword";
        private Upass usernamePassword = new Upass();

        public String getApiStructType() {
            return apiStructType;
        }

        public void setApiStructType(String apiStructType) {
            this.apiStructType = apiStructType;
        }

        public Upass getUsernamePassword() {
            return usernamePassword;
        }

        public void setUsernamePassword(Upass usernamePassword) {
            this.usernamePassword = usernamePassword;
        }
    }

    private static class Upass {
        private String username = "10055670";
        private String password = "aaa111";

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
//    {
//        "loginRequest": {
//        "brand": "nab",
//            "lob": "nab",
//            "credentials": {
//            "apiStructType": "usernamePassword",
//                "usernamePassword": {
//                "username": "10055670",
//                    "password": "aaa111"
//            }
//        }
    //  }
}

