package com.downpu.imple;

import com.downpu.domain.DownItem;
import com.downpu.repository.DownItemRepository;
import com.downpu.service.DownItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service(value = "/DownItemService")
public class DownItemServiceImpl implements DownItemService{
    @Resource
    DownItemRepository downItemRepository;
    @Override
    public Page<DownItem> findDownItemNoCriteria(Integer page,Integer size){
        PageRequest request=this.buildPageRequest(page,size);
        Page<DownItem> downItems=this.downItemRepository.findAll(request);
        return downItems;
    }
    private PageRequest buildPageRequest(int pageNumber,int pageSize){
        return new PageRequest(pageNumber-1,pageSize,null);
    }
    @Override
    public  Page<DownItem> findDownItemCriteria(Integer page,Integer size,final DownItem downItem){
        Pageable pageable=new PageRequest(page,size,Sort.Direction.ASC,"idloaditem");
        Page<DownItem> page1=downItemRepository.findAll(new Specification<DownItem>() {
            @Override
            public Predicate toPredicate(Root<DownItem> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate p1=cb.equal(root.get("kind").as(String.class),downItem.getKind());
                query.where(cb.and(p1));
                return query.getRestriction();
            }
        },pageable);
        return page1;
    }
}
