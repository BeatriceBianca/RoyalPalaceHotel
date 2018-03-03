DELIMITER $$
CREATE PROCEDURE Alter_Price()
  BEGIN
    DECLARE _count INT;
    SET _count = (  SELECT COUNT(*)
                    FROM INFORMATION_SCHEMA.COLUMNS
                    WHERE   TABLE_NAME = 'ROOMS_TYPE' AND
                            COLUMN_NAME = 'PRICE');
    IF _count = 0 THEN
      ALTER TABLE ROOMS_TYPE
        ADD COLUMN PRICE FLOAT(10,2) default 0.0;
    END IF;
  END $$
DELIMITER ;

CALL Alter_Price();

drop procedure Alter_Price;
