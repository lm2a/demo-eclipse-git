package com.lm2a.tacos.data;

import org.springframework.data.repository.CrudRepository;

import com.lm2a.tacos.model.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}
