package com.downpu.repository;

import com.downpu.domain.DownItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by yy187 on 2017/8/21.
 */
@Transactional
public interface DownItemRepository extends JpaRepository<DownItem,Integer> {
     DownItem findByName(String name);
    List<DownItem> findByNameLike(String name);
    Object findByIdloaditem(Integer id);
    void deleteDownItemByIdloaditem(Integer id);
}
