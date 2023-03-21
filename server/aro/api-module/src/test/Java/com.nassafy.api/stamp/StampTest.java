package com.nassafy.api.stamp;


import com.nassafy.api.service.StampService;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import com.nassafy.core.respository.StampRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StampTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private StampService stampService;

    @Autowired
    private StampRepository stampRepository;

    @Test
    @Transactional
    public void makeStamp() throws Exception {

        //given
        Member member1 = memberRepository.save(Member.builder().build());

        //when
        Integer cnt = stampService.makeStamp(member1.getId());
        Integer countstamp = stampRepository.findByMemberId(member1.getId()).size();

        //then
        Assertions.assertThat(cnt).isEqualTo(countstamp);

    }
}
