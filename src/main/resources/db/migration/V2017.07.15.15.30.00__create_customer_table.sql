CREATE TABLE CUSTOMER (
          ID INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
          LAST_NAME VARCHAR(20) NOT NULL ,
          FIRST_NAME VARCHAR(20) NOT NULL,
          ADDRESS VARCHAR(100),
          CNP VARCHAR(20) NOT NULL,
          GENDER VARCHAR(20),
          CUSTOMER_PHONE INT);



