package net.flinkgutt.samples.nodes.domain.dao;

import java.util.ArrayList;
import java.util.Arrays;
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
        String getCategoriesWithParentQuery = "SELECT category_id, category_name, category_description, active, sort_order FROM categories WHERE parent_id=:parentId ORDER BY sort_order ASC, category_name ASC";
        MapSqlParameterSource params = new MapSqlParameterSource("parentId", parent.getCategoryID());
        List<Category> children = getJdbcTemplate().query(getCategoriesWithParentQuery, params, new CategoryRowMapper(parent));
        List<ICategory> c = Arrays.asList(children.toArray(new ICategory[children.size()]));
        parent.setChildren(c);
        return children;
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
        int rowCount = getJdbcTemplate().update(deleteCategoryQuery, params);
        if (rowCount == 1) {
            category.getParent().removeChild(category);
        }
    }

    @Override
    public boolean hasChildren(Category category) {
        String hasChildrenQuery = "SELECT COUNT(category_id) FROM categories WHERE parent_id=:parentId";
        MapSqlParameterSource params = new MapSqlParameterSource("parentId", category.getCategoryID());
        Integer rowCount = getJdbcTemplate().queryForObject(hasChildrenQuery, params, Integer.class);
        return rowCount > 0;
    }

    @Override
    public boolean update(Category category) {
        String updateCategoryQuery = "UPDATE categories "
                + "SET category_name=:name, category_description=:description, parent_id=:parentId, sort_order=:sortOrder "
                + "WHERE category_id=:categoryId";
        MapSqlParameterSource params = new MapSqlParameterSource("name", category.getName())
                .addValue("description", category.getDescription())
                .addValue("parentId", category.getParentID())
                .addValue("categoryId", category.getCategoryID())
                .addValue("sortOrder", category.getSortOrder());
        int rowsAffected = getJdbcTemplate().update(updateCategoryQuery, params);
        return rowsAffected > 0;
    }

    @Override
    public void updateChildrenSortOrder(Category parent) {
        String updateSortOrderQuery = "UPDATE categories SET sort_order = :sortOrder WHERE category_id = :categoryId";
        MapSqlParameterSource[] batchArgs = new MapSqlParameterSource[parent.getChildren().size()];
        
        for (int i = 0; i < parent.getChildren().size(); i++) {
            ICategory category = parent.getChildren().get(i);
            MapSqlParameterSource params = new MapSqlParameterSource("sortOrder", category.getSortOrder()).addValue("categoryId", category.getCategoryID());
            batchArgs[i] = params;
        }

        getJdbcTemplate().batchUpdate(updateSortOrderQuery, batchArgs);
    }

    @Override
    public boolean addCategory(Category category) {
        String insertCategoryQuery = "INSERT INTO categories (category_name, category_description, parent_id, sort_order ) VALUES(:name, :description, :parentId, :sortOrder)";
        MapSqlParameterSource params = new MapSqlParameterSource("name", category.getName())
                .addValue("description", category.getDescription())
                .addValue("parentId", category.getParentID())
                .addValue("sortOrder", category.getSortOrder());
        KeyHolder key = new GeneratedKeyHolder();
        int rowsAffected = getJdbcTemplate().update(insertCategoryQuery, params, key);
        if (rowsAffected != 1) {
            return false;
        }
        category.setCategoryID(key.getKey().intValue());
        return true;
    }
}
