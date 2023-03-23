package com.nassafy.api.controller;
import com.nassafy.api.dto.req.AttractionInterestOrNotDTO;
import com.nassafy.api.service.InterestService;
import com.nassafy.core.entity.Interest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/interest")
@RequiredArgsConstructor
public class InteresetController {
    private final InterestService interestService;

    // 41번 Api
    @PostMapping("")
    public ResponseEntity<String> registerInterest(@RequestParam Long memberId, @RequestBody Map<String, Object> requestBody) {
        List<Integer> ids = (List<Integer>) requestBody.get("attractionIds");
        List<Long> attractionIds = ids.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        interestService.registerInterest(memberId, attractionIds);
        return ResponseEntity.ok("success");
    }



    // 42번 Api
    @GetMapping("/{nationName}/{memberId}")
    public ResponseEntity<List<AttractionInterestOrNotDTO>> getAttreactionInterestOrNot(@PathVariable String nationName, @PathVariable Long memberId) {
        List<AttractionInterestOrNotDTO> attractionInterestOrNotDTOList = interestService.getAttractionInterestOrNot(nationName,memberId);
        return ResponseEntity.ok(attractionInterestOrNotDTOList);
    }



}
