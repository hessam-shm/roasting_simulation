create schema cropster;

CREATE TABLE facilities (
    id SERIAL PRIMARY KEY,
    name varchar(128) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (name)
);

CREATE TABLE machines (
    id SERIAL PRIMARY KEY,
    name varchar(128) NOT NULL,
    capacity int,
    facility_id int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (facility_id) REFERENCES facilities (id),
    UNIQUE (name,facility_id)
);

CREATE TABLE green_coffees (
    id SERIAL PRIMARY KEY,
    name varchar(128) NOT NULL,
    amount int,
    facility_id int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (facility_id) REFERENCES facilities (id)
);

CREATE TABLE roasting_processes (
    id SERIAL PRIMARY KEY,
    start_weight decimal(10),
    end_weight decimal(10),
    start_time timestamp,
    end_time timestamp,
    machine_id int,
    green_coffee_id int,
    product_name varchar(128),
    PRIMARY KEY (id),
    FOREIGN KEY (machine_id) REFERENCES machines (id),
    FOREIGN KEY (green_coffee_id) REFERENCES green_coffees (id),
    CHECK (start_weight > end_weight),
    CHECK (end_time > roasting_processes.start_time)
)