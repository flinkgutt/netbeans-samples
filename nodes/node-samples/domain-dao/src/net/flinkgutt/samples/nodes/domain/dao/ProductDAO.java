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
        return jdbcTemplate.query(getProductsInCategoryQuery, params, new ProductRowMapper());
    }
    
}
