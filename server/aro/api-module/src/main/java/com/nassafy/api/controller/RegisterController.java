package com.nassafy.api.controller;


import com.nassafy.core.DTO.ServiesRegisterDTO;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RegisterController {

    @Autowired
    private MemberRepository memberRepository;

    // 알람 서비스 등록 여부 조회
    @GetMapping("toggleAlarm/{member_id}")
    public boolean getAlarm(@PathVariable Long member_id){
        Member member = memberRepository.findById(member_id).orElseThrow(
                () -> new EntityNotFoundException("회원이 없습니다")
        );
        return member.getAlarm();
    }

    // 오로라 표시 여부 조회
    @GetMapping("auroraDisplay/{member_id}")
    public boolean getAuroraDisplay(@PathVariable Long member_id){
        Member member = memberRepository.findById(member_id).orElseThrow(
                () -> new EntityNotFoundException("회원이 없습니다")
        );
        return member.getAuroraDisplay();
    }


    // 오로라 서비스 등록 여부 조회
    @GetMapping("servies/aurora/{member_id}")
    public boolean getAuroraService(@PathVariable Long member_id) {
        Member member = memberRepository.findById(member_id).orElseThrow(
                () -> new EntityNotFoundException("회원이 없습니다.")
        );
        return member.getAuroraService();
    }

    // 유성우 서비스 등록 여부 조회
    @GetMapping("servies/meteor/{member_id}")
    public boolean getMeteorService(@PathVariable Long member_id){
        Member member = memberRepository.findById(member_id).orElseThrow(
                () -> new EntityNotFoundException("회원이 없습니다")
        );
        return member.getMeteorService();
    }

    @PutMapping("/toggleAlarm/{member_id}")
    public ResponseEntity<Void> toggleAlarm(@PathVariable Long member_id) {
        Member member = memberRepository.findById(member_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다."));
        member.toggleAlarm();
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/auroraDisplay/{member_id}")
    public ResponseEntity<Void> toggleAuroraDisplay(@PathVariable Long member_id){
        Member member = memberRepository.findById(member_id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다."));
        member.toggleAuroraDisplay();
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
    }
    // 테스트 해봐야함
    @PutMapping("servies/{member_id}")
    public ResponseEntity<Void> serviesRegiser(@PathVariable Long member_id, @RequestParam ServiesRegisterDTO serviesRegisterDTO) {
        Member member = memberRepository.findById(member_id)
                .orElseThrow(
                        () -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다.")
                );
        if (member.getAuroraService() != serviesRegisterDTO.isAuroraServise()) {
            member.toggleAuroraService();
        }
        if (member.getMeteorService() != serviesRegisterDTO.isMeteorServise()) {
            member.toggleMeteorService();
        }
        memberRepository.save(member);
        return ResponseEntity.noContent().build();

    }
}
