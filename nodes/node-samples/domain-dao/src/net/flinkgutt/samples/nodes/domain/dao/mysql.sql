# MySQL netbeans-samples create script
# by Christian Michelsen 21.05.2013

DROP TABLE IF EXISTS `netbeans-samples`.categories;
CREATE TABLE `netbeans-samples`.categories (
	category_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	parent_id INT,
	category_name VARCHAR(100),
	category_description TEXT,
	active BOOL DEFAULT true
)
ENGINE=InnoDB
DEFAULT CHARSET=utf8
COLLATE=utf8_unicode_ci;

INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(0, "Category #1",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(0, "Category #2",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(0, "Category #3",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(0, "Category #4",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(0, "Category #5",null,true);

INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(1, "Category #6",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(2, "Category #7",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(2, "Category #8",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(2, "Category #9",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(3, "Category #10",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(3, "Category #11",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(3, "Category #12",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(5, "Category #13",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(5, "Category #14",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(5, "Category #15",null,true);
INSERT INTO `netbeans-samples`.categories (parent_id,category_name, category_description, active) VALUES(5, "Category #16",null,true);