package com.downpu.repository;

import com.downpu.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yy187 on 2017/9/11.
 */
public interface AdminRepository extends JpaRepository<Admin,Integer> {
   List<Admin> findByName(String username);
   List<Admin> findByNameAndPassword(String name,String password);
}
