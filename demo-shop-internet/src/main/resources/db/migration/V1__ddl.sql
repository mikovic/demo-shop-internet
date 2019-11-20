CREATE DATABASE IF NOT EXISTS geek_db
DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS roles;
CREATE TABLE roles (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(50) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL,
  password char(80) NOT NULL,
  first_name varchar(50) NOT NULL,
  last_name varchar(50) NOT NULL,
  email varchar(50) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS users_roles;

CREATE TABLE users_roles (
  user_id int(11) NOT NULL,
  role_id int(11) NOT NULL,

  PRIMARY KEY (user_id, role_id),

  KEY FK_ROLE_idx (role_id),

  CONSTRAINT FK_USER_05 FOREIGN KEY (user_id)
  REFERENCES users (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT FK_ROLE FOREIGN KEY (role_id)
  REFERENCES roles (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS = 1;
DROP TABLE IF EXISTS products;

CREATE TABLE products (
   id int(11) NOT NULL AUTO_INCREMENT,
    subcategory_id int(11) NOT NULl,
    title VARCHAR(50) NOT NULL,
    brand_id int(11) NOT NULL,
    description VARCHAR(250) NOT NULL,
    price DECIMAL(8,2) NOT NULL,
    create_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT  CHARSET=utf8;

DROP TABLE IF EXISTS categories;
CREATE TABLE categories (
   id int(11) NOT NULL AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    description VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT  CHARSET=utf8;

DROP TABLE IF EXISTS subcategories;
CREATE TABLE subcategories (
   id int(11) NOT NULL AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
   description VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT  CHARSET=utf8mb4;

DROP TABLE IF EXISTS brands;
CREATE TABLE brands (
   id int(11) NOT NULL AUTO_INCREMENT,
    title VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT  CHARSET=utf8;

DROP TABLE IF EXISTS images;
CREATE TABLE images (
   id int(11) NOT NULL AUTO_INCREMENT,
    title VARCHAR(50),
    path VARCHAR(250) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT  CHARSET=utf8;

DROP TABLE IF EXISTS products_categories;

CREATE TABLE categories_products (
  category_id int(11) NOT NULL,
  product_id int(11) NOT NULL,
  CONSTRAINT FOREIGN KEY (category_id)
  REFERENCES categories (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT  FOREIGN KEY (product_id )
  REFERENCES products (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION


) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS categories_images;
CREATE TABLE categories_images (
  category_id int(11) NOT NULL,
  image_id int(11) NOT NULL,

  CONSTRAINT  FOREIGN KEY (category_id )
  REFERENCES categories  (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT FOREIGN KEY (image_id)
  REFERENCES images (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS subcategories_images;
CREATE TABLE subcategories_images (
 subcategory_id int(11) NOT NULL,
 image_id int(11) NOT NULL,

 CONSTRAINT  FOREIGN KEY (subcategory_id )
 REFERENCES subcategories  (id)
 ON DELETE NO ACTION ON UPDATE NO ACTION,

 CONSTRAINT FOREIGN KEY (image_id)
 REFERENCES images (id)
 ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




DROP TABLE IF EXISTS products_images;
CREATE TABLE products_images (
  product_id int(11) NOT NULL,
  image_id int(11) NOT NULL,

  CONSTRAINT  FOREIGN KEY (product_id )
  REFERENCES products (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION,

  CONSTRAINT FOREIGN KEY (image_id)
  REFERENCES images (id)
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS categories_subcategories;
CREATE TABLE categories_subcategories (
    category_id int(11) NOT NULL,
    subcategory_id int(11) NOT NULL,

    CONSTRAINT  FOREIGN KEY (category_id )
    REFERENCES categories  (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FOREIGN KEY (subcategory_id)
    REFERENCES subcategories (id)
     ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE orders_statuses (
                                 id                    INT(11) NOT NULL AUTO_INCREMENT,
                                 title                 VARCHAR(50) DEFAULT NULL,
                                 PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS delivery_addresses;

CREATE TABLE delivery_addresses (
                                    id	                INT(11) NOT NULL AUTO_INCREMENT,
                                    user_id               INT(11) NOT NULL,
                                    address               VARCHAR(500) NOT NULL,
                                    PRIMARY KEY (id),
                                    CONSTRAINT FK_USER_ID_DEL_ADR FOREIGN KEY (user_id)
                                        REFERENCES users (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS orders;

CREATE TABLE orders (
                        id	                INT(11) NOT NULL AUTO_INCREMENT,
                        user_id               INT(11) NOT NULL,
                        price                 DECIMAL(8,2) NOT NULL,
                        delivery_price        DECIMAL(8,2) NOT NULL,
                        delivery_address_id   INT(11) NOT NULL,
                        phone_number          VARCHAR(20) NOT NULL,
                        status_id             INT(11) NOT NULL,
                        delivery_date         TIMESTAMP NULL ,
                        create_at             TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        update_at             TIMESTAMP NULL,
                        PRIMARY KEY (id),
                        CONSTRAINT FK_USER_ID FOREIGN KEY (user_id)
                            REFERENCES users (id),
                        CONSTRAINT FK_STATUS_ID FOREIGN KEY (status_id)
                            REFERENCES orders_statuses (id),
                        CONSTRAINT FK_DELIVERY_ADDRESS_ID FOREIGN KEY (delivery_address_id)
                            REFERENCES delivery_addresses (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

DROP TABLE IF EXISTS orders_item;

CREATE TABLE orders_item (
                             id	                INT(11) NOT NULL AUTO_INCREMENT,
                             product_id            INT(11) NOT NULL,
                             order_id              INT(11) NOT NULL,
                             quantity              INT NOT NULL,
                             item_price            DECIMAL(8,2) NOT NULL,
                             total_price           DECIMAL(8,2) NOT NULL,
                             PRIMARY KEY (id),
                             CONSTRAINT FK_ORDER_ID FOREIGN KEY (order_id)
                                 REFERENCES orders (id),
                             CONSTRAINT FK_PRODUCT_ID_ORD_IT FOREIGN KEY (product_id)
                                 REFERENCES products (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;

