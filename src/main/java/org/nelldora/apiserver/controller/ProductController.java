package org.nelldora.apiserver.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nelldora.apiserver.dto.ProductDTO;
import org.nelldora.apiserver.util.CustomFileUtil;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;

    @PostMapping("/")
    public Map<String,String> register(ProductDTO productDTO){
        log.info("register" + productDTO);
        List<MultipartFile> files = productDTO.getFiles();
        log.info("Controller에서 받은 file 개수 " + productDTO.getFiles().size());
        List<String> uploadFileNames = fileUtil.saveFiles(files);

        productDTO.setUploadedFileNames(uploadFileNames);

        log.info(uploadFileNames);

        return Map.of("RESULT","SUCCESS");

    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGet(@PathVariable("fileName") String fileName){

        log.info("Controller : fileName : "+ fileName);
        return fileUtil.getFile(fileName);
    }

}
