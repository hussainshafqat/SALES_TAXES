/**
 * interface to get the sales tax rate
 * that is applicable to a product depending upon the nature of the product,
 * as some product can be subsidized or exempted
 */
public interface TaxRateProvider {
    double getSalesRate();
    double getImportDuty();
}
