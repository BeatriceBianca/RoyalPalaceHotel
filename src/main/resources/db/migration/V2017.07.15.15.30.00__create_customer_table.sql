DECLARE n_customer NUMBER(10);

BEGIN
   SELECT COUNT(*) INTO n_customer FROM tab WHERE tname='CUSTOMER';

   IF (n_customer = 0) THEN
      EXECUTE IMMEDIATE
      'CREATE TABLE CUSTOMER (
          ID NUMBER(10) NOT NULL PRIMARY KEY,
          LAST_NAME VARCHAR2(20) NOT NULL ,
          FIRST_NAME VARCHAR2(20) NOT NULL,
          ADDRESS VARCHAR2(100),
          CNP NUMBER(13) NOT NULL,
          GENDER VARCHAR2(20),
          CUSTOMER_PHONE NUMBER(20) ) ';

      EXECUTE IMMEDIATE
      'CREATE SEQUENCE customer_id_incr START WITH 1';

      EXECUTE IMMEDIATE
      'CREATE OR REPLACE TRIGGER ID_CUSTOMER
          BEFORE INSERT ON CUSTOMER
          FOR EACH ROW

          BEGIN
            SELECT customer_id_incr.NEXTVAL
            INTO   :new.id
            FROM   dual;
          END; ';
   END IF;
END;

