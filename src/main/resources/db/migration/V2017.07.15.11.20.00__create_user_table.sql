CREATE TABLE USERS (
          ID INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
          LAST_NAME VARCHAR(20) NOT NULL ,
          FIRST_NAME VARCHAR(20) NOT NULL,
          USER_ROLE VARCHAR(20) NOT NULL,
          BIRTH_DATE DATE,
          HIRE_DATE DATE NOT NULL,
          DEPARTURE_DATE DATE,
          PHONE_NUMBER VARCHAR(20),
          USER_EMAIL VARCHAR(100) NOT NULL,
          USER_PASSWORD VARCHAR(200) NOT NULL );