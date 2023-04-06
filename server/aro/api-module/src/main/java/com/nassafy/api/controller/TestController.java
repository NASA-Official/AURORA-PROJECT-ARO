package com.nassafy.api.controller;

import com.nassafy.api.util.S3Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final S3Util s3Util;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test() {
        String responseJson = "{ \"message\": \"test\" }"; // JSON 형식의 문자열 반환
        return ResponseEntity.ok(responseJson);
    }

    @PostMapping("/article")
    public ResponseEntity<String> createArticle (
            @RequestPart("image") MultipartFile multipartFile) {
        String fileName = "";
        String nickname = "신짱구";
        String attractionId = "1";
        try {
            fileName = s3Util.upload(multipartFile, "dailyRecord" + "/" + nickname + "/" + attractionId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(fileName, HttpStatus.OK);
    }


    @DeleteMapping("/article")
    public ResponseEntity<String> deleteArticle (@RequestParam("url") String url) {
        log.info(url);
        log.info(url.substring(48));
        String result = s3Util.delete(url.substring(48));

        return new ResponseEntity<String>(result, HttpStatus.OK);
    }
}
