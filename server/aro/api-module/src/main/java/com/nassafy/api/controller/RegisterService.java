package com.nassafy.api.controller;


import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api")
public class RegisterService {

    @Autowired
    private MemberRepository memberRepository;

    @PutMapping("/{id}/toggleAlarm")
    public ResponseEntity<Void> toggleAlarm(@PathVariable Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다."));
        member.toggleAlarm();
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/auroraDisplay")
    public ResponseEntity<Void> toggleAuroraDisplay(@PathVariable Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 회원이 없습니다."));
        member.toggleAuroraDisplay();
        memberRepository.save(member);
        return ResponseEntity.noContent().build();
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
}
