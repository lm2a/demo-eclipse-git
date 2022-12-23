package com.lm2a.tacos.web.api;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lm2a.tacos.data.TacoRepository;
import com.lm2a.tacos.model.Taco;

@RestController
@RequestMapping(path="/rest", produces="application/json")
@CrossOrigin(origins="*")
public class TacoControllerAPI {
	
	@Autowired
	private TacoRepository tacoRepo;
	
	
//	@GetMapping("/{id}")
//	public Taco tacoById(@PathVariable("id") Long id) {
//		Optional<Taco> optTaco = tacoRepo.findById(id);
//		if(optTaco.isPresent()) {
//			return optTaco.get();
//		}
//		return null;
//	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Taco> tacoByIdWithStatusCode(@PathVariable("id") Long id) {
		Optional<Taco> optTaco = tacoRepo.findById(id);
		if(optTaco.isPresent()) {
			return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(path="/taco", consumes="application/json")
	public ResponseEntity<Taco> postTaco(@RequestBody  @Valid Taco taco, Errors errors) {
		if(errors.hasErrors()) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		Taco savedTaco = tacoRepo.save(taco);
		return new ResponseEntity<>(savedTaco, HttpStatus.CREATED);
	}
	
	@GetMapping("/all")
	public Iterable<Taco> getAll(){
		return tacoRepo.findAll();
	}
	
	@GetMapping("/recent")
	public Iterable<Taco> getRecentTacos(){
		PageRequest paginable = PageRequest.of(0, 5, Sort.by("createdAt").descending());
		return tacoRepo.findAll(paginable);
	}

}
