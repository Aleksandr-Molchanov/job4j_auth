CREATE TABLE IF NOT EXISTS employee(
    id serial primary key not null,
    name varchar(255),
    surname varchar(255),
    inn int,
    date_hiring timestamp without time zone not null default now()
);

CREATE TABLE IF NOT EXISTS person
(
    id serial primary key not null,
    username varchar(2000),
    password varchar(2000),
    employee_id integer references employee(id)
);