package com.lm2a.tacos.data;

import org.springframework.data.repository.CrudRepository;

import com.lm2a.tacos.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {

}
