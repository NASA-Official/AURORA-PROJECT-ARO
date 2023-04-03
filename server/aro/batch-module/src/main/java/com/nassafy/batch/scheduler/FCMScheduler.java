package com.nassafy.batch.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.gson.JsonParseException;
import com.nassafy.batch.dto.notificcation.FcmMessage;
import com.nassafy.core.entity.Interest;
import com.nassafy.core.entity.Member;
import com.nassafy.core.entity.Probability;
import com.nassafy.core.respository.MemberRepository;
import com.nassafy.core.respository.ProbabilityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FCMScheduler {

    private static final Logger logger = LoggerFactory.getLogger(FCMScheduler.class);

    private final MemberRepository memberRepository;
    private final ProbabilityRepository probabilityRepository;
    private final ObjectMapper objectMapper;
    private FirebaseApp firebaseApp;

    // application yml 설정파일에 설정한 값 사용
    @Value("${fcm.key.path}")
    private String firebaseConfig;

    @Value("${fcm.api_url}")
    private String API_URL;

    @Value("${fcm.key.scope}")
    private String scope;

    @Value("${fcm.projectID}")
    private String projectID;


    @PostConstruct
    private void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("firebase/" + firebaseConfig).getInputStream()))
                    .setProjectId(projectID)
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                this.firebaseApp = FirebaseApp.initializeApp(options);
                logger.info("Firebase application has been initialized");
            } else {
                this.firebaseApp = FirebaseApp.getInstance();
            }
        } catch (IOException e) {
            logger.error("Create FirebaseApp Error", e);
        }
    }

//    @Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0/10 * * * * ?")
    @Transactional
    public void pushMessage() throws IOException {
        log.info("pushMessage - scheduler");

        StringBuilder sb;
        Probability maxProbability = null;
        // 1. 모든 유저에 대해서
        List<Member> members = memberRepository.findAll();
        for(Member member : members){
            // 2. 알람 여부 확인 및 FCM 토큰 확인
            if(!member.getAlarm() || member.getFcmToken() == null || member.getFcmToken().equals("")) continue;
            logger.info("member : " + member.toString());
            logger.info("member : " + member.getEmail() + ", " + member.getNickname());
//            logger.info("Interest : " + member.getInterests().toArray());
            // 3. 유저의 관심지역에 대해서
            for(Interest interest : member.getInterests()){
                Long attrectionId = interest.getAttraction().getId();

                // 4. 관심지역의 확률이 가장 높은 하나 선택
                List<Probability> probabilities = probabilityRepository.findByAttractionId(attrectionId);

                for(Probability probability : probabilities){
                    if(maxProbability == null || maxProbability.getProb() < probability.getProb()){
                        maxProbability = probability;
                    }
                }
            }

            if(maxProbability != null && maxProbability.getProb() >= 0){
                sb  = new StringBuilder();
                sb.append(maxProbability.getDateTime()).append(" ")
                        .append(maxProbability.getAttraction().getAttractionName()).append("의 관측 확률은")
                        .append(maxProbability.getProb()).append("% 입니다!");

                log.info("pushMessage - maxProbability");
                log.info(maxProbability.toString());
                log.info("pushMessage - getFcmToken : " + member.getFcmToken());
                sendMessageTo(
                        member.getFcmToken(),
                        member.getNickname() + "님!",
                        sb.toString()
                );
            }

        }

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

        String firebaseConfigPath = "firebase/" + firebaseConfig;
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of(scope));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}
