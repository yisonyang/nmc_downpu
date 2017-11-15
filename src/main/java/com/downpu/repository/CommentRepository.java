package com.downpu.repository;

import com.downpu.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by yy187 on 2017/8/31.
 */
public interface CommentRepository extends JpaRepository<Comment,Integer> {
   List<Comment> findById(Integer id);
   List<Comment> findByRid(Integer rid);
}
