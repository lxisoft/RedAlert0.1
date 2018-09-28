/*
* Copyright 2002-2016 the original author or authors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.lxisoft.redalert.web.rest;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.codahale.metrics.annotation.Timed;
import com.lxisoft.redalert.service.FeedService;
import com.lxisoft.redalert.service.dto.FeedDTO;



/*** TODO Provide a detailed description here 
* @author Prasad
* prasad, prasad.v.g@lxisoft.com
*/
@Controller
@RequestMapping("/feeds")
public class FeedController {
 
	private final Logger log = LoggerFactory.getLogger(FeedController.class);
	 private static final String ENTITY_NAME = "feed";
	    private final FeedService feedService;
	    public FeedController(FeedService feedService)
	    {
	    	this.feedService = feedService;
	    }
	    /**
	     * POST  /postfeeds : Create a new feed.
	     *
	     * @param feedDTO the feedDTO to create
	     * @return the ResponseEntity with status 201 (Created) and with body the new feedDTO, or with status 400 (Bad Request) if the feed has already an ID
	     * @throws URISyntaxException if the Location URI syntax is incorrect
	     */
	    @PostMapping("/postfeeds")
	    @Timed
	    public String createFeed(@ModelAttribute FeedDTO feedDTO) throws URISyntaxException {
	        log.debug("request to save Feed : {}", feedDTO);
	       
	        FeedDTO result = feedService.save(feedDTO);
	        return "newsfeed";
	    }
		@GetMapping("/get")
		public String getFeed(Model model)
		{
			model.addAttribute("feed",new FeedDTO());
			return "view";
			
		}
}
