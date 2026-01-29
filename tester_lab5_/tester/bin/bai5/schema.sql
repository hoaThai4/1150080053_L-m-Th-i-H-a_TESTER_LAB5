-- PostgreSQL schema for Bai 5
-- Run this in database: lab5

create table if not exists customers (
    id bigserial primary key,
    customer_code varchar(10) not null,
    full_name varchar(50) not null,
    email varchar(254) not null,
    phone varchar(12) not null,
    address varchar(255) not null,
    password_hash varchar(200) not null,
    birthdate date null,
    gender varchar(10) null,
    accepted_terms boolean not null default true,
    created_at timestamptz not null default now(),

    constraint uq_customers_customer_code unique (customer_code),
    constraint uq_customers_email unique (email)
);

create index if not exists idx_customers_phone on customers(phone);
