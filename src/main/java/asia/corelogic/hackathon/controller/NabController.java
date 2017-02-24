package asia.corelogic.hackathon.controller;

import asia.corelogic.hackathon.gateway.NabAccountListGateway;
import asia.corelogic.hackathon.gateway.NabCustomerProfileGateway;
import asia.corelogic.hackathon.gateway.NabLoginGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class NabController {
    private NabLoginGateway gateway;
    private NabAccountListGateway acctGateway;
    private NabCustomerProfileGateway profileGateway;

    public NabController(NabLoginGateway gateway, NabAccountListGateway acctGateway, NabCustomerProfileGateway profileGateway) {
        this.gateway = gateway;
        this.acctGateway = acctGateway;
        this.profileGateway = profileGateway;
    }

    @GetMapping(path = "/nabLogin")
    public String getLoginToken() {
        return gateway.token();
    }

    @GetMapping(path = "/nabAccounts")
    public NabAccountListGateway.Res getAccounts() {
        getLoginToken();

        return acctGateway.accountList();
    }

    @GetMapping(path = "/nabCustomer")
    public Map<Object, Object> profile() {
        getLoginToken();
        return profileGateway.customerProfile();
    }

}
