package com.jayfan.store.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository("GoodsRepository")
public interface GoodsRepository extends JpaRepository<Goods, Long> {

	// Limit ?2 Offset ?1
	@Query(value = "select id,name,price,description,brand,cpu_Brand,cpu_Type,memory_Capacity,hd_Capacity,card_Model,displaysize,image from Goods Limit ?2 Offset ?1", nativeQuery = true)
	List<Goods> findStartOffset(int start, int offset);
}
