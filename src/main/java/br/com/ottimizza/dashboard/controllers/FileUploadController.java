package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.services.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = {"/upload"})
public class FileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @PostMapping(path = {"/single", "/"})
    public ResponseEntity<String> singleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
        return ResponseEntity.ok("");
    }

    @PostMapping(path = {"/multiple", "/multiple/"})
    public ResponseEntity<String> multipleFileUpload(@RequestParam("files") MultipartFile[] files) throws Exception {
        return ResponseEntity.ok("");
    }

}
