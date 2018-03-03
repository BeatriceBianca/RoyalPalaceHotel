package com.hotel.royalpalace.repository;

import com.hotel.royalpalace.model.Room;
import com.hotel.royalpalace.model.RoomType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {

    @Query(value = "SELECT DISTINCT (r) from Room r")
    List<Room> getAllRooms();

    @Query(value = "SELECT DISTINCT (t) FROM RoomType t")
    List<RoomType> getAllRoomTypes();

}
