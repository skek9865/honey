package project.honey.comm.file;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileConverter {

    public FileName uploadFile(MultipartFile multipartFile, String path) throws IOException {
        if (multipartFile.isEmpty()) throw new FileNotFoundException();
        String originalName = multipartFile.getOriginalFilename();
        String localFileName = UUID.randomUUID() + "." + extractExt(originalName);
        multipartFile.transferTo(new File(path + localFileName));
        return new FileName(originalName, localFileName);
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
