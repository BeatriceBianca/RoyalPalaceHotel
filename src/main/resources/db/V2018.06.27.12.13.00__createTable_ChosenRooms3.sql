CREATE TABLE CHOSEN_ROOMS3 (
  ID INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
  ROOM_ID INT NOT NULL,
  SESSION_ID VARCHAR(100)
FOREIGN KEY (ROOM_ID) REFERENCES HOTEL_ROOMS(id) );