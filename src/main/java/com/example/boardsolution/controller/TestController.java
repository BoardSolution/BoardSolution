package com.example.boardsolution.controller;

import com.example.boardsolution.service.MybaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {

	@GetMapping("/index")
	public String home() {
		return "index";
	}


	@Autowired
	MybaService service;

	@RequestMapping("/myba")
	public ModelAndView myba() {
		ModelAndView mv = new ModelAndView();
		int totSize = service.totSize("");

		mv.addObject("totSize", totSize);
		mv.setViewName("myba_result");
		return mv;
	}
}
