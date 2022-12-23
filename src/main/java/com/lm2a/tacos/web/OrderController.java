package com.lm2a.tacos.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.lm2a.tacos.data.OrderRepository;
import com.lm2a.tacos.model.Order;

import lombok.extern.slf4j.Slf4j;

//TODO importante ver que funcione
@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	//!!!
	@Autowired
	OrderRepository orderRepository;
	
	@GetMapping("/current")
	public String orderForm() {
		//model.addAttribute("order", new Order());
		return "orderForm";
	}
	
	@PostMapping
	public String processOrder(@Valid @ModelAttribute(name="order") Order order, Errors errors, SessionStatus session) {
		if(errors.hasErrors()) {
			return "orderForm";
		}
		
		Order persistedOrder = orderRepository.save(order);
		log.info("Procesando la orden: "+persistedOrder);
		session.setComplete();
		
		return "redirect:/";
	}

}
