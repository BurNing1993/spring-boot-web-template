package com.joey.boot.common.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joey
 */
@RestController
@RequestMapping("/common")
public class FileController {

    @Value("${app.static-location}")
    private String staticLocation;

    @Value("${spring.mvc.static-path-pattern}")
    private String staticPathPattern;

    /**
     * 上传单文件
     *
     * @return
     */
    @PostMapping("/upload")
    public ResponseEntity unload(@RequestPart("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = "file-" + System.currentTimeMillis() + "-" + file.getOriginalFilename();
            try {
                file.transferTo(new File(staticLocation + fileName));
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败!");
            }
            return ResponseEntity.ok(staticPathPattern.replace("**", "") + fileName);
        } else {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("上传的文件为空!");
        }
    }

    /**
     * 上传多文件
     *
     * @return
     */
    @PostMapping("/uploads")
    public ResponseEntity unload(@RequestPart("files") MultipartFile[] files) {
        if (files.length > 0) {
            List<String> fileNames = new ArrayList<>(files.length);
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    String fileName = "file-" + System.currentTimeMillis() + "-" + file.getOriginalFilename();
                    try {
                        file.transferTo(new File(staticLocation + fileName));
                        fileNames.add(fileName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return ResponseEntity.ok(fileNames);
        } else {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("上传的文件为空!");
        }
    }
}
