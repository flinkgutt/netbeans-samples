DROP TABLE IF EXISTS categories CASCADE;
CREATE TABLE categories (
	category_id SERIAL NOT NULL,
	parent_id INTEGER, 
	category_name VARCHAR(100),
	category_description TEXT,
	active BOOLEAN DEFAULT TRUE,
        sort_order INTEGER DEFAULT 0,
PRIMARY KEY("category_id")
);
ALTER TABLE "categories" OWNER TO "netbeans-samples";

DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products (
    product_id SERIAL NOT NULL,
    product_name VARCHAR(100),
    product_description TEXT,
    product_price NUMERIC(6,2) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    sort_order INTEGER DEFAULT 0,
PRIMARY KEY("product_id")
);
ALTER TABLE "products" OWNER TO "netbeans-samples";

DROP TABLE IF EXISTS products_to_categories CASCADE;
CREATE TABLE products_to_categories (
    prod_to_cat_id SERIAL NOT NULL,
    product_id_fk INTEGER NOT NULL,
    category_id_fk INTEGER NOT NULL,
PRIMARY KEY("prod_to_cat_id"),
FOREIGN KEY ("product_id_fk") REFERENCES products ("product_id"),
FOREIGN KEY ("category_id_fk") REFERENCES categories ("category_id")
);
ALTER TABLE "products_to_categories" OWNER TO "netbeans-samples";

-- Insert some test data
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(0, 'BooksPostgreSQL',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(0, 'Movies',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(0, 'Posters',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(0, 'Online courses',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(0, 'Music',null,true);

INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(1, 'Crime',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(1, 'Fantasy',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(1, 'Humor / Satire',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(2, 'Action',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(2, 'Comedy',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(2, 'Drama',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(2, 'Bollywood',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(2, 'Thriller',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(2, 'Romance',null,true);

INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(5, 'Jazz',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(5, 'Disco',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(5, 'Rock',null,true);
INSERT INTO categories (parent_id,category_name, category_description, active) VALUES(5, 'Techno',null,true);


-- Products
INSERT INTO products (product_name, product_description, product_price, active, sort_order) VALUES('The Hitchhikers Guide to the Galaxy','Got your towel?', 15.95,true,0);
INSERT INTO products (product_name, product_description, product_price, active, sort_order) VALUES('Lord of the Rings','One ring to rule them all and in darkness bind them.',15.30, true,0);
INSERT INTO products (product_name, product_description, product_price, active, sort_order) VALUES('A Memory of Light [WoT]','The epic conclusion to the Wheel of Time series',22.78,true,0);
INSERT INTO products (product_name, product_description, product_price, active, sort_order) VALUES('The Eye of the World [WoT]','Book one in the Wheel of Time series',10.92,true,0);
INSERT INTO products (product_name, product_description, product_price, active, sort_order) VALUES('The Shadow Rising [WoT]','Book four in the Wheel of Time series',10.31,true,0);
INSERT INTO products (product_name, product_description, product_price, active, sort_order) VALUES('The Great Hunt [WoT]','Book two in the Wheel of Time series',9.54,true,0);
INSERT INTO products (product_name, product_description, product_price, active, sort_order) VALUES('Lord of Chaos [WoT]','Book six in the Wheel of Time series',13.31,true,0);

INSERT INTO products_to_categories (product_id_fk, category_id_fk) VALUES(1,8);
INSERT INTO products_to_categories (product_id_fk, category_id_fk) VALUES(2,7);
INSERT INTO products_to_categories (product_id_fk, category_id_fk) VALUES(3,7);
INSERT INTO products_to_categories (product_id_fk, category_id_fk) VALUES(4,7);
INSERT INTO products_to_categories (product_id_fk, category_id_fk) VALUES(5,7);
INSERT INTO products_to_categories (product_id_fk, category_id_fk) VALUES(6,7);
INSERT INTO products_to_categories (product_id_fk, category_id_fk) VALUES(7,7);


INSERT INTO products (product_name, product_description, product_price, active, sort_order) VALUES('Shawshank Redemption','On top of the IMDB Top 250 movies of all time',9.0,true,0);
INSERT INTO products (product_name, product_description, product_price, active, sort_order) VALUES('Schindlers List','In Poland during World War II, Oskar Schindler gradually becomes concerned for his Jewish workforce after witnessing their persecution by the Nazis.',9.0,true,0);

INSERT INTO products_to_categories (product_id_fk, category_id_fk) VALUES(8,11);
INSERT INTO products_to_categories (product_id_fk, category_id_fk) VALUES(9,11);
