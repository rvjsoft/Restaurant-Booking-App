package com.rvj.app.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvj.app.foodorder.entity.TableBooking;

@Repository
public interface TableBookingRepository extends JpaRepository<TableBooking, Long>{

}
