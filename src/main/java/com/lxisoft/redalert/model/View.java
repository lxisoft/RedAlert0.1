package com.lxisoft.redalert.model;

import org.springframework.web.multipart.MultipartFile;

import com.lxisoft.redalert.service.dto.FeedDTO;
import com.lxisoft.redalert.service.dto.FileDTO;

public class View {
	
	 private FileDTO file= new FileDTO();
	 private FeedDTO feed = new FeedDTO();
	 private MultipartFile image;
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	public FileDTO getFile() {
		return file;
	}
	public void setFile(FileDTO file) {
		this.file = file;
	}
	public FeedDTO getFeed() {
		return feed;
	}
	public void setFeed(FeedDTO feed) {
		this.feed = feed;
	}
	

}
