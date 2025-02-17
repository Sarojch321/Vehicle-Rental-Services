package com.vehiclerentalservices.app.services.impl;

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

import com.vehiclerentalservices.app.services.FileService;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		// File name
		
		String name = file.getOriginalFilename();
		
		//random name generate
		String randomId = UUID.randomUUID().toString();
		String filename= randomId.concat(name.substring(name.lastIndexOf(".")));
		
		//Full Path
		String pathName = path+File.separator+filename;
		
		//create folder if not
		File f =new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		
		//file copy
		
		Files.copy(file.getInputStream(), Paths.get(pathName));
		return filename;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path +File.separator+ fileName;
		
		InputStream is = new FileInputStream(fullPath);
		return is;
	}

}
