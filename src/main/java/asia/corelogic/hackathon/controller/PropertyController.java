package asia.corelogic.hackathon.controller;

import asia.corelogic.hackathon.gateway.PropertySuggestion;
import asia.corelogic.hackathon.gateway.PropertySuggestionGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PropertyController {

    private PropertySuggestionGateway suggestionGateway;

    @Autowired
    public PropertyController(PropertySuggestionGateway suggestionGateway) {
        this.suggestionGateway = suggestionGateway;
    }

    @GetMapping(path = "/suggestAddress")
    public List<PropertySuggestion> getAddresses(@RequestParam String q) {
        return suggestionGateway.findSuggestions(q, 5);
    }
}
