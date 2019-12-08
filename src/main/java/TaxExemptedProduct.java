/**
 * Products free of basic sales tax,
 * like Books, Medicines and food items
 */
public class TaxExemptedProduct extends Product {

    public TaxExemptedProduct(long productId, String name, double basicPrice, boolean isImported) {
        super(productId,name, basicPrice, isImported);
    }

    @Override
    public double getSalesRate() {
        /*
         *exempted and import duty is applied
         * only  if it is an imported product
         */
        return getImportDuty();
    }
}
