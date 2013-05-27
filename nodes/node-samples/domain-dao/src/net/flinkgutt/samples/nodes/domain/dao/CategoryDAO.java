package net.flinkgutt.samples.nodes.domain.dao;

import java.util.ArrayList;
import java.util.List;
import net.flinkgutt.samples.nodes.api.ICategory;

import net.flinkgutt.samples.nodes.api.ICategoryDAO;
import net.flinkgutt.samples.nodes.domain.entities.Category;
import org.openide.util.lookup.ServiceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 *
 * @author Christian
 */
@ServiceProvider(service = ICategoryDAO.class)
public class CategoryDAO extends SuperDAO implements ICategoryDAO<Category> {

    private Category root;

    public CategoryDAO() {
    }

    @Override
    public List<Category> getCategoriesWithParent(Category parent) {
        String getCategoriesWithParentQuery = "SELECT category_id, category_name, category_description, active, sort_order FROM categories WHERE parent_id=:parentId";
        MapSqlParameterSource params = new MapSqlParameterSource("parentId", parent.getCategoryID());
        return jdbcTemplate.query(getCategoriesWithParentQuery, params, new CategoryRowMapper(parent));
    }

    @Override
    public Category createCategory(Category parent, String name) {
        return new Category(null, parent, name);
    }

    @Override
    public Category getRootCategory() {
        if (root == null) {
            root = new Category(0, null, "ROOT");
            root.setChildren(new ArrayList<ICategory>(this.getCategoriesWithParent(root)));
        }
        return root;
    }

    @Override
    public void deleteCategory(Category category) {
        if (hasChildren(category)) {
            // Notify the user
            // TODO Look into if deleteCategory should return a boolean or some some other more rich object
            return;
        }
        String deleteCategoryQuery = "DELETE FROM categories WHERE category_id=:categoryId";
        MapSqlParameterSource params = new MapSqlParameterSource("categoryId", category.getCategoryID());
        int rowCount = jdbcTemplate.update(deleteCategoryQuery, params);
        System.out.println("deleteCategory->rowCount: " + rowCount);
        if (rowCount == 1) {
            category.getParent().removeChild(category);
        }
    }

    @Override
    public boolean hasChildren(Category category) {
        String hasChildrenQuery = "SELECT COUNT(category_id) FROM categories WHERE parent_id=:parentId";
        MapSqlParameterSource params = new MapSqlParameterSource("parentId", category.getCategoryID());
        Integer rowCount = jdbcTemplate.queryForObject(hasChildrenQuery, params, Integer.class);
        if (rowCount > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void update(Category category) {
        String updateCategoryQuery = "UPDATE categories "
                + "SET category_name=:name, category_description=:description, parent_id=:parentId, sort_order=:sortOrder " 
                + "WHERE category_id=:categoryId";
        MapSqlParameterSource params = new MapSqlParameterSource("name", category.getName())
                .addValue("description", category.getDescription())
                .addValue("parentId",category.getParentID())
                .addValue("categoryId",category.getCategoryID())
                .addValue("sortOrder", category.getSortOrder());
        int rowsAffected = jdbcTemplate.update(updateCategoryQuery, params);
        
        // TODO Look into if this function should return a boolean
    }
    
    @Override
    public boolean addCategory(Category category) {
        String insertCategoryQuery = "INSERT INTO categories (category_name, category_description, parent_id, sort_order ) VALUES(:name, :description, :parentId, :sortOrder)";
        MapSqlParameterSource params = new MapSqlParameterSource("name", category.getName())
                .addValue("description", category.getDescription())
                .addValue("parentId",category.getParentID() )
                .addValue("sortOrder",category.getSortOrder())
                ;
        KeyHolder key = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(insertCategoryQuery, params, key);
        if(rowsAffected != 1) {
            return false;
        }
        category.setCategoryID(key.getKey().intValue());
        return true;
    }
    
}
