package asia.corelogic.hackathon.gateway;

public class ComparableSale {
    private long propertyId;
    private String singleLineAddress;
    private String propertyPhotoUri;
    private String priceDescription;

    public ComparableSale() {
    }

    public ComparableSale(long propertyId) {
        this.propertyId = propertyId;
    }

    public long getPropertyId() {
        return propertyId;
    }

    public String getSingleLineAddress() {
        return singleLineAddress;
    }

    public void setSingleLineAddress(String singleLineAddress) {
        this.singleLineAddress = singleLineAddress;
    }

    public String getPropertyPhotoUri() {
        return propertyPhotoUri;
    }

    public void setPropertyPhotoUri(String propertyPhotoUri) {
        this.propertyPhotoUri = propertyPhotoUri;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    public String getPriceDescription() {
        return priceDescription;
    }

    public void setPriceDescription(String priceDescription) {
        this.priceDescription = priceDescription;
    }
}
