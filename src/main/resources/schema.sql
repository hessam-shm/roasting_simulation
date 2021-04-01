create schema cropster;

CREATE TABLE facilities (
    id int NOT NULL,
    name varchar(128) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE machines (
    id int NOT NULL,
    name varchar(128) NOT NULL,
    capacity int,
    facility_id int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (facility_id) REFERENCES facilities (id),
    UNIQUE (name,facility_id)
);

CREATE TABLE green_coffees (
    id int NOT NULL,
    name varchar(128) NOT NULL,
    amount int,
    facility_id int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (facility_id) REFERENCES facilities (id)
);

CREATE TABLE roasting_processes (
    id int NOT NULL,
    start_weigh decimal(10),
    end_weight decimal(10),
    start_time timestamp,
    end_date timestamp,
    green_coffee_id int,
    product_name varchar(128),
    PRIMARY KEY (id),
    FOREIGN KEY (green_coffee_id) REFERENCES green_coffees (id),
    CHECK (start_weigh > end_weight)
)