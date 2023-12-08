package com.app.blog.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadFile(String path, MultipartFile multipartFile) {  //Here path is path upto folder i.e. upto images/
		
		//File Name
				String fileName = multipartFile.getOriginalFilename(); 
				//suppose file name is abc.png 
						
				//generate a random name for the file we're uploading
				String randomId = UUID.randomUUID().toString();
				String fileName1 = randomId.concat(fileName.substring(fileName.lastIndexOf("."))); //we combined randomId with .png!!
						
				//Full location of file
				String fullPath = path + File.separator + fileName1;   //File.separator means in general "/" or "\"..so either one will get used as needed!!
						
				//Create a folder if not created where we will upload our file
				File file = new File(path);
				if(!file.exists())
					
					{
						file.mkdir();
					}
						
				//Copy/upload file to fullPath location
				try {
						Files.copy(multipartFile.getInputStream(), Paths.get(fullPath));
					} catch (IOException e) 
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) {
		String fullPath =  path + File.separator + fileName;
		InputStream inputStream = null; 
		try {
			inputStream = new FileInputStream(fullPath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return inputStream;
	}

}
