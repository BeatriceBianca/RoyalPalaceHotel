DELIMITER $$
CREATE PROCEDURE Alter_Room()
    BEGIN
        DECLARE _count INT;
        SET _count = (  SELECT COUNT(*)
                        FROM INFORMATION_SCHEMA.COLUMNS
                        WHERE   TABLE_NAME = 'ROOM' AND
                                COLUMN_NAME = 'NAME_ROOM');
        IF _count = 0 THEN
            ALTER TABLE ROOM
                ADD COLUMN NAME_ROOM VARCHAR(20);
        END IF;
    END $$
DELIMITER ;

CALL Alter_Room();

DROP PROCEDURE Alter_Room;