package net.flinkgutt.samples.nodes.domain.dao;

import java.util.ArrayList;
import java.util.List;
import net.flinkgutt.samples.nodes.api.IProductDAO;
import net.flinkgutt.samples.nodes.domain.entities.Category;
import net.flinkgutt.samples.nodes.domain.entities.Product;
import org.openide.util.lookup.ServiceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 *
 * @author Christian
 */
@ServiceProvider( service = IProductDAO.class )
public class ProductDAO extends SuperDAO implements IProductDAO<Product, Category> {

    @Override
    public List<Product> getProducts(Category category) {
        if(category == null) {
            return new ArrayList<Product>();
        }
        String getProductsInCategoryQuery = "SELECT p.* FROM products p LEFT JOIN products_to_categories cp ON p.product_id=cp.product_id_fk WHERE cp.category_id_fk=:categoryId";
        MapSqlParameterSource params = new MapSqlParameterSource("categoryId", category.getCategoryID());
        return getJdbcTemplate().query(getProductsInCategoryQuery, params, new ProductRowMapper());
    }

    @Override
    public boolean save(Product product) {
        // TODO Update products_to_categories table as well. It's not currently possible to change a products category, so it's not implemented yet.
        if(product == null) {
            throw new IllegalArgumentException("Product can't be null");
        }
        // If product does not exist, we should hand it over to another method to handle it's insert.
        if(product.getProductID() == 0) {
            // insertProduct-method
            return false;
        }
        String updateProductQuery = "UPDATE products SET product_name = :name, product_description = :description, product_price=:price, active=:active, sort_order=:sortOrder "
                + "WHERE product_id = :productId";
        MapSqlParameterSource params = new MapSqlParameterSource("name", product.getName());
        params.addValue("description", product.getDescription()).
                addValue("price", product.getPrice()).
                addValue("active", product.isActive()).
                addValue("sortOrder",product.getSortOrder()).
                addValue("productId", product.getProductID());
        int rowsAffected = getJdbcTemplate().update(updateProductQuery, params);
        if(rowsAffected > 0) {
            return true;
        }
        return false;
    }
}
