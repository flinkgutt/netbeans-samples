/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.flinkgutt.samples.nodes.view.product;

import ca.odell.glazedlists.gui.TableFormat;
import net.flinkgutt.samples.nodes.api.IProduct;

/**
 *
 * @author Christian
 */
class AdvancedProductsTableFormat implements TableFormat<IProduct> {

    public AdvancedProductsTableFormat() {
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "ID";
            case 1: return "Name";
            case 2: return "Sort Order";
            case 3: return "Active";
            default: return "";
        }
    }

    @Override
    public Object getColumnValue(IProduct product, int column) {
        switch (column) {
            case 0: return product.getProductID();
            case 1: return product.getName();
            case 2: return product.getSortOrder();
            case 3: return product.isActive();
            default: return "";
        }
    }
}
