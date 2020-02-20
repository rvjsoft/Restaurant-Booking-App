package com.rvj.app.dataaccess;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rvj.app.foodorder.entity.Image;

@Repository
public interface ImageRespository extends JpaRepository<Image, BigInteger>{

}
