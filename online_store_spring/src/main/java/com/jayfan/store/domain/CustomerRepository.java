package com.jayfan.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("CustomerRepository")
public interface CustomerRepository extends JpaRepository<Customer, String> {
	
}
