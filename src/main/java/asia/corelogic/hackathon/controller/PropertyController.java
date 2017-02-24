package asia.corelogic.hackathon.controller;

import asia.corelogic.hackathon.gateway.AvmGateway;
import asia.corelogic.hackathon.gateway.PropertySuggestion;
import asia.corelogic.hackathon.gateway.PropertySuggestionGateway;
import asia.corelogic.hackathon.gateway.RentalAvmGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PropertyController {

    private PropertySuggestionGateway suggestionGateway;
    private AvmGateway avmGateway;
    private RentalAvmGateway avmRental;

    @Autowired
    public PropertyController(PropertySuggestionGateway suggestionGateway, AvmGateway avmGateway, RentalAvmGateway rentalAvmGateway) {
        this.suggestionGateway = suggestionGateway;
        this.avmGateway = avmGateway;
        this.avmRental = rentalAvmGateway;
    }

    @GetMapping(path = "/suggestAddress")
    public List<PropertySuggestion> getAddresses(@RequestParam String q) {
        return suggestionGateway.findSuggestions(q, 5);
    }

    @GetMapping(path = "/avm")
    public AvmGateway.FaresDetails avm(@RequestParam int q) {
        return avmGateway.getAvm(q);
    }
    @GetMapping(path = "/avmRental")
    public RentalAvmGateway.AvmResult avmRental(@RequestParam int q) {
        return avmRental.getAvm(q);
    }
}
