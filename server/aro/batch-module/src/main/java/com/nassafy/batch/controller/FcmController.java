package com.nassafy.batch.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.nassafy.batch.dto.notificcation.NotificationData;
import com.nassafy.batch.dto.notificcation.NotificationRequestDTO;
import com.nassafy.batch.dto.notificcation.RequestDTO;
import com.nassafy.batch.fcm.FirebaseCloudMessageService;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/batch/fcm")
public class FcmController {
    private final MemberRepository memberRepository;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Value("${fcm.fcm_token}")
    private String FCM_TOKEN;

    public FcmController(MemberRepository memberRepository, FirebaseCloudMessageService firebaseCloudMessageService) {
        this.memberRepository = memberRepository;
        this.firebaseCloudMessageService = firebaseCloudMessageService;
    }

    private static final Logger logger = LoggerFactory.getLogger(FcmController.class);

    //  @RequestBody RequestDTO requestDTO
    @PostMapping("/push")
    public ResponseEntity<?> pushMessage() throws FirebaseMessagingException, IOException {
        logger.debug("\t Start pushMessage ");

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = (User)authentication.getPrincipal();
//
//        Member member = memberRepository.findByEmail(user.getUsername()).orElseThrow(
//                () -> new EntityNotFoundException("회원이 없습니다.")
//        );
//
//        firebaseCloudMessageService.sendMessageTo(
//                requestDTO.getTargetToken(),
//                requestDTO.getTitle(),
//                requestDTO.getBody());

//        String token = "dI4MUZQKQym7ZhayQVTa_s:APA91bFXkz6cYoM2gO7KGzVxH1G48D4KDk2czfUnqtF5y_zaFokh4Ho_MXo0GCs9-WDnNlv9Lt_E2Cilk9PUR3DW4D0ghaugMy8Aik10S3Vqs03SAgtC62lqS--y6C38tXo6PdHRlu-r";
        firebaseCloudMessageService.sendMessageTo(
                FCM_TOKEN,
            "NASSAFY - Title",
                "NASSAFY - Body");

//        NotificationRequestDTO msgDTO = new NotificationRequestDTO();
//
//        NotificationData notificationData = new NotificationData();
//        notificationData.setTitle("NASSAFY");
//        notificationData.setBody("NASSAFY-PUSH TEST");
//
//        msgDTO.setRegistration_ids(token);
//        msgDTO.setNotification(notificationData);
//
//        String response = firebaseCloudMessageService.sendPushToDevice(msgDTO);

        return ResponseEntity.ok("");

    }
}
