create table currency_tbl (
  id serial primary key not null,
  name varchar(20) not null
);

create table account_tbl (
  id serial primary key not null,
  name varchar(50) not null,
  amount decimal(15, 2) not null,
  type varchar(20) not null,
  currency_id int not null
);

ALTER TABLE account_tbl
ADD CONSTRAINT currency_fk
FOREIGN KEY (currency_id) REFERENCES currency_tbl (id);

create table category_tbl (
  id serial primary key not null,
  name varchar(30) not null,
  category_id int not null
);

alter table category_tbl
add constraint category_tbl_fk
foreign key (category_id) references category_tbl (id);

create table transaction_tbl (
    id serial primary key not null,
    name varchar(50) not null,
    date_time date not null,
    category_id int not null,
    account_id int not null,
    currency_id int not null
);

alter table transaction_tbl
add constraint transaction_to_category_fk
foreign key (category_id) references category_tbl;

alter table transaction_tbl
add constraint transaction_to_account_fk
foreign key (account_id) references account_tbl;
