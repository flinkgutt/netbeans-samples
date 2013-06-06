package net.flinkgutt.samples.nodes.domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.flinkgutt.samples.nodes.domain.entities.Product;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Christian
 */
class ProductRowMapper implements RowMapper<Product> {

    @Override
    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
        Product p = new Product();
        p.setProductID(rs.getInt("product_id"));
        p.setName(rs.getString("product_name"));
        p.setDescription(rs.getString("product_description"));
        p.setActive(rs.getBoolean("active"));
        p.setSortOrder(rs.getInt("sort_order"));
        return p;
    }
    
}
