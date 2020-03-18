package com.jayfan.store.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jayfan.store.dao.imp.GoodsDaoImpJdbc;
import com.jayfan.store.domain.Goods;

public class GoodsDaoTest {

	GoodsDao dao;

	@Before
	public void setUp() throws Exception {
		dao = new GoodsDaoImpJdbc();
	}

	@After
	public void tearDown() throws Exception {
		dao = null;
	}

	@Test
	public void testFindByPk() {
		Goods goods = dao.findByPk(1L);
		assertNotNull(goods);
		assertEquals(1L, goods.getId());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機", goods.getName());
		assertEquals(3399, goods.getPrice(), 0.1);
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機(八代i3-8100 8G 1T 四年上門 有線鍵鼠 FHD寬屏)21.5英吋 ", goods.getDescription());
		assertEquals("5ae00211N25afad2c.jpg", goods.getImage());
		assertEquals("", goods.getBrand());
		assertEquals("", goods.getCpuBrand());
		assertEquals("", goods.getCardModel());
		assertEquals("", goods.getMemoryCapacity());
		assertEquals("", goods.getCpuType());
		assertEquals("", goods.getHdCapacity());
		assertEquals("", goods.getDisplaysize());

	}

	@Test
	public void testFindAll() {
		List<Goods> list = dao.findAll();
		assertEquals(list.size(), 34);
		Goods goods = list.get(0);
		assertEquals(1L, goods.getId());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機", goods.getName());

	}

	@Test
	public void testFindStartEnd() {
		List<Goods> list = dao.findStartEnd(0, 10);
		assertEquals(10, list.size());
		Goods goods = list.get(0);
		assertEquals(1L, goods.getId());
		assertEquals("戴爾(DELL)成就3470高性能商用辦公檯式電腦整機", goods.getName());
	}

	@Test
	public void testCreate() {
		Goods goods = new Goods();
		goods.setId(69);
		goods.setName("Ipad Mini4");
		goods.setPrice(14900);
		goods.setDescription("Apple Ipad Mini4");
		goods.setBrand("Apple");
		goods.setCpuBrand("Intel");
		goods.setCpuType("i7");
		goods.setMemoryCapacity("16G");
		goods.setHdCapacity("1T");
		goods.setCardModel("GTX970");
		goods.setDisplaysize("7.9");
		goods.setImage("aaa.jpg");

		dao.create(goods);
		Goods goods1 = dao.findByPk(69);
		assertNotNull(goods1);
		assertEquals(69, goods1.getId());
		assertEquals("Ipad Mini4", goods1.getName());
		assertEquals(14900, goods.getPrice(), 0.1);
		assertEquals("Apple Ipad Mini4", goods.getDescription());
		assertEquals("aaa.jpg", goods1.getImage());
		assertEquals("Apple", goods1.getBrand());
		assertEquals("Intel", goods1.getCpuBrand());
		assertEquals("i7", goods1.getCpuType());
		assertEquals("GTX970", goods1.getCardModel());
		assertEquals("16G", goods1.getMemoryCapacity());
		assertEquals("1T", goods1.getHdCapacity());
		assertEquals("7.9", goods1.getDisplaysize());
	}

	@Test
	public void testModify() {
		Goods goods = new Goods();
		goods.setId(69);
		goods.setName("Ipad Pro");
		goods.setPrice(19600);
		goods.setDescription("Apple Ipad Pro");
		goods.setBrand("Apple");
		goods.setCpuBrand("Intel Ultra");
		goods.setCpuType("Intel i11");
		goods.setMemoryCapacity("32G");
		goods.setHdCapacity("2T");
		goods.setCardModel("GTX990");
		goods.setDisplaysize("9.7");
		goods.setImage("ab.jpg");

		dao.modify(goods);

		Goods goods1 = dao.findByPk(69);
		assertNotNull(goods1);
		assertEquals(69, goods1.getId());
		assertEquals("Ipad Pro", goods1.getName());
		assertEquals(19600, goods.getPrice(), 0.1);
		assertEquals("Apple Ipad Pro", goods.getDescription());
		assertEquals("ab.jpg", goods1.getImage());
		assertEquals("Apple", goods1.getBrand());
		assertEquals("Intel Ultra", goods1.getCpuBrand());
		assertEquals("Intel i11", goods1.getCpuType());
		assertEquals("GTX990", goods1.getCardModel());
		assertEquals("32G", goods1.getMemoryCapacity());
		assertEquals("2T", goods1.getHdCapacity());
		assertEquals("9.7", goods1.getDisplaysize());
	}

	@Test
	public void testRemove() {
        dao.remove(69);
        Goods goods = dao.findByPk(69);
        assertNull(goods);
	}

}
