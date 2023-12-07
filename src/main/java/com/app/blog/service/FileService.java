package com.app.blog.service;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	String uploadFile(String path, MultipartFile multipartFile); //Here path is path upto folder i.e. upto images/ that we've declared in our application.properties
	//MultipartFile contains all information about the file.

	InputStream getResource(String path, String fileName); //InputStream is basically a pipe inside which data is flowing...so we can get data from InputStream
}
