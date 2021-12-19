CREATE TABLE account (
  account_id BIGINT NOT NULL,
  created DATETIME,
  updated DATETIME,
  amount VARCHAR(255),
  currency VARCHAR(255) NOT NULL,
  user_id BIGINT,
  PRIMARY KEY (account_id)
) ENGINE = InnoDB;
CREATE TABLE account_seq (next_val BIGINT) ENGINE = InnoDB;
INSERT INTO
  account_seq
VALUES
  (1);
CREATE TABLE domestic_currency (
    domestic_currency_id BIGINT NOT NULL,
    created DATETIME,
    updated DATETIME,
    is_active BIT NOT NULL,
    domestic_currency_cost VARCHAR(255) NOT NULL,
    domestic_currency_title VARCHAR(255) NOT NULL,
    PRIMARY KEY (domestic_currency_id)
  ) ENGINE = InnoDB;
CREATE TABLE domestic_currency_seq (next_val BIGINT) ENGINE = InnoDB;
INSERT INTO
  domestic_currency_seq
VALUES
  (1);
CREATE TABLE inventory (
    inventory_id BIGINT NOT NULL,
    created DATETIME,
    updated DATETIME,
    PRIMARY KEY (inventory_id)
  ) ENGINE = InnoDB;
CREATE TABLE inventory_seq (next_val BIGINT) ENGINE = InnoDB;
INSERT INTO
  inventory_seq
VALUES
  (1);
CREATE TABLE inventory_unit (
    unit_id BIGINT NOT NULL,
    created DATETIME,
    updated DATETIME,
    amount_items VARCHAR(255),
    item_id BIGINT,
    inventory_id BIGINT,
    PRIMARY KEY (unit_id)
  ) ENGINE = InnoDB;
CREATE TABLE item (
    item_id BIGINT NOT NULL,
    created DATETIME,
    updated DATETIME,
    is_active BIT NOT NULL,
    description VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    PRIMARY KEY (item_id)
  ) ENGINE = InnoDB;
CREATE TABLE item_seq (next_val BIGINT) ENGINE = InnoDB;
INSERT INTO
  item_seq
VALUES
  (1);
CREATE TABLE price (
    price_id BIGINT NOT NULL,
    created DATETIME,
    updated DATETIME,
    is_active BIT NOT NULL,
    cost VARCHAR(255),
    domestic_currency_id BIGINT,
    item_id BIGINT,
    PRIMARY KEY (price_id)
  ) ENGINE = InnoDB;
CREATE TABLE price_seq (next_val BIGINT) ENGINE = InnoDB;
INSERT INTO
  price_seq
VALUES
  (1);
CREATE TABLE refresh_token (
    token_id BIGINT NOT NULL,
    created DATETIME,
    updated DATETIME,
    expiry_dt DATETIME NOT NULL,
    refresh_count BIGINT,
    token VARCHAR(255) NOT NULL,
    user_device_id BIGINT NOT NULL,
    PRIMARY KEY (token_id)
  ) ENGINE = InnoDB;
CREATE TABLE refresh_token_seq (next_val BIGINT) ENGINE = InnoDB;
INSERT INTO
  refresh_token_seq
VALUES
  (1);
CREATE TABLE role (
    role_id BIGINT NOT NULL auto_increment,
    role_name VARCHAR(255),
    PRIMARY KEY (role_id)
  ) ENGINE = InnoDB;
CREATE TABLE unit_seq (next_val BIGINT) ENGINE = InnoDB;
INSERT INTO
  unit_seq
VALUES
  (1);
CREATE TABLE user (
    user_id BIGINT NOT NULL,
    created DATETIME,
    updated DATETIME,
    is_active BIT NOT NULL,
    avatar VARCHAR(255),
    balance VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    is_email_verified BIT NOT NULL,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    inventory_id BIGINT NOT NULL,
    PRIMARY KEY (user_id)
  ) ENGINE = InnoDB;
CREATE TABLE user_authority (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id)
  ) ENGINE = InnoDB;
CREATE TABLE user_device (
    user_device_id BIGINT NOT NULL,
    created DATETIME,
    updated DATETIME,
    device_id VARCHAR(255) NOT NULL,
    device_type VARCHAR(255),
    is_refresh_active BIT,
    notification_token VARCHAR(255),
    user_id BIGINT NOT NULL,
    PRIMARY KEY (user_device_id)
  ) ENGINE = InnoDB;
CREATE TABLE user_device_seq (next_val BIGINT) ENGINE = InnoDB;
INSERT INTO
  user_device_seq
VALUES
  (1);
CREATE TABLE user_seq (next_val BIGINT) ENGINE = InnoDB;
INSERT INTO
  user_seq
VALUES
  (1);
CREATE TABLE verification_token (
    token_id BIGINT NOT NULL auto_increment,
    expiry_dt DATETIME NOT NULL,
    token VARCHAR(255) NOT NULL,
    token_status VARCHAR(255),
    user_id BIGINT NOT NULL,
    PRIMARY KEY (token_id)
  ) ENGINE = InnoDB;
ALTER TABLE
  domestic_currency
ADD
  CONSTRAINT UK_k02teyvggt4asf6d8t9sy03ir UNIQUE (domestic_currency_title);
ALTER TABLE
  refresh_token
ADD
  CONSTRAINT UK_8ogx3ejsbfbf2xsgl4758otrm UNIQUE (user_device_id);
ALTER TABLE
  refresh_token
ADD
  CONSTRAINT UK_r4k4edos30bx9neoq81mdvwph UNIQUE (token);
ALTER TABLE
  role
ADD
  CONSTRAINT UK_epk9im9l9q67xmwi4hbed25do UNIQUE (role_name);
ALTER TABLE
  user
ADD
  CONSTRAINT UK_2its1oh0ku235njjgsxsgc64m UNIQUE (inventory_id);
ALTER TABLE
  user
ADD
  CONSTRAINT UK_ob8kqyqqgmefl0aco34akdtpe UNIQUE (email);
ALTER TABLE
  user
ADD
  CONSTRAINT UK_sb8bbouer5wak8vyiiy4pf2bx UNIQUE (username);
ALTER TABLE
  verification_token
ADD
  CONSTRAINT UK_p678btf3r9yu6u8aevyb4ff0m UNIQUE (token);
ALTER TABLE
  account
ADD
  CONSTRAINT FK7m8ru44m93ukyb61dfxw0apf6 FOREIGN KEY (user_id) REFERENCES user (user_id);
ALTER TABLE
  inventory_unit
ADD
  CONSTRAINT FKg63n1jatlwrrdjh69sqw4qimd FOREIGN KEY (item_id) REFERENCES item (item_id);
ALTER TABLE
  inventory_unit
ADD
  CONSTRAINT FKaaxbal8bbo4bq638oj8kb83ir FOREIGN KEY (inventory_id) REFERENCES inventory (inventory_id);;
ALTER TABLE
  price
ADD
  CONSTRAINT FK4ft2mb2yj1jpi3sw1ltcw88sp FOREIGN KEY (domestic_currency_id) REFERENCES domestic_currency (domestic_currency_id);
ALTER TABLE
  price
ADD
  CONSTRAINT FKndp5uoh7s7ibg1yjtbe9u6jc7 FOREIGN KEY (item_id) REFERENCES item (item_id);
ALTER TABLE
  refresh_token
ADD
  CONSTRAINT FKr92opronarwe7pn1c41621grv FOREIGN KEY (user_device_id) REFERENCES user_device (user_device_id);
ALTER TABLE
  user
ADD
  CONSTRAINT FKalo4ugar5xpb802kfh7lqlcj4 FOREIGN KEY (inventory_id) REFERENCES inventory (inventory_id);
ALTER TABLE
  user_authority
ADD
  CONSTRAINT FKash3fy9hdayq3o73fir11n3th FOREIGN KEY (role_id) REFERENCES role (role_id);
ALTER TABLE
  user_authority
ADD
  CONSTRAINT FKpqlsjpkybgos9w2svcri7j8xy FOREIGN KEY (user_id) REFERENCES user (user_id);
ALTER TABLE
  user_device
ADD
  CONSTRAINT FKd2lb0k09c4nnfpvku8r61g92n FOREIGN KEY (user_id) REFERENCES user (user_id);
ALTER TABLE
  verification_token
ADD
  CONSTRAINT FKrdn0mss276m9jdobfhhn2qogw FOREIGN KEY (user_id) REFERENCES user (user_id);