package com.nassafy.api.controller;
import com.nassafy.api.dto.req.AttractionInterestOrNotDTO;
import com.nassafy.api.service.InterestService;
import com.nassafy.api.service.JwtService;
import com.nassafy.api.service.MemberService;
import com.nassafy.core.entity.Interest;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/interest")
@RequiredArgsConstructor
public class InteresetController {
    private final InterestService interestService;
    private final JwtService jwtService;

    private final MemberRepository memberRepository;
    // 41번 Api
    @PostMapping("")
    public ResponseEntity<String> registerInterest(@RequestBody Map<String, Object> requestBody) {
        Long memberId = jwtService.getUserIdFromJWT();
        List<Integer> ids = (List<Integer>) requestBody.get("attractionIds");
        List<Long> attractionIds = ids.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        interestService.registerInterest(memberId, attractionIds);
        return ResponseEntity.ok("success");
    }



    // 42번 Api
    @GetMapping("/{nationName}")
    public ResponseEntity<List<AttractionInterestOrNotDTO>> getAttreactionInterestOrNot(@PathVariable String nationName) {
        Long memberId = jwtService.getUserIdFromJWT();
        List<AttractionInterestOrNotDTO> attractionInterestOrNotDTOList = interestService.getAttractionInterestOrNot(nationName,memberId);
        return ResponseEntity.ok(attractionInterestOrNotDTOList);
    }



}
