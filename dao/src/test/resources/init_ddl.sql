create table if not exists currency_tbl (
  id serial primary key not null,
  name varchar(20) not null
);

create table if not exists account_tbl (
  id serial primary key not null,
  name varchar(50) not null,
  amount decimal(15),
  type varchar(20),
  currency_id int
);

alter table account_tbl
add constraint if not exists currency_fk
foreign key (currency_id) references currency_tbl (id);

create table if not exists category_tbl (
  id serial primary key not null,
  name varchar(30) not null,
  category_id int
);

alter table category_tbl
add constraint if not exists category_tbl_fk
foreign key (category_id) references category_tbl (id);

create table if not exists transaction_tbl (
  id serial primary key not null,
  name varchar(50) not null,
  date_time date not null,
  account_id int
);

alter table transaction_tbl
add constraint if not exists transaction_to_account_fk
foreign key (account_id) references account_tbl;

create table if not exists transaction_category_tbl (
  transaction_id int not null,
  category_id int not null
);

alter table transaction_category_tbl
add constraint if not exists transaction_fk
foreign key (transaction_id) references transaction_tbl;

alter table transaction_category_tbl
add constraint if not exists category_fk
foreign key (category_id) references category_tbl;