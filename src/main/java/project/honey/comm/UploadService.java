package project.honey.comm;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {

    public static void uploadFile(MultipartFile multipartFile, String path){
        String originalFilename = multipartFile.getOriginalFilename();
        String filePath = path+"/"+originalFilename;
        File file = new File(filePath);
        try{
            multipartFile.transferTo(file);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
