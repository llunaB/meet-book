package org.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.example.conference.ConferenceRequest;
import org.example.conference.ConferenceResponse;
import org.example.service.ConferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin // CORS Issue prevent
@AllArgsConstructor
@RestController
@RequestMapping("/conference")
public class ConferenceController {

    private final ConferenceService conferenceService;

    @PostMapping("")
    public ResponseEntity<Map<String, String>> makeConf(@RequestBody ConferenceRequest conf){

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", "회의 생성 성공");
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
    }

    @GetMapping("/list/{pageno}")
    public ResponseEntity<List<ConferenceResponse>> list(@PathVariable("pageno") String pageno){
        ArrayList<ConferenceResponse> list = new ArrayList<ConferenceResponse>();
        list.add(new ConferenceResponse());

        return new ResponseEntity<List<ConferenceResponse>>(list, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConferenceResponse> info(@PathVariable("id") String id){
        System.out.println("conference id : "+id);
        return new ResponseEntity<ConferenceResponse>(new ConferenceResponse(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String,String>> updateConf(@PathVariable("id") String id){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", "회의 수정 성공");
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteConf(@PathVariable("id") String id){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("message", "회의 삭제 성공");
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<ConferenceResponse> detailInfo(@PathVariable("id") String id){
        System.out.println("conference id : "+id);
        return new ResponseEntity<ConferenceResponse>(new ConferenceResponse(), HttpStatus.OK);
    }

}
