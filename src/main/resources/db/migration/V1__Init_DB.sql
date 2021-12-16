

create table account (
	account_id bigint not null, 
	created datetime, 
	updated datetime, 
	amount varchar(255), 
	currency varchar(255), 
	user_id bigint, 
	primary key (account_id)
	) engine=InnoDB;

create table account_seq (
	next_val bigint
	) engine=InnoDB;
insert into account_seq values ( 1 );

create table domestic_currency (
	domestic_currency_id bigint not null, 
	created datetime, 
	updated datetime, 
	is_active bit not null, 
	domestic_currency_price varchar(255) not null, 
	domestic_currency_title varchar(255), 
	primary key (domestic_currency_id)
	) engine=InnoDB;

create table domestic_currency_seq (
	next_val bigint
	) engine=InnoDB;
insert into domestic_currency_seq values ( 1 );

create table inventory (
	inventory_id bigint not null, 
	created datetime, 
	updated datetime, 
	primary key (inventory_id)
	) engine=InnoDB;

create table inventory_seq (
	next_val bigint
	) engine=InnoDB;
insert into inventory_seq values ( 1 );
insert into inventory_seq values ( 1 );
create table inventory_unit (
	unit_id bigint not null, 
	created datetime, 
	updated datetime, 
	amount_items varchar(255), 
	item_id bigint, 
	inventory_id bigint, 
	primary key (unit_id)
	) engine=InnoDB;

create table item (
	item_id bigint not null, 
	created datetime, 
	updated datetime, 
	is_active bit not null, 
	description varchar(255), 
	name varchar(255) not null, 
	type varchar(255) not null, 
	primary key (item_id)
	) engine=InnoDB;
create table item_seq (
	next_val bigint
	) engine=InnoDB;
insert into item_seq values ( 1 );

create table price (
	price_id bigint not null, 
	created datetime, 
	updated datetime, 
	is_active bit, 
	cost varchar(255), 
	domestic_currency_id bigint, 
	item_id bigint, 
	primary key (price_id)
	) engine=InnoDB;
create table price_seq (
	next_val bigint
	) engine=InnoDB;
insert into price_seq values ( 1 );

create table refresh_token (
	token_id bigint not null, 
	created datetime, 
	updated datetime, 
	expiry_dt datetime not null, 
	refresh_count bigint, 
	token varchar(255) not null, 
	user_device_id bigint not null, 
	primary key (token_id)
	) engine=InnoDB;
create table refresh_token_seq (
	next_val bigint
	) engine=InnoDB;
insert into refresh_token_seq values ( 1 );

create table role (
	role_id bigint not null auto_increment, 
	role_name varchar(255), 
	primary key (role_id)
	) engine=InnoDB;
create table unit_seq (
	next_val bigint
	) engine=InnoDB;
insert into unit_seq values ( 1 );

create table user (
	user_id bigint not null, 
	created datetime, 
	updated datetime, 
	is_active bit not null, 
	avatar varchar(255), 
	balance varchar(255), 
	email varchar(255), 
	is_email_verified bit not null, 
	password varchar(255) not null, 
	username varchar(255) not null, 
	inventory_id bigint not null, 
	primary key (user_id)
	) engine=InnoDB;
	
create table user_authority (
	user_id bigint not null, 
	role_id bigint not null, 
	primary key (user_id, role_id)
	) engine=InnoDB;
create table user_device (
	user_device_id bigint not null, 
	created datetime, 
	updated datetime, 
	device_id varchar(255) not null, 
	device_type varchar(255), 
	is_refresh_active bit, 
	notification_token varchar(255), 
	user_id bigint not null, 
	primary key (user_device_id)
	) engine=InnoDB;
create table user_device_seq (
	next_val bigint
	) engine=InnoDB;
insert into user_device_seq values ( 1 );
create table user_seq (
	next_val bigint
	) engine=InnoDB;
insert into user_seq values ( 1 );
create table verification_token (
	token_id bigint not null auto_increment, 
	expiry_dt datetime not null, 
	token varchar(255) not null, 
	token_status varchar(255),
	user_id bigint not null, 
	primary key (token_id)
	) engine=InnoDB;
alter table domestic_currency add constraint UK_k02teyvggt4asf6d8t9sy03ir unique (domestic_currency_title);
alter table refresh_token add constraint UK_8ogx3ejsbfbf2xsgl4758otrm unique (user_device_id);
alter table refresh_token add constraint UK_r4k4edos30bx9neoq81mdvwph unique (token);
alter table role add constraint UK_epk9im9l9q67xmwi4hbed25do unique (role_name);
alter table user add constraint UK_2its1oh0ku235njjgsxsgc64m unique (inventory_id);
alter table user add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email);
alter table user add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username);
alter table verification_token add constraint UK_p678btf3r9yu6u8aevyb4ff0m unique (token);
alter table account add constraint FK7m8ru44m93ukyb61dfxw0apf6 foreign key (user_id) references user (user_id);
alter table inventory_unit add constraint FKg63n1jatlwrrdjh69sqw4qimd foreign key (item_id) references item (item_id);
alter table inventory_unit add constraint FKaaxbal8bbo4bq638oj8kb83ir foreign key (inventory_id) references inventory (inventory_id);
;alter table price add constraint FK4ft2mb2yj1jpi3sw1ltcw88sp foreign key (domestic_currency_id) references domestic_currency (domestic_currency_id);
alter table price add constraint FKndp5uoh7s7ibg1yjtbe9u6jc7 foreign key (item_id) references item (item_id);
alter table refresh_token add constraint FKr92opronarwe7pn1c41621grv foreign key (user_device_id) references user_device (user_device_id);
alter table user add constraint FKalo4ugar5xpb802kfh7lqlcj4 foreign key (inventory_id) references inventory (inventory_id);
alter table user_authority add constraint FKash3fy9hdayq3o73fir11n3th foreign key (role_id) references role (role_id);
alter table user_authority add constraint FKpqlsjpkybgos9w2svcri7j8xy foreign key (user_id) references user (user_id);
alter table user_device add constraint FKd2lb0k09c4nnfpvku8r61g92n foreign key (user_id) references user (user_id);
alter table verification_token add constraint FKrdn0mss276m9jdobfhhn2qogw foreign key (user_id) references user (user_id);

