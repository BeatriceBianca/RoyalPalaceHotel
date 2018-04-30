package com.hotel.royalpalace.repository;

import com.hotel.royalpalace.model.RoomType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoomTypeRepository extends CrudRepository<RoomType, Long> {

    RoomType findById(Long id);
}
