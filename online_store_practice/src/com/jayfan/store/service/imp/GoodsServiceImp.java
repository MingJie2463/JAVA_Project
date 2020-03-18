package com.jayfan.store.service.imp;

import java.util.List;

import com.jayfan.store.dao.GoodsDao;
import com.jayfan.store.dao.imp.GoodsDaoImpJdbc;
import com.jayfan.store.domain.Goods;
import com.jayfan.store.service.GoodsService;

public class GoodsServiceImp implements GoodsService {

	GoodsDao goodsDao = new GoodsDaoImpJdbc();

	@Override
	public List<Goods> queryAll() {
		// TODO Auto-generated method stub
		return goodsDao.findAll();
	}

	@Override
	public List<Goods> queryByStartEnd(int start, int end) {
		// TODO Auto-generated method stub
		return goodsDao.findStartEnd(start, end);
	}

	@Override
	public Goods queryDetail(long goodsid) {
		// TODO Auto-generated method stub
		return goodsDao.findByPk(goodsid);
	}

}
