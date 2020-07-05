package com.jayfan.store.service;

import java.util.List;

import com.jayfan.store.domain.Goods;

public interface GoodsService {
	List<Goods> queryAll();

	List<Goods> queryByStartOffset(int start,int offset);

	Goods queryDetail(long goodsid);
}
