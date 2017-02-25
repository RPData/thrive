package asia.corelogic.hackathon.gateway;

import java.util.List;

public class ComparableSalesResponse {
    private List<ComparableSale> comparableSales;
    private int page;
    private int totalPages;
    public ComparableSalesResponse() {
    }
    public List<ComparableSale> getComparableSales() {
        return comparableSales;
    }
    public void setComparableSales(List<ComparableSale> comparableSales) {
        this.comparableSales = comparableSales;
    }
    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
