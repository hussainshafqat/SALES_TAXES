import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Class that emulates basket of purchased products
 */
public class ShoppingBasket {
    private HashMap<Long, List<Product>> products = new HashMap<>();

    private double salesTaxPaid;
    private double totalPrice;
    public ShoppingBasket() {
        products = new LinkedHashMap<>();
    }

    public double getSalesTaxPaid() {
        return salesTaxPaid;
    }


    public HashMap<Long, List<Product>> getProducts() {
        return products;
    }

    public List<Product> getProductsByProductId(long productID) {
        List<Product> products =new ArrayList<>();
        if(this.products.containsKey(productID)){
            products=this.products.get(productID);
        }
        return products;
    }
    public double getTotalPrice() {
        return totalPrice;
    }


    private void addProducts(List<Product> productList) {
        for(Product product:productList){
            add2Basket(product);
        }
    }

    public void add2Basket(Product product){
        if (!products.containsKey(product.getProductId())) {
            ArrayList<Product> list = new ArrayList<>();
            list.add(product);
            products.put(product.getProductId(), list);
        } else {
            products.get(product.getProductId()).add(product);
        }
    }
    public void addAllOfType(List<Product> products){
        addProducts(products);
    }

    /**
     * Product wise print based on product id
     * I18N is not used for simplicity
     */
    public void printPointOfSaleReceipt(){
        for(List<Product> products:getProducts().values()){
            //at least one item
            if(products != null) {
                int quantity = products.size();
                if (quantity >0) {
                    Product product = products.get(0);
                    double taxPerItem = PriceCalculator.calculateTax(product);
                    double itemsPrice = PriceCalculator.calculateTotalPrice(product, quantity, taxPerItem);
                    System.out.println(quantity + " " + product.getDescription() + ": " + String.format("%.2f", itemsPrice));
                            //+ " (tax @ "+String.format("%.2f", taxPerItem)+" per item)");
                    totalPrice += itemsPrice;
                    salesTaxPaid += taxPerItem*quantity;
                }
            }
        }
        //salesTaxPaid= round2Nearest05(salesTaxPaid);
        totalPrice=new BigDecimal(totalPrice).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
        salesTaxPaid=new BigDecimal(salesTaxPaid).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue();
        System.out.println("Sales Tax: "+ String.format("%.2f", salesTaxPaid));
        System.out.println("Total: "+ String.format("%.2f", totalPrice));
    }

}
