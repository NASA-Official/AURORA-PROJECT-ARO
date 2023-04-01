package com.nassafy.api.controller;
import com.nassafy.api.dto.req.AttractionInterest;
import com.nassafy.api.dto.req.AttractionInterestOrNotDTO;
import com.nassafy.api.dto.req.InterestListDTO;
import com.nassafy.api.dto.res.InterestProbabiliyResDTO;
import com.nassafy.api.service.InterestService;
import com.nassafy.api.service.JwtService;
import com.nassafy.api.service.MemberService;
import com.nassafy.core.entity.Interest;
import com.nassafy.core.entity.Member;
import com.nassafy.core.respository.InterestRepository;
import com.nassafy.core.respository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/interest")
@RequiredArgsConstructor
@Slf4j
public class InteresetController {
    private final InterestService interestService;
    private final InterestRepository interestRepository;
    private final JwtService jwtService;

    private final EntityManager em;
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
    public ResponseEntity<List<AttractionInterest>> getAttreactionInterest(@PathVariable String nationName) {
        Long memberId = jwtService.getUserIdFromJWT();
        List<AttractionInterest> attractionInterestList = interestService.getAttractionInterest(nationName,memberId);
        return ResponseEntity.ok(attractionInterestList);
    }


    // 43번 Api
    @GetMapping("")
    public ResponseEntity<InterestListDTO> getInterest() {
        Long memberId = jwtService.getUserIdFromJWT();
        InterestListDTO interestListDTO = interestService.getInterest(memberId);
        return ResponseEntity.ok(interestListDTO);
    }


    // 44번 Api
    @DeleteMapping("/{interestId}")
    public ResponseEntity<Void> deleteInterest(@PathVariable Long interestId) {
        Optional<Interest> interest = interestRepository.findById(interestId);
        if (interest.isPresent()) {
            interestRepository.delete(interest.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/probability/{attractionId}/{date}/{time}")
    public ResponseEntity<InterestProbabiliyResDTO> getProbability(@PathVariable Long attractionId, @PathVariable String date, @PathVariable int time) {
        String[] dates = date.split("-");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]), time, 0);

        try {
            InterestProbabiliyResDTO reseult = interestService.getProbability(attractionId, dateTime);
            return new ResponseEntity<>(reseult, HttpStatus.OK);
        } catch (Exception e) {
            log.info(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
