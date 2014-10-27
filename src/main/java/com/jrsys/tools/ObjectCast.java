package com.jrsys.tools;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class ObjectCast {

	public ObjectCast(){}
	
    public static File multipartToFile(MultipartFile multipart) throws IllegalStateException, IOException {
        File tmpFile = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + 
                                multipart.getOriginalFilename());
        multipart.transferTo(tmpFile);
        return tmpFile;
    }
}
