/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.flinkgutt.samples.nodes.view.product;

import ca.odell.glazedlists.gui.TableFormat;
import net.flinkgutt.samples.nodes.api.IProduct;
import org.openide.util.NbBundle.Messages;
import static net.flinkgutt.samples.nodes.view.product.Bundle.*; // This is so we can use the messages defined in @Messages() above getColumnName(int column) directly

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

    @Messages({"product.table.header.id=ID",
    "product.table.header.name=Name",
    "product.table.header.sortorder=Sort Order",
    "product.table.header.active=Active"})
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return product_table_header_id(); // Messages defined above in the @Messages() annotation. See import section.
            case 1: return product_table_header_name();
            case 2: return product_table_header_sortorder();
            case 3: return product_table_header_active();
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
