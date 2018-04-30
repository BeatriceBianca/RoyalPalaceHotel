package com.hotel.royalpalace.service.impl;

import com.hotel.royalpalace.model.Room;
import com.hotel.royalpalace.model.RoomType;
import com.hotel.royalpalace.repository.RoomRepository;
import com.hotel.royalpalace.repository.RoomTypeRepository;
import com.hotel.royalpalace.service.RoomsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomsService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomTypeRepository roomTypeRepository;

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

    @Override
    @Transactional(readOnly = true)
    public List<RoomType> getAllRoomTypes() {
        return roomRepository.getAllRoomTypes();
    }

    @Override
    public void editRoomType(Room room) {

        Room newRoom = room;
        try {
            roomRepository.save(newRoom);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editPrice(RoomType roomType) {

        try {
            roomTypeRepository.save(roomType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public RoomType findById(Long id) {

        return roomTypeRepository.findById(id);
    }
}
