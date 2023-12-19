package com.codi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewController {
	
	@GetMapping({"","/"})
    public void home() {
	}
	
	@GetMapping("/login")
    public void login() {
	}
	
	@GetMapping("/join")
    public void join() {
	}
	
	@GetMapping("/setting")
    public void setting() {
	}
}
