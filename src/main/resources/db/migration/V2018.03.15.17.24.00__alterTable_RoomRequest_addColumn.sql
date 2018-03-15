DELIMITER $$
CREATE PROCEDURE Alter_RoomRequest()
  BEGIN
    DECLARE _count INT;
    SET _count = (  SELECT COUNT(*)
                    FROM INFORMATION_SCHEMA.COLUMNS
                    WHERE   TABLE_NAME = 'ROOM_REQUEST' AND
                            COLUMN_NAME = 'ID');
    IF _count = 0 THEN
      ALTER TABLE ROOM_REQUEST
        ADD COLUMN ID INT(10) NOT NULL PRIMARY KEY auto_increment;
    END IF;
  END $$
DELIMITER ;

CALL Alter_RoomRequest();

drop procedure Alter_RoomRequest;
