package com.jayfan.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("OrderLineItemRepository")
public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long> {

	@Transactional
	@Modifying
	@Query("delete from OrderLineItem c where c.id = ?1")
	void deleteBy(Long id);
}
