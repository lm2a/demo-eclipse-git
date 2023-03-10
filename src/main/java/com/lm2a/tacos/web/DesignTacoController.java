package com.lm2a.tacos.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.lm2a.tacos.data.IngredientRepository;
import com.lm2a.tacos.data.TacoRepository;
import com.lm2a.tacos.model.Ingredient;
import com.lm2a.tacos.model.Taco;
import com.lm2a.tacos.model.Ingredient.Type;
import com.lm2a.tacos.model.Order;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

	@Autowired
	IngredientRepository ingredientRepo;
	
	@Autowired
	TacoRepository tacoRepo;

	
	@ModelAttribute("order")
	public Order order() {
		return new Order();
	}
	
	@ModelAttribute("tktn")
	public Taco taco() {
		return new Taco();
	}
	
	
	@GetMapping
	public String showDesignForm(Model model) {
		int x = 0;
		double y = 1/x;
		System.out.println(""+y);
		fillIngredients(model);
		return "design";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}

	@PostMapping
	public String processDesign(@Valid @ModelAttribute(name = "tktn") Taco design, Errors errors, Model model, @ModelAttribute Order order) {
		if (errors.hasErrors()) {
			fillIngredients(model);
			return "design";
		}		
		
		Taco taco = tacoRepo.save(design);
		order.addDesign(taco);

		log.info("Procesando el taco: " + design);
		return "redirect:/orders/current";
	}

	public void fillIngredients(Model model) {
//		List<Ingredient> ingredients = Arrays.asList(
//				  new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//			      new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//			      new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//			      new Ingredient("CARN", "Carnitas",       Type.PROTEIN),
//			      new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//			      new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//			      new Ingredient("CHED", "Cheddar", Type.CHEESE),
//			      new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//			      new Ingredient("SLSA", "Salsa", Type.SAUCE),
//			      new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//				);

		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));

		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}

		//model.addAttribute("tktn", new Taco());

	}

}
