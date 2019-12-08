package util;

/**
 * utility class that provides access to the configurable properties
 * of the application
 */
public class ConfigProvider {
    private static TaxConfig instance;

    private ConfigProvider() {

    }

    public static TaxConfig getInstance() {
        if(instance==null){
            instance=new DefaultTaxConfig("config.properties");
        }
        return instance;
    }

    public static void setInstance(TaxConfig newInstance) {
        instance = newInstance;
    }
}
