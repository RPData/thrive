package asia.corelogic.hackathon.controller;

import asia.corelogic.hackathon.gateway.NabAccountListGateway;
import asia.corelogic.hackathon.gateway.NabLoginGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NabController {
    private NabLoginGateway gateway;
    private NabAccountListGateway acctGateway;

    public NabController(NabLoginGateway gateway, NabAccountListGateway acctGateway) {
        this.gateway = gateway;
        this.acctGateway = acctGateway;
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



}
