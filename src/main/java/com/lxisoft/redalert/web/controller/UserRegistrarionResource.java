package com.lxisoft.UserRegistration.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lxisoft.UserRegistration.model.UserRegistration;
import com.lxisoft.UserRegistration.repository.UserRegistrationRepository;

@Controller
public class UserRegistrarionResource {

	@Autowired
	UserRegistrationRepository userRegistrationRepository;

	@RequestMapping("/first")
	public String first() {

		return "start";
	}
	
	@GetMapping(value = "/signup")
	public String signUp(Model model)
	{
		
		model.addAttribute("userRegistration", new UserRegistration());
		return "signup";
		
	}
	
	@GetMapping(value = "/login")
	public String login(Model model)
	{
		return "login";
		
	}

	@PostMapping(value = "/save")
	public String save(@ModelAttribute UserRegistration userRegistration,String email, Model model) {
		if(userRegistrationRepository.findByEmail(email)==null)
		{
			userRegistrationRepository.save(userRegistration);
			return "next";
		}
		else
		{
			return "signup";
		}
		

	}
	@PostMapping(value = "/findprofile")
	public String findProfile(String email,String password, Model model)
	{
		UserRegistration userRegistration2 = null;
		UserRegistration userRegistration;
		UserRegistration userRegistration1;
		
		boolean check = false;
		do
		{
		userRegistration = userRegistrationRepository.findByEmail(email);
		userRegistration1 =userRegistrationRepository.findByPassword(password);
		if(userRegistration.equals(userRegistration1))
		{
			userRegistration2=userRegistration;
			check=true;
		}
		}while(check=false);
		if(userRegistration.equals(userRegistration1))
		{
			
		}
		else
		{
			return "login";
		}
		
		model.addAttribute("userRegistration",userRegistration2);
		System.out.println(userRegistration2);
		return "userprofile";
		
	}

}
