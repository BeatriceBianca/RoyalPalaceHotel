package com.hotel.royalpalace.service;

import com.hotel.royalpalace.model.Room;
import com.hotel.royalpalace.model.RoomType;

import java.util.List;

public interface RoomsService {

    List<Room> getAllRooms();

    List<Room> getAllRoomsByFloor(int floor);

    List<RoomType> getAllRoomTypes();

    void editRoomType(Room room);
}
