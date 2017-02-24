package asia.corelogic.hackathon.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Component
public class NabAccountListGateway {
    public static final String GATEWAY_PATH = "/v2/accounts";
    private final RestTemplate restTemplate;
    private final String path;

    @Autowired
    public NabAccountListGateway(@Qualifier(value = "nabTemplate") RestTemplate restTemplate,
                                 @Value("${nab.uri}") String nabUrl) {
        this.restTemplate = restTemplate;
        this.path = nabUrl;
    }

    public Res accountList() {

        URI url = getUrl();

        return restTemplate.getForObject(url, Res.class);
    }

    private URI getUrl() {
        return fromHttpUrl(path)
            .path(GATEWAY_PATH)
            .queryParam("v", 1)
            .queryParam("category", "domestic")
            .build()
            .toUri();
    }

    public static class Res {
        private AcctResponse accountsResponse;

        public AcctResponse getAccountsResponse() {
            return accountsResponse;
        }

        public void setAccountsResponse(AcctResponse accountsResponse) {
            this.accountsResponse = accountsResponse;
        }
    }

    public static class AcctResponse {
        private List<Account> accounts;

        public List<Account> getAccounts() {
            return accounts;
        }

        public void setAccounts(List<Account> accounts) {
            this.accounts = accounts;
        }
    }

    private static class Account {
        private String nickname;
        private String availableBalance;
        private String currentBalance;
        private String accountIdDisplay;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvailableBalance() {
            return availableBalance;
        }

        public void setAvailableBalance(String availableBalance) {
            this.availableBalance = availableBalance;
        }

        public String getCurrentBalance() {
            return currentBalance;
        }

        public void setCurrentBalance(String currentBalance) {
            this.currentBalance = currentBalance;
        }

        public String getAccountIdDisplay() {
            return accountIdDisplay;
        }

        public void setAccountIdDisplay(String accountIdDisplay) {
            this.accountIdDisplay = accountIdDisplay;
        }
    }
}
