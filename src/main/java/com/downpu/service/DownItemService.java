package com.downpu.service;

import com.downpu.domain.DownItem;
import org.springframework.data.domain.Page;

public interface DownItemService {
    Page<DownItem> findDownItemNoCriteria(Integer page,Integer size);
    Page<DownItem> findDownItemCriteria(Integer page,Integer size,DownItem downItem);
}
