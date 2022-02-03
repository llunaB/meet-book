package com.ssafy.api.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ImageController {
	
	//private ConferenceService conferenceService;
	private String attachPath;
	private String nullImage;
	private String rootPath;
	
	
	@Autowired
	public ImageController() {
		//this.conferenceService = conferenceService;
		rootPath = "C:/SSAFY/resources/upload/";
		attachPath = "image/";
		nullImage = "null.jpg";
	}
	
	@PostMapping("")
	public ResponseEntity<Map<String, String>> createImage(@RequestParam MultipartFile file){
		HashMap<String, String> map = new HashMap<String, String>();
		
		String filename = file.getOriginalFilename();
		File f = new File(rootPath + attachPath + filename);

		
		try {
			file.transferTo(f);
			map.put("message", "이미지 업로드 성공");
			map.put("url", "/thumbail/"+filename);
		}catch(Exception e) {
			e.printStackTrace();
			map.put("message", "이미지 업로드 실패");
		}
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
	}
	
	@ResponseBody
	@GetMapping("/{filename}")
	public ResponseEntity<Resource> getImage(@PathVariable("filename") String filename){
		UrlResource resource = null;
		try {
			String path = rootPath + attachPath + filename;
			File f = new File(path);
			if(!f.exists()) {
				resource = new UrlResource("file:" + rootPath + nullImage);
				return new ResponseEntity<Resource>(resource, HttpStatus.OK);
			}
			
			resource = new UrlResource("file:" + path);
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource, HttpStatus.OK);
		//return resource;
	}
	
	@PutMapping("/{filename}")
	public ResponseEntity<Map<String,String>> updateImage(@PathVariable("filename") String filename, @RequestParam MultipartFile file){
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			
			map.put("message", "이미지 수정 성공");
		}catch(Exception e) {
			e.printStackTrace();
			map.put("message", "이미지 수정 실패");
		}
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
	@DeleteMapping("/{filename}")
	public ResponseEntity<Map<String,String>> deleteImage(@PathVariable("filename") String filename){
		HashMap<String, String> map = new HashMap<String, String>();
		
		File f = new File(rootPath + attachPath + filename);
		
		
		if(!f.exists()) {
			map.put("message", "이미지가 존재하지 않습니다.");
			return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
		}
		
		try {
			if(f.delete()) {
				map.put("message", "이미지 삭제 성공");
			}else {
				map.put("message", "이미지 삭제 실패");
			}
			
		}catch(Exception e){
			map.put("message", "이미지 삭제 실패");
		}
		
		return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
	}
	
}
