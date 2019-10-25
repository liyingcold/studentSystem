package com.ly.dao;

import com.ly.pojo.MaleHealth;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MaleHealthMapper {

    MaleHealth selectByPrimaryKey(@Param("id") Long id);

}