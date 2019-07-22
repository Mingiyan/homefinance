create table currency_tbl (
  id serial primary key not null,
  name varchar(20) not null
);

create table account_tbl (
  id serial primary key not null,
  name varchar(50) not null,
  amount decimal(15, 2) not null,
  currency_id int not null
);

ALTER TABLE account_tbl
ADD CONSTRAINT currency_fk
FOREIGN KEY (currency_id) REFERENCES currency_tbl (id);

create table category_tbl (
  id serial primary key not null,
  name varchar(30) not null
);