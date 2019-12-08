package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Properties;

/**
 * implementation to the configurable properties
 * that serves the purpose of this particular application
 */
public class DefaultTaxConfig implements TaxConfig {


    private final Properties config;

    public DefaultTaxConfig(String path) {
        config = new Properties();
        try {
            config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
        } catch (IOException | NullPointerException e) {
            //use the default hardcoded values in this case
        }
    }
    public double getImportDutyRate() {
        String dutyRatePercentage = config.getProperty("DUTY_RATE_PERCENTAGE");
        return getRate(dutyRatePercentage,5);
    }

    public double getSalesTaxRate() {
        String salesTaxRatePercentage = config.getProperty("BASIC_SALES_TAX_RATE_PERCENTAGE");
        return getRate(salesTaxRatePercentage,10);
    }
    private double getRate(String ratePercentage,double defaultRate) {
        double rate = defaultRate / 100;
        if (ratePercentage != null) {
            try {
                rate = Double.parseDouble(ratePercentage) / 100;
            } catch (NumberFormatException e) {
                //to avoid dependencies just print the exception
                e.printStackTrace();
                //will return the default rate
            }

        }
        return rate;
    }


}
