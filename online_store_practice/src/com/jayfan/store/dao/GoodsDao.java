package com.jayfan.store.dao;

import java.util.List;

import com.jayfan.store.domain.Goods;

public interface GoodsDao {
	Goods findByPk(long pk);
	
	List<Goods> findAll();
	

    List<Goods> findStartEnd(int start, int end);

    
	void create(Goods goods);
	
	void modify(Goods goods);
	
	void remove(long pk);
}
