package com.nassafy.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> test() {
        String responseJson = "{ \"message\": \"test\" }"; // JSON 형식의 문자열
        return ResponseEntity.ok(responseJson);
    }

}
