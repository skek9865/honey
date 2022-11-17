package project.honey.comm;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {

    private static final String fileDir = "C:\\JAVA\\honey\\src\\main\\resources\\static\\images\\corp\\";

    public static void uploadFile(MultipartFile multipartFile){
        String originalFilename = multipartFile.getOriginalFilename();
        String filePath = fileDir+originalFilename;
        File file = new File(filePath);
        try{
            multipartFile.transferTo(file);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
