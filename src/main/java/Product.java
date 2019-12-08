import util.ConfigProvider;

public abstract class Product implements TaxRateProvider {

    private final long productId;
    private final String description;
    private double basicUnitPrice;
    private boolean isImported;


    public Product(long productId,String name, double basicPrice, boolean isImported) {
        this.productId = productId;
        this.basicUnitPrice = basicPrice;
        this.isImported = isImported;
        this.description = name;
    }

    public boolean isImported() {
        return isImported;
    }

    public double getBasicUnitPrice() {
        return basicUnitPrice;
    }

    public long getProductId() {
        return productId;
    }
    public String getDescription() {
        return description;
    }
    @Override
    public double getImportDuty() {
        double duty= 0;
       if(isImported()){
            duty= ConfigProvider.getInstance().getImportDutyRate();
        }
       return duty;
    }

    @Override
    abstract public double getSalesRate();

}
