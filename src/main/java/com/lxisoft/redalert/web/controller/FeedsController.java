package com.lxisoft.redalert.web.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codahale.metrics.annotation.Timed;
import com.lxisoft.redalert.domain.Action;
import com.lxisoft.redalert.domain.Feed;
import com.lxisoft.redalert.repository.ActionRepository;
import com.lxisoft.redalert.repository.FeedRepository;
import com.lxisoft.redalert.service.dto.FeedDTO;
import com.lxisoft.redalert.service.mapper.FeedMapper;
import com.lxisoft.redalert.web.rest.errors.BadRequestAlertException;
import com.lxisoft.redalert.web.rest.util.HeaderUtil;


@Controller
@RequestMapping("/feeds")
public class FeedController {
	
	private FeedRepository feedRepository;
	private FeedMapper feedMapper;
	private ActionRepository actionRepository;

	public FeedController(FeedRepository feedRepository, ActionRepository actionRepository) {
        this.feedRepository = feedRepository;
        this.actionRepository=actionRepository;
    }
	@GetMapping("/sahal")
    @Timed
    public String createPage( Model model)  {
		
		feedRepository.delete((long) 3);
	return "upload";
	}
	
	
	
	@GetMapping("/get")
    @Timed
    public String createFeedPage( Model model)  {
		
		
	
	/*Feed feed=new Feed();
	feed.setComments("super lAST feed try mine 1");
	Feed feedSaved=feedRepository.save(feed);
	Action action=new Action();
	action.setUserName("first action");
	action.setFeed(feedSaved);
	actionRepository.save(action);
	action=new Action();
	action.setUserName("second action");
	action.setFeed(feedSaved);
	actionRepository.save(action);  
	action=new Action();
	action.setUserName("third action");
	action.setFeed(feedSaved);
	actionRepository.save(action);*/
	
	
	Feed feed=feedRepository.findAll().get(0);
	model.addAttribute("feed",feed);
	Action action=new Action();
	action.setFeed(feed);
	model.addAttribute("newAction",action );
	return "upload";
	
        
	}
	
	@PostMapping("/comments")
    @Timed
    public String addComments(@ModelAttribute Action newAction,Model model) throws URISyntaxException {
		System.out.print("***************FEEd**************"+newAction.getFeed());
		System.out.println("*************ACtion**********"+newAction);
	
		actionRepository.save(newAction);
	
	return	"redirect:/feeds/get";
		
        
    }

}
