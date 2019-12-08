import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import util.ConfigProvider;
import util.DefaultTaxConfig;

import java.util.ArrayList;

public class ShoppingBasketTest {

    public ShoppingBasketTest() {
    }

    @Before
    public void setUp() {
        //not necessarily needed for our test purposes
        ConfigProvider.setInstance(new DefaultTaxConfig("config.properties"));
        //

    }

    /**
     * Input :
     * 1 book at 12.49
     * 1 music CD at 14.99
     * 1 chocolate bar at 0.85
     * <p>
     * Output 1:
     * 1 chocolate bar: 0.85
     * 1 music CD: 16.49
     * Sales Taxes: 1.50
     * Total: 29.83
     */
    @Test
    public void providedInput1Test() {
        Product productBook = ProductFactory.produce(ProductType.BookType1, 1).get(0);
        Product productCd = ProductFactory.produce(ProductType.MusicCD, 1).get(0);
        Product productChocolateBar = ProductFactory.produce(ProductType.ChocolatesType1, 1).get(0);
        ShoppingBasket basket = new ShoppingBasket();
        basket.add2Basket(productBook);
        basket.add2Basket(productCd);
        basket.add2Basket(productChocolateBar);
        System.out.println("Output Provided Test 1:");
        basket.printPointOfSaleReceipt();
        Assert.assertEquals(1.50, basket.getSalesTaxPaid(), 0);
        Assert.assertEquals(29.83, basket.getTotalPrice(), 0);
    }

    /**
     * Input 2:
     * 1 imported box of chocolates at 10.00
     * 1 imported bottle of perfume at 47.50
     * Output 2:
     * 1 imported box of chocolates: 10.50
     * 1 imported bottle of perfume: 54.65
     * Sales Taxes: 7.65
     * Total: 65.15
     */
    @Test
    public void providedInput2Test() {
        Product productChocolateBox = ProductFactory.produce(ProductType.ChocolatesType2, 1).get(0);
        Product productPerfume = ProductFactory.produce(ProductType.PerfumeType2, 1).get(0);
        ShoppingBasket basket = new ShoppingBasket();
        basket.add2Basket(productChocolateBox);
        basket.add2Basket(productPerfume);
        System.out.println("Output Provided Test 2:");
        basket.printPointOfSaleReceipt();
        Assert.assertEquals(7.65, basket.getSalesTaxPaid(), 0);
        Assert.assertEquals(65.15, basket.getTotalPrice(), 0);
    }

    /**
     * Input 3:
     * 1 imported bottle of perfume at 27.99
     * 1 bottle of perfume at 18.99
     * 1 packet of headache pills at 9.75
     * 1 box of imported chocolates at 11.25
     * Output 3:
     * 1 imported bottle of perfume: 32.19
     * 1 bottle of perfume: 20.89
     * 1 packet of headache pills: 9.75
     * 1 imported box of chocolates: 11.85
     * Sales Taxes: 6.70
     * Total: 74.68
     */
    @Test
    public void providedInput3Test() {
        ArrayList<Product> productImportedPerfumes = ProductFactory.produce(ProductType.PerfumeType3, 1);
        ArrayList<Product> productPerfumes = ProductFactory.produce(ProductType.PerfumeType1, 1);
        ArrayList<Product> productPills = ProductFactory.produce(ProductType.Pills, 1);
        ArrayList<Product> productImportedChocolate = ProductFactory.produce(ProductType.ChocolatesType3, 1);
        ShoppingBasket basket = new ShoppingBasket();
        basket.addAllOfType(productImportedPerfumes);
        basket.addAllOfType(productPerfumes);
        basket.addAllOfType(productPills);
        basket.addAllOfType(productImportedChocolate);
        System.out.println("Output Provided Test 3:");
        basket.printPointOfSaleReceipt();
        Assert.assertEquals(6.70, basket.getSalesTaxPaid(), 0);
        Assert.assertEquals(74.68, basket.getTotalPrice(), 0);
    }


    @Test
    public void exemptedWithNWithoutImportDutyTest() {
        Product productsBook = ProductFactory.produce(ProductType.BookType1, 1).get(0);
        Product productsImportedBook = ProductFactory.produce(ProductType.BookType2, 1).get(0);
        ShoppingBasket basket = new ShoppingBasket();
        basket.add2Basket(productsBook);
        basket.add2Basket(productsImportedBook);
        System.out.println("Output exemptedWithNWithoutImportDutyTest:");
        basket.printPointOfSaleReceipt();
        //no sales
        Assert.assertEquals(0, PriceCalculator.calculateTax(productsBook), 0);
        //import duty rounded to nearest 0.05
        Assert.assertEquals(0.65, PriceCalculator.calculateTax(productsImportedBook), 0);
    }

    @Test
    public void multipleItemsTest() {
        ArrayList<Product> productImportedPerfumes = ProductFactory.produce(ProductType.PerfumeType3, 2);
        ArrayList<Product> productPerfumes = ProductFactory.produce(ProductType.PerfumeType1, 3);
        ArrayList<Product> productPills = ProductFactory.produce(ProductType.Pills, 4);
        ArrayList<Product> productImportedChocolate = ProductFactory.produce(ProductType.ChocolatesType3, 5);
        ShoppingBasket basket = new ShoppingBasket();
        basket.addAllOfType(productImportedPerfumes);
        basket.addAllOfType(productPerfumes);
        basket.addAllOfType(productPills);
        basket.addAllOfType(productImportedChocolate);
        System.out.println("Output Multiple items:");
        basket.printPointOfSaleReceipt();
        Assert.assertEquals(2,basket.getProductsByProductId(ProductType.PerfumeType3.ordinal()).size(),0);
        Assert.assertEquals(3,basket.getProductsByProductId(ProductType.PerfumeType1.ordinal()).size(),0);
        Assert.assertEquals(4,basket.getProductsByProductId(ProductType.Pills.ordinal()).size(),0);
        Assert.assertEquals(5,basket.getProductsByProductId(ProductType.ChocolatesType3.ordinal()).size(),0);
        Assert.assertEquals(17.10,basket.getSalesTaxPaid(),0);

    }
    @Test
    public void exemptedThreeItemsQtyFour() {
        ArrayList<Product> products = ProductFactory.produce(ProductType.BookType1, 4);
        ShoppingBasket basket = new ShoppingBasket();
        basket.addAllOfType(products);
        Assert.assertEquals(4,basket.getProductsByProductId(ProductType.BookType1.ordinal()).size(),0);
        System.out.println("Output 4 items of the same product id:");
        basket.printPointOfSaleReceipt();

    }

    static class ProductFactory {

        static ArrayList<Product> produce(ProductType type, int qty) {
            ArrayList<Product> products = new ArrayList<>();
            for (int i = 0; i < qty; i++) {
                Product product;
                if (type.isTaxExempted()) {
                    product = new TaxExemptedProduct(type.ordinal(), type.getDescription(),
                            type.getUnitPrice(), type.isImported());
                } else {
                    product = new TaxableProduct(type.ordinal(), type.getDescription(),
                            type.getUnitPrice(), type.isImported());
                }
                products.add(product);
            }
            return products;
        }
    }

    enum ProductType {
        BookType1("book", 12.49, true, false),
        BookType2("Imported book", 12.49, true, true),
        Pills("packet of headache pills", 9.75, true, false),
        MusicCD("music CD", 14.99, false, false),
        PerfumeType1("bottle of perfume", 18.99, false, false),
        PerfumeType2("imported bottle of perfume", 47.50, false, true),
        PerfumeType3("imported bottle of perfume", 27.99, false, true),
        ChocolatesType1("chocolate bar", 0.85, true, false),
        ChocolatesType2("box of imported chocolates", 10.00, true, true),
        ChocolatesType3("box of imported chocolates", 11.25, true, true),

        ;
        private final String description;
        private final boolean imported;
        private final boolean isTaxExempted;
        private final double unitPrice;

        ProductType(String description, double unitPrice, boolean isTaxExempted, boolean isImported) {
            this.imported = isImported;
            this.description = description;
            this.isTaxExempted = isTaxExempted;
            this.unitPrice = unitPrice;
        }

        public String getDescription() {
            return description;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public boolean isImported() {
            return imported;
        }

        public boolean isTaxExempted() {
            return isTaxExempted;
        }
    }
}