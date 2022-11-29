package project.honey.comm;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class UploadService {

    public static String uploadFile(MultipartFile multipartFile, String path){
        String uuid = UUID.randomUUID().toString();
        String originalFilename = multipartFile.getOriginalFilename();
        String filePath = path+"/"+uuid+"#"+originalFilename;
        File file = new File(filePath);
        try{
            multipartFile.transferTo(file);
        } catch(IOException e){
            e.printStackTrace();
        }
        return filePath;
    }

}
