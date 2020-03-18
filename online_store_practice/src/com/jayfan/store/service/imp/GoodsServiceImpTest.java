package com.jayfan.store.service.imp;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayfan.store.domain.Goods;
import com.jayfan.store.service.GoodsService;

public class GoodsServiceImpTest {

	GoodsService goodService;

	@Before
	public void setUp() throws Exception {
		goodService = new GoodsServiceImp();
	}

	@After
	public void tearDown() throws Exception {
		goodService = null;
	}

	@Test
	public void testQueryAll() {
		List<Goods> list = goodService.queryAll();
		assertEquals(34, list.size());
		Goods goods = list.get(0);
		assertEquals(1L, goods.getId());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機", goods.getName());
	}

	@Test
	public void testQueryByStartEnd() {
		List<Goods> list = goodService.queryByStartEnd(0, 10);
		assertEquals(10, list.size());
		Goods goods = list.get(0);
		assertEquals(1L, goods.getId());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機", goods.getName());
	}

	@Test
	public void testQueryDetail() {
		Goods goods =  goodService.queryDetail(3);
		assertNotNull(goods);
		assertEquals("聯想天逸510S", goods.getName());

	}

}
