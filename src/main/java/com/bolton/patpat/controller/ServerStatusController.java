package com.bolton.patpat.controller;

import com.bolton.patpat.constant.ResponseCodes;
import com.bolton.patpat.dto.json.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("server/status/check")
public class ServerStatusController {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponse> serverStatusCheck() {
        return new ResponseEntity<>(new CommonResponse(ResponseCodes.OPERATION_SUCCESS, "Server Status : RUNNING"), HttpStatus.OK);
    }
}
