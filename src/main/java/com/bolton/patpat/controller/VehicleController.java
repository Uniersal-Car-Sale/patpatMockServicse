package com.bolton.patpat.controller;

import com.bolton.patpat.constant.ResponseCodes;
import com.bolton.patpat.dto.json.CommonResponse;
import com.bolton.patpat.dto.json.VehicleFilterRequestDTO;
import com.bolton.patpat.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("api/v1/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/view-all")
    public ResponseEntity<?> getAllCustomerDetails() {
        try {
            return ResponseEntity.ok(vehicleService.getAllVehicleDetails());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/")
    public ResponseEntity<CommonResponse> filterVehicleDetails(@RequestBody VehicleFilterRequestDTO requestDTO) {
        return (requestDTO.getPage() == 0 && requestDTO.getSize() == 0) ?
                new ResponseEntity<>(new CommonResponse(ResponseCodes.OPERATION_SUCCESS, vehicleService.filterVehicleDetails(requestDTO)
                        , ResponseCodes.SUCCESS_RESPONSE), HttpStatus.OK)

                : new ResponseEntity<>(new CommonResponse(ResponseCodes.OPERATION_SUCCESS, vehicleService.filterVehicleDetailsWithPagination(requestDTO)
                , ResponseCodes.SUCCESS_RESPONSE), HttpStatus.OK);
    }
}
