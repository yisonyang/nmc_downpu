package com.downpu.repository;

import com.downpu.domain.DownItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

/**
 * Created by yy187 on 2017/8/21.
 */
@Transactional
public interface DownItemRepository extends JpaRepository<DownItem,Integer>,JpaSpecificationExecutor<DownItem>{
    List<DownItem> findByNameLike(String name);
    Object findByIdloaditem(Integer id);
    boolean existsByName(String name);
    void deleteDownItemByIdloaditem(Integer id);
}
