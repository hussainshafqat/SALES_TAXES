public class PriceCalculator {

    public static double calculateTotalPrice(Product product, int quantityPurchased, double tax) {
        return ((product.getBasicUnitPrice()+tax)*quantityPurchased);
    }

    public static double calculateTax(Product product) {
        return round2Nearest05(product.getBasicUnitPrice()*product.getSalesRate());
    }
    public static double round2Nearest05(double tax) {
        return Math.ceil(tax / 0.05) * 0.05;
    }
}
