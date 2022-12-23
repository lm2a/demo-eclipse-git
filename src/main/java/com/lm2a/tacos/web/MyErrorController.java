package com.lm2a.tacos.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MyErrorController implements ErrorController {
	
	@Autowired
	ErrorAttributes errorAttributes;

	@RequestMapping("/error")
	public String handleError(WebRequest webRequest, Model model) {
		
		Map<String, Object> errorAtts = errorAttributes.getErrorAttributes(webRequest, ErrorAttributeOptions.of(ErrorAttributeOptions.Include.STACK_TRACE));
		log.error("Atencion!!!! Ha ocurrido un error");
		//
		return "error";
	}
	
}
