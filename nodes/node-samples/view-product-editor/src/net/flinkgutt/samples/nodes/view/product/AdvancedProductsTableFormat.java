package net.flinkgutt.samples.nodes.view.product;

import ca.odell.glazedlists.gui.AdvancedTableFormat;
import java.math.BigDecimal;
import java.util.Comparator;
import net.flinkgutt.samples.nodes.api.IProduct;
import org.openide.util.NbBundle.Messages;
// We import Bundle.* statically
// This is so we can use the messages defined in @Messages() annotation defined
// above getColumnName(int column) directly
import static net.flinkgutt.samples.nodes.view.product.Bundle.*;

/**
 *
 * @author Christian
 */
class AdvancedProductsTableFormat implements AdvancedTableFormat<IProduct> {

    public AdvancedProductsTableFormat() {
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Messages({"product.table.header.id=ID",
        "product.table.header.name=Name",
        "product.table.header.sortorder=Sort Order",
        "product.table.header.active=Active",
        "product.table.header.price=Price"})
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return product_table_header_id(); // Messages defined above in the @Messages() annotation. See import section.
            case 1:
                return product_table_header_name();
            case 2:
                return product_table_header_sortorder();
            case 3:
                return product_table_header_price();
            case 4:
                return product_table_header_active();
            default:
                return "";
        }
    }

    @Override
    public Object getColumnValue(IProduct product, int column) {
        switch (column) {
            case 0:
                return product.getProductID();
            case 1:
                return product.getName();
            case 2:
                return product.getSortOrder();
            case 3:
                return product.getPrice();
            case 4:
                return product.isActive();
            default:
                return null;
        }
    }

    @Override
    public Class getColumnClass(int column) {
        switch (column) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            case 3:
                return BigDecimal.class;
            case 4:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    @Override
    public Comparator getColumnComparator(int column) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
