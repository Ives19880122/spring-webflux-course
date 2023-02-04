CREATE TABLE users(
    id bigint auto_increment,
    name varchar(50),
    balance int,
    primary key(id)
);

CREATE TABLE user_transaction(
    id bigint auto_increment,
    user_id bigint,
    amount int,
    transaction_date timestamp,
    -- 修正異常，避免刪除bug
    foreign key (user_id) references users(id) on delete cascade
);

INSERT INTO users
    (name, balance)
    values
    ('sam',1000),
    ('mike',1200),
    ('jake',800),
    ('marshal',2000);