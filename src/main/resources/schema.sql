CREATE SCHEMA IF NOT EXISTS cropster;

CREATE TABLE IF NOT EXISTS cropster.facilities (
    id SERIAL PRIMARY KEY,
    name varchar(128) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS cropster.machines (
    id SERIAL PRIMARY KEY,
    name varchar(128) NOT NULL,
    capacity int,
    facility_id int NOT NULL,
    FOREIGN KEY (facility_id) REFERENCES cropster.facilities (id),
    UNIQUE (name,facility_id)
);

CREATE TABLE IF NOT EXISTS cropster.green_coffees (
    id SERIAL PRIMARY KEY,
    name varchar(128) NOT NULL,
    amount int,
    facility_id int NOT NULL,
    FOREIGN KEY (facility_id) REFERENCES cropster.facilities (id)
);

CREATE TABLE IF NOT EXISTS cropster.roasting_processes (
    id SERIAL PRIMARY KEY,
    start_weight decimal(10),
    end_weight decimal(10),
    start_time timestamp,
    end_time timestamp,
    machine_id int,
    green_coffee_id int,
    product_name varchar(128),
    FOREIGN KEY (machine_id) REFERENCES cropster.machines (id),
    FOREIGN KEY (green_coffee_id) REFERENCES cropster.green_coffees (id),
    CHECK (start_weight > end_weight),
    CHECK (end_time > roasting_processes.start_time)
)