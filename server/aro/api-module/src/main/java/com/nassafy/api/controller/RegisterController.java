package com.nassafy.api.controller;


import com.nassafy.api.dto.req.ServiceDTO;
import com.nassafy.api.service.InterestService;
import com.nassafy.api.service.JwtService;
import com.nassafy.api.service.MeteorInterestService;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import com.nassafy.core.respository.MeteorInterestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/members")
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private InterestService interestService;
    @Autowired
    MeteorInterestRepository meteorInterestRepository;

    @Autowired
    private JwtService jwtService;

    /***
     * API 11 오로라 서비스 등록 및 수정
     * @param serviceDTO
     * @return
     */
    @PostMapping("/service")
    public ResponseEntity<Void> serviceRegiser(@RequestBody ServiceDTO serviceDTO) {
        logger.debug("\t Start serviceRegister ");

        Long memberId = jwtService.getUserIdFromJWT();
        Member member = memberRepository.findById(memberId).get();
        logger.debug("\t Start memberRepository ");
        if (member.getAuroraService() != serviceDTO.getAuroraService()) {
            member.toggleAuroraService();
            if (!member.getAuroraService()) {
                // 오로라서비스가 false가 됐을 때 기존 명소 데이터 지우기
                interestService.deleteAuroaAttraction(memberId);
            }
        }
        if (member.getMeteorService() != serviceDTO.getMeteorService()) {
            member.toggleMeteorService();
            // 메테오 서비스가 추가 되면 메테오 명소 지우는 로직도 추가 되어야 합니다.
            if (!member.getMeteorService()) {
                meteorInterestRepository.deleteByMemberId(memberId);
                meteorInterestRepository.flush();
            }
        }
        memberRepository.save(member);
        return ResponseEntity.noContent().build();

    }

    /***
     * API 12 - 오로라 서비스 등록 여부 조회
     * @return
     */
    @GetMapping("/service/aurora")
    public ResponseEntity<Boolean> getAuroraService() {
        logger.debug("\t Start getAuroraService ");
        Member member = jwtService.getUserFromEmail();
        return ResponseEntity.ok(member.getAuroraService());
    }

    /***
     * API 13 - 유성우 서비스 등록 여부 조회
     * @return
     */
    @GetMapping("/service/meteor")
    public ResponseEntity<Boolean> getMeteorService(){
        logger.debug("\t Start getMeteorService ");
        Member member = jwtService.getUserFromEmail();
        return ResponseEntity.ok(member.getMeteorService());
    }

    /***
     *API 14 - 알람 서비스 등록 여부 조회
     * @return
     */
    @GetMapping("/alarm")
    public ResponseEntity<Boolean> getAlarm(){
        logger.debug("\t Start getAlarm ");
        Member member = jwtService.getUserFromEmail();
        return ResponseEntity.ok(member.getAlarm());
    }

    /***
     * API 15
     * @return
     */
    @PostMapping("/alarm")
    public ResponseEntity<Void> toggleAlarm() {
        logger.debug("\t Start toggleAlarm ");
        Member member = jwtService.getUserFromEmail();
        member.toggleAlarm();
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
    }

    /***
     * API 16 - 오로라 표시 여부 조회
     * @return
     */
    @GetMapping("/auroraDisplay")
    public ResponseEntity<Boolean> getAuroraDisplay(){
        logger.debug("\t Start getAuroraDisplay ");
        Member member = jwtService.getUserFromEmail();
        return ResponseEntity.ok(member.getAuroraDisplay());
    }

    /***
     * API 17
     * @return
     */
    @PostMapping("/auroraDisplay")
    public ResponseEntity<Void> toggleAuroraDisplay(){
        logger.debug("\t Start toggleAuroraDisplay ");
        Member member = jwtService.getUserFromEmail();
        member.toggleAuroraDisplay();
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
    }

    /**
     * 18번 Api
     * @return 오로라 서비스 등록 여부 메테오 서비스 등록 여부
     */
    @GetMapping("/service/all")
    public ResponseEntity<ServiceDTO> getAllServiceRegisteration() {
        Long memberId = jwtService.getUserIdFromJWT();
        Member member = memberRepository.findById(memberId).get();
        ServiceDTO serviceDTO = new ServiceDTO(member.getAuroraService(), member.getMeteorService());
        return ResponseEntity.ok(serviceDTO);
    }

    /**
     * 100번 Api
     * @return boolen 구름 표시 유무
     */
    // 구름 표시 여부 조회
    @GetMapping("/cloudDisplay")
    public ResponseEntity<Boolean> getCloudDisplay(){
        Long memberId = jwtService.getUserIdFromJWT();
        Member member = memberRepository.findById(memberId).get();
        return ResponseEntity.ok(member.getCloud());
    }

    /**
     * 101번 Api
     * @return no
     */

    // 구를 표시 여부 변경
    @PostMapping("/cloudDisplay")
    public ResponseEntity<Void> toggleCloudDisplay(){
        Long memberId = jwtService.getUserIdFromJWT();
        Member member = memberRepository.findById(memberId).get();
        member.toggleCloudDisplay();
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
    }

}
