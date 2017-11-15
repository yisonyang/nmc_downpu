package com.downpu.repository;

import com.downpu.domain.Users;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yy187 on 2017/9/5.
 */
@Transactional
public interface UserRepository extends JpaRepository<Users,Integer> {
    List<Users> findByUsername(String username);
    void deleteUsersByUserid(Integer id);
   List <Users> findByUsernameAndPassword(String username,String password);
   Users findByUserid(Integer id);
}
