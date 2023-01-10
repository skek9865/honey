package project.honey.comm;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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

    public String uploadFileUuid(MultipartFile multipartFile, String path){
        String originalFilename = multipartFile.getOriginalFilename();
        String fileName = makeFileNameUuid(originalFilename);
        String filePath = path+"/"+fileName;

        File file = new File(filePath);
        try{
            multipartFile.transferTo(file);
        } catch(IOException e){
            e.printStackTrace();
        }
        return fileName;
    }

    public void deleteFile(String path, String fileName, Integer id){
        String rFileName = makeFileName(fileName, id);
        String filePath = path + "/" + rFileName;

        File file = new File(filePath);

        file.delete();
    }

    public void deleteFileUuid(String path, String fileName){
        String filePath = path + "/" + fileName;
        File file = new File(filePath);
        file.delete();
    }

    private String makeFileName(String originalFilename, Integer id){
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        return String.valueOf(id) + "." + ext;
    }

    private String makeFileNameUuid(String originalFilename){
        int pos = originalFilename.lastIndexOf(".");
        String ext = originalFilename.substring(pos + 1);
        return UUID.randomUUID().toString() + "." + ext;
    }
}
