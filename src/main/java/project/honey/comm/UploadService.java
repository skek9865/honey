package project.honey.comm;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class UploadService {

    public String uploadFile(MultipartFile multipartFile, String path, Integer id){
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = makeFileName(originalFilename, id);
        String filePath = path+"/"+fileName;

        File file = new File(filePath);
        try{
            multipartFile.transferTo(file);
        } catch(IOException e){
            e.printStackTrace();
        }
        return originalFilename;
    }

    public void deleteFile(String path, String fileName, Integer id){
        String rFileName = makeFileName(fileName, id);
        String filePath = path + "/" + rFileName;

        File file = new File(filePath);

        file.delete();
    }

    private String makeFileName(String originalFilename, Integer id){
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        return String.valueOf(id) + "." + ext;
    }

}
