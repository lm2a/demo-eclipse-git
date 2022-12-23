package com.lm2a.tacos.data;


import org.springframework.data.repository.PagingAndSortingRepository;

import com.lm2a.tacos.model.Taco;

public interface TacoRepository extends PagingAndSortingRepository<Taco, Long> {

}
