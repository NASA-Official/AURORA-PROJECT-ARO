package com.nassafy.batch.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.gson.JsonParseException;
import com.nassafy.batch.controller.FcmController;
import com.nassafy.batch.dto.notificcation.FcmMessage;
import com.nassafy.batch.dto.notificcation.NotificationData;
import com.nassafy.batch.dto.notificcation.NotificationRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * 푸시 알림 발송 Service
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FirebaseCloudMessageService {
    private static final Logger logger = LoggerFactory.getLogger(FirebaseCloudMessageService.class);


    // application yml 설정파일에 설정한 값 사용
    @Value("${fcm.key.path}")
    private String firebaseConfig;

    @Value("${fcm.api_url}")
    private String API_URL;

    @Value("${fcm.key.scope}")
    private String scope;

    private final ObjectMapper objectMapper;

    public String sendPushToDevice(NotificationRequestDTO msgDTO){
        String response = null;

        try{
            if(msgDTO != null && msgDTO.getRegistration_ids() != null && !msgDTO.getRegistration_ids().equals("")){
                NotificationData notificationData = msgDTO.getNotification();

                Message message = Message.builder()
                        .setToken(msgDTO.getRegistration_ids())
                        .setNotification(new Notification(notificationData.getTitle(), notificationData.getBody()))
                        .putData("content", notificationData.getTitle())
                        .putData("body", notificationData.getBody())
                        .build();

                response = FirebaseMessaging.getInstance().send(message);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }

    public void sendMessageTo(String targetToken, String title, String body) throws IOException {
        String message = makeMessage(targetToken, title, body);

        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
                MediaType.get("application/json; charset=utf-8"));
        String accessToken = getAccessToken();
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        log.info(request.toString());
        Response response = client.newCall(request).execute();

        log.info(response.body().string());
    }

    // 파라미터를 FCM이 요구하는 body 형태로 만들어준다.
    private String makeMessage(String targetToken, String title, String body) throws JsonParseException, JsonProcessingException {
        FcmMessage fcmMessage = FcmMessage.builder()
                .message(FcmMessage.Message.builder()
                        .token(targetToken)
                        .notification(FcmMessage.Notification.builder()
                                .title(title)
                                .body(body)
                                .image(null)
                                .build()
                        ).build()).validateOnly(false).build();

        return objectMapper.writeValueAsString(fcmMessage);
    }

    private String getAccessToken() throws IOException {
//        String firebaseConfigPath = "firebase/" + firebaseConfig;
        String firebaseConfigPath = firebaseConfig;
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of(scope));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}
