package org.nelldora.apiserver.util;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@Log4j2
@RequiredArgsConstructor
public class CustomFileUtil {

    @Value("${org.zerock.upload.path}")
    private String uploadPath;

    //생성자 대신에 많이 사용
    //폴더를 만들어두는 용도
    @PostConstruct
    public void init(){
        File tempFolder = new File(uploadPath);
        if(!tempFolder.exists()){
            tempFolder.mkdir();
        }
        uploadPath = tempFolder.getAbsolutePath();
        log.info("----------------------------------");

    }

    public List<String> saveFiles(List<MultipartFile> files) throws RuntimeException{

        log.info("파일 길이"+files.size());
        if(files==null || files.size()==0){
            return null;
        }
        List<String> uploadNames = new ArrayList<>();

        for(MultipartFile file : files){
            String saveName = UUID.randomUUID().toString()+"-"+file.getOriginalFilename();

            Path savePath = Paths.get(uploadPath, saveName); //파일 만들기
            log.info("savePath의 경로 명 : "+ savePath);
            try {
                Files.copy(file.getInputStream(),savePath);  //원본 저장 (사실복사)

                //섬네일 만들기
                String contentType= file.getContentType(); //Mime Type
                if(contentType !=null || contentType.startsWith("image")){

                    Path thumbnailPath = Paths.get(uploadPath,"s-"+saveName);

                    //savePath명의 사이즈를 200/200으로 섬네일Path파일 명으로 저장
                    Thumbnails.of(savePath.toFile()).size(200,200).toFile(thumbnailPath.toFile());



                }

                uploadNames.add(saveName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return uploadNames;

    }

    public ResponseEntity<Resource> getFile(String fileName){
         Resource resource = new FileSystemResource(uploadPath+File.separator+fileName);
        if(!resource.isReadable()){
            log.info("util : resource 읽기 불가");
            resource = new FileSystemResource(uploadPath+File.separator+"default.jpg");

        }
        HttpHeaders headers =new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    public void deleteFile(List<String> fileNames){
        if(fileNames ==null || fileNames.size() ==0){
            return ;
        }

        fileNames.forEach(fileName ->{
            //썸네일 삭제

            String thumbnailFileName = "s-"+fileName;
            Path thumbnailPath = Paths.get(uploadPath, thumbnailFileName);
            Path filePath = Paths.get(uploadPath, fileName);

            try {
                Files.deleteIfExists(filePath);
                Files.deleteIfExists(thumbnailPath);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }


        });
    }
}
