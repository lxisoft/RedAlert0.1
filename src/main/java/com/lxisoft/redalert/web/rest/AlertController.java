package com.lxisoft.redalert.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Base64;

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
import com.lxisoft.redalert.model.View;
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

	public AlertController(FeedService feedService, FileService fileservice) {
		this.feedService = feedService;
		this.fileservice = fileservice;
	}

	@PostMapping("/postinformation")
	@Timed
	public String createFeed(@ModelAttribute View view, @RequestParam MultipartFile img,
			RedirectAttributes redirectAttr, Model model) throws URISyntaxException {
		System.out.println("second View Dto" + view);
		System.out.println("second feed dto" + view.getFeed() + "id" + view.getFile());
		FeedDTO feedDto = feedService.findOne(view.getFeed().getId());
		feedDto.setComments(view.getFeed().getComments());
		feedDto.setType(view.getFeed().getType());
		System.out.println("feeddto nnnn" + feedDto);

		feedService.save(feedDto);
		FileDTO fileDTO = view.getFile();
		fileDTO.setFeedId(view.getFeed().getId());

		try {
			
			String imagedata="data:image/jpg;base64,"+Base64.getEncoder().encodeToString(img.getBytes());
			model.addAttribute("image", imagedata);
			fileDTO.setAttachments(img.getBytes());
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("second file dto" + view.getFile().getAttachments());
		fileservice.save(view.getFile());
		System.out.println("successsful");
		return "success";
	}

	@GetMapping("/firstpage")
	public String firstView(Model model) {
		model.addAttribute("feed", new FeedDTO());
		return "home";
	}

	@GetMapping("/alert")
	public String alert(@ModelAttribute FeedDTO feed, Model model) {
		System.out.println(feed.getType());
		if ((feed.getType().equals(Alert.ORANGE_ALERT))) {
			feed.setType(Alert.ORANGE_ALERT);
			feed = feedService.save(feed);
			System.out.println("first feed " + feed);
			View view = new View();

			FileDTO fileDTO = new FileDTO();
			fileDTO.setFeedId(feed.getId());
			view.setFeed(feed);
			view.setFile(fileDTO);
			System.out.println("first file " + view.getFeed() + "*****" + view.getFile().getFeedId());
			model.addAttribute("view", view);

			return "orangealert";

		} else if ((feed.getType().equals(Alert.RED_ALERT))) {
			feed.setType(Alert.RED_ALERT);
			feed = feedService.save(feed);
			System.out.println("first feed " + feed);
			View view = new View();

			FileDTO fileDTO = new FileDTO();
			fileDTO.setFeedId(feed.getId());
			view.setFeed(feed);
			view.setFile(fileDTO);
			System.out.println("first file " + view.getFeed() + "*****" + view.getFile().getFeedId());
			model.addAttribute("view", view);
			return "redalert";
		} else {
			feed.setType(Alert.GREEN_ALERT);
			feed = feedService.save(feed);
			System.out.println("first feed " + feed);
			View view = new View();

			FileDTO fileDTO = new FileDTO();
			fileDTO.setFeedId(feed.getId());
			view.setFeed(feed);
			view.setFile(fileDTO);
			System.out.println("first file " + view.getFeed() + "*****" + view.getFile().getFeedId());
			model.addAttribute("view", view);
			return "greenalert";
		}
	}

	// }

	@GetMapping("/orangealert")
	public String orangeAlert(Model model) {
		model.addAttribute("user", new FeedDTO());

		model.addAttribute("file", new FileDTO());

		return "orangealert";
	}

	@GetMapping("/redalert")
	public String redAlert(Model model) {
		model.addAttribute("user", new FeedDTO());
		return "redalert";
	}
}
