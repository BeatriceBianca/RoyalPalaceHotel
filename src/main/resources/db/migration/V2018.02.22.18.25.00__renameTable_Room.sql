DELIMITER $$
CREATE PROCEDURE Rename_Room()
  BEGIN
    DECLARE _count INT;
    SET _count = (  SELECT COUNT(*)
                    FROM INFORMATION_SCHEMA.TABLES
                    WHERE   TABLE_NAME = 'ROOMS_TYPE');
    IF _count = 0 THEN
      RENAME TABLE ROOM TO ROOMS_TYPE;
    END IF;
  END $$
DELIMITER ;
