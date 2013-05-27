DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
	category_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	parent_id INT,
	category_name VARCHAR(100),
	category_description TEXT,
	active BOOL DEFAULT true,
        sort_order INT DEFAULT 0
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_unicode_ci;

# Insert some test data
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(0, "Books",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(0, "Movies",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(0, "Posters",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(0, "Online courses",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(0, "Music",null,true,0);

INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(1, "Crime",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(2, "Action",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(2, "Comedy",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(2, "Drama",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(3, "Thriller",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(3, "Romance",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(3, "Bollywood",null,true,0);

INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(5, "Jazz",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(5, "Disco",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(5, "Rock",null,true,0);
INSERT INTO categories (parent_id,category_name, category_description, active, sort_order) VALUES(5, "Techno",null,true,0);
