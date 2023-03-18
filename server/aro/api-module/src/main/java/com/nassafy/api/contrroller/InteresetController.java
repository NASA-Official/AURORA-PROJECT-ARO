package com.nassafy.api.contrroller;
import com.nassafy.api.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aurora")
@RequiredArgsConstructor
public class InteresetController {
    private final InterestService interestService;

    //관심 지역 등록 테스트 해봐야함
    @PostMapping("/favorite")
    public ResponseEntity<String> registerInterest(@RequestParam Long memberId, @RequestBody List<Long> attractionIds) {
        interestService.registerInterest(memberId, attractionIds);
        return ResponseEntity.ok("success");
    }

}
