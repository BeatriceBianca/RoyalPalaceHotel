CREATE TABLE IF NOT EXISTS REQUEST (
          ID INT(10) NOT NULL PRIMARY KEY auto_increment,
          USER_ID INT(10) NOT NULL,
          CUSTOMER_ID INT(10) NOT NULL,
          REQUEST_DATE DATE NOT NULL,
          ARRIVAL_DATE DATE NOT NULL,
          DEPARTURE_DATE DATE NOT NULL,
          FOREIGN KEY (USER_ID) REFERENCES USERS(ID),
          FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(ID) );