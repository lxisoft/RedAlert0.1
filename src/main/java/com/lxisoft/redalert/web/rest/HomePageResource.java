package com.lxisoft.byta.alert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lxisoft.byta.alert.domain.UserRegistration;
import com.lxisoft.byta.alert.repository.HomePageRepository;


@Controller
public class HomePageResource {
	@Autowired
	HomePageRepository repository;
	
	
	@RequestMapping(value="saveData",method= RequestMethod.GET)
	public String saveData(Model model)
	{
		model.addAttribute("userreg",new UserRegistration());
	   return "show";
	}
	@RequestMapping(value="redalert",method=RequestMethod.GET)
	public String doRedAlert(@ModelAttribute UserRegistration userreg2,Model model)
	{
	
	 model.addAttribute("userreg2", repository.save(userreg2));
		return "index";
	}
	

}
