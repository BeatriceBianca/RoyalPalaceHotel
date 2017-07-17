DECLARE n_room NUMBER(10);

BEGIN
   SELECT COUNT(*) INTO n_room FROM tab WHERE tname='ROOM';

   IF (n_room = 0) THEN
      EXECUTE IMMEDIATE
      'CREATE TABLE ROOM (
          ID NUMBER(10) NOT NULL PRIMARY KEY,
          SINGLE_BED NUMBER(10),
          DOUBLE_BED NUMBER(10) )';

      EXECUTE IMMEDIATE
      'CREATE SEQUENCE room_id_incr START WITH 1';

      EXECUTE IMMEDIATE
      'CREATE OR REPLACE TRIGGER ID_ROOM
          BEFORE INSERT ON ROOM
          FOR EACH ROW

          BEGIN
            SELECT room_id_incr.NEXTVAL
            INTO   :new.id
            FROM   dual;
          END; ';
   END IF;
END;

