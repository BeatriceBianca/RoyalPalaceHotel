package com.hotel.royalpalace.repository;

import com.hotel.royalpalace.model.ChosenRooms;
import com.hotel.royalpalace.model.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChosenRoomRepository extends CrudRepository<ChosenRooms, Long>{

    List<ChosenRooms> findByRoom(Room room);

    List<ChosenRooms> findAll();
}
