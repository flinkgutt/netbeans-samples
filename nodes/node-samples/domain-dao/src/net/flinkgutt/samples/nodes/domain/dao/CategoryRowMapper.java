/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.flinkgutt.samples.nodes.domain.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import net.flinkgutt.samples.nodes.domain.entities.Category;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Christian
 */
class CategoryRowMapper implements RowMapper<Category> {

    private Category parent;
    public CategoryRowMapper() {
    }
    public CategoryRowMapper(Category parent) {
        this.parent = parent;
    }


    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {

        Category c = new Category();
        c.setCategoryID(rs.getInt("category_id"));
        c.setName(rs.getString("category_name"));
        c.setDescription(rs.getString("category_description"));
        c.setSortOrder(rs.getInt("sort_order"));
        if(parent != null) {
            c.setParent(parent);
        }
        return c;
    }

    
}
