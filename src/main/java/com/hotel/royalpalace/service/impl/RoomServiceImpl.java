package com.hotel.royalpalace.service.impl;

import com.hotel.royalpalace.model.Room;
import com.hotel.royalpalace.repository.RoomRepository;
import com.hotel.royalpalace.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomsService {

    @Autowired
    RoomRepository roomRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Room> getAllRooms() {
        return roomRepository.getAllRooms();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Room> getAllRoomsByFloor(int floor) {
        return (List<Room>) roomRepository.findAll();
    }
}
