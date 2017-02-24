package asia.corelogic.hackathon.gateway;

public class PropertySuggestion {
    private long propertyId;
    private String address;

    public PropertySuggestion(long propertyId, String address) {
        this.propertyId = propertyId;
        this.address = address;
    }

    public PropertySuggestion() {
    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
