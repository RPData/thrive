package asia.corelogic.hackathon.gateway;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

public class ComparableSale {
    private long propertyId;
    private String singleLineAddress;
    private String propertyPhotoUri;
    private String advertisementDescription;
    private Integer firstAdvertisementPrice;
    private Integer latestAdvertisementPrice;
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
    public String getAdvertisementDescription() {
        return advertisementDescription;
    }
    public void setAdvertisementDescription(String advertisementDescription) {
        this.advertisementDescription = advertisementDescription;
    }

    public Integer getFirstAdvertisementPrice() {
        return firstAdvertisementPrice;
    }
    public void setFirstAdvertisementPrice(Integer firstAdvertisementPrice) {
        this.firstAdvertisementPrice = firstAdvertisementPrice;
    }
    public Integer getLatestAdvertisementPrice() {
        return latestAdvertisementPrice;
    }
    public void setLatestAdvertisementPrice(Integer latestAdvertisementPrice) {
        this.latestAdvertisementPrice = latestAdvertisementPrice;
    }
}
