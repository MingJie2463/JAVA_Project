package com.jayfan.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("OrdersRepository")
public interface OrdersRepository extends JpaRepository<Orders, String> {

}
