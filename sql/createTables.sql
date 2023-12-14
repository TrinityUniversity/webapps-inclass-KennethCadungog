CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username varchar(20) NOT NULL,
    password varchar(200) NOT NULL
);

CREATE TABLE messages (
    text varchar(2000) NOT NULL 
    sender varchar (30) NOT NULL
    receiver varchar (30) NOT NULL
);