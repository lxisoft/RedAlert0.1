package com.lxisoft.redalert.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.codahale.metrics.annotation.Timed;
import com.lxisoft.redalert.domain.File;
import com.lxisoft.redalert.domain.User;
import com.lxisoft.redalert.domain.enumeration.Alert;
import com.lxisoft.redalert.service.FeedService;
import com.lxisoft.redalert.service.FileService;
import com.lxisoft.redalert.service.dto.FeedDTO;
import com.lxisoft.redalert.service.dto.FileDTO;
import com.lxisoft.redalert.service.dto.UserRegistrationDTO;

@Controller
@RequestMapping("/alertcontroller")
public class AlertController {

	private final FeedService feedService;
	private final FileService fileservice;
    public AlertController(FeedService feedService,FileService fileservice)
    {
    	this.feedService = feedService;
    	this.fileservice = fileservice;
    }
	 @PostMapping("/postinformation")
	    @Timed
	    public String createFeed(@ModelAttribute FeedDTO feedDTO,Model model) throws URISyntaxException {
	       
	       
			FeedDTO result = feedService.save(feedDTO);
			model.addAttribute("file",new FileDTO());
	        return "fileupload";
	    }
	 @PostMapping("/postfile")
	 @Timed
	 public String uploadFile(@RequestParam MultipartFile file ,RedirectAttributes redirectAttr,Model model){
		 
		 FileDTO filedto=new FileDTO();
		 try {
			filedto.setAttachments(file.getBytes());
			FileDTO saved=fileservice.save(filedto);
			System.out.println(saved);
			model.addAttribute("fileDTO",saved);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return "success";
		 
	 }
	@GetMapping("/firstpage") 
public String firstView(Model model)
{
	model.addAttribute("user",new UserRegistrationDTO());
return "home";
}
	 @GetMapping("/alert")
	 public String alert(@RequestParam String type)
	 {
		 for(Alert al:Alert.values())
		 {
			 if(type==Alert.ORANGE_ALERT.toString())
			 {
				 return "orangealert";
			 }
			 if(type==Alert.RED_ALERT.toString())
			 {
				 return "redalert";
			 }
		 }
		 
	 	return "";
	 }
@GetMapping("/orangealert")
public String orangeAlert(Model model)
{
	model.addAttribute("user",new FeedDTO());

	model.addAttribute("file",new FileDTO());
	
	
    return "orangealert";	
}
@GetMapping("/redalert")
public String redAlert(Model model)
{
	model.addAttribute("user",new FeedDTO()); 
    return "redalert";	
}
}
