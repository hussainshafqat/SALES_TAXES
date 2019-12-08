package util;

/**
 * interface that provides configurable properties
 * we restrict here to product's tax related information
 */
public interface TaxConfig {
    double getImportDutyRate();
    double getSalesTaxRate();
}
