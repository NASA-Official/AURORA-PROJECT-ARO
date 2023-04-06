package com.nassafy.batch.controller;

import com.nassafy.batch.scheduler.FCMScheduler;
import com.nassafy.core.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/batch/fcm")
public class FcmController {
    private static final Logger logger = LoggerFactory.getLogger(FcmController.class);
    private final FCMScheduler fcmScheduler;

    /***
     * API 00
     * @param
     * @return
     */
    @GetMapping("/push")
    public ResponseEntity<?> pushNotification() throws IOException {
        logger.debug("\t Start pushNotification");

        fcmScheduler.pushMessage();

        return ResponseEntity.ok().build();
    }

}

