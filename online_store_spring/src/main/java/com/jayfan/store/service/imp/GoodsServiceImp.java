package com.jayfan.store.service.imp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jayfan.store.domain.Goods;
import com.jayfan.store.domain.GoodsRepository;
import com.jayfan.store.service.GoodsService;

@Service
public class GoodsServiceImp implements GoodsService {

	
	@Autowired
	private GoodsRepository goodsRepository;
	
	@Override
	public List<Goods> queryAll() {
		// TODO Auto-generated method stub
		return goodsRepository.findAll();
	}

	@Override
	public List<Goods>  queryByStartOffset(int page, int pageSize) {
		// TODO Auto-generated method stub
		int start =0;
		int offset =pageSize;
		start = (page-1) * pageSize;
		return goodsRepository.findStartOffset(start, offset);
	}

	@Override
	public Goods queryDetail(long goodsid) {
		// TODO Auto-generated method stub
		return goodsRepository.findById(goodsid).orElse(new Goods());
	}

}
