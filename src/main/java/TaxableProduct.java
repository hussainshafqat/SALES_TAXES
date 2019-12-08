import util.ConfigProvider;

public  class TaxableProduct extends Product {

    public TaxableProduct(long productId,String description, double basicPrice, boolean isImported) {
        super(productId, description,basicPrice, isImported);
    }

    @Override
    public  double getSalesRate() {
        //basic sales tax rate plus import duty is it is an imported product
        return getImportDuty()+ConfigProvider.getInstance().getSalesTaxRate();
    }
}
