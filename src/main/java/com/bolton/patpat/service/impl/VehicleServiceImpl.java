package com.bolton.patpat.service.impl;

import com.bolton.patpat.constant.ResponseCodes;
import com.bolton.patpat.dto.VehicleDTO;
import com.bolton.patpat.dto.json.VehicleFilterRequestDTO;
import com.bolton.patpat.service.VehicleService;
import com.bolton.patpat.entity.VehicleEntity;
import com.bolton.patpat.repository.VehicleRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ModelMapper modelMapper;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, ModelMapper modelMapper) {
        this.vehicleRepository = vehicleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<VehicleDTO> filterVehicleDetails(VehicleFilterRequestDTO requestDTO) {
        try {

            List<VehicleEntity> filterVehicleList = vehicleRepository.filterVehicle(requestDTO.getModel(), requestDTO.getTitle(), requestDTO.getBrand(),
                    requestDTO.getDescription(), requestDTO.getLocation(), requestDTO.getUsedType(), requestDTO.getFuelType(),
                    requestDTO.getYear(), ResponseCodes.WEBSITE);

            if (filterVehicleList == null || filterVehicleList.isEmpty())
                return new ArrayList<>();

            return filterVehicleList.stream().map(this::mapEntityToDTO).collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Method filterVehicleDetails : " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public Page<VehicleDTO> filterVehicleDetailsWithPagination(VehicleFilterRequestDTO requestDTO) {
        try {

            Pageable pageable = PageRequest.of(requestDTO.getPage(), requestDTO.getSize());

            Page<VehicleEntity> filterVehicleWithPagination = vehicleRepository.filterVehicleWithPagination(requestDTO.getModel(), requestDTO.getTitle(), requestDTO.getBrand(),
                    requestDTO.getDescription(), requestDTO.getLocation(), requestDTO.getUsedType(), requestDTO.getFuelType(),
                    requestDTO.getYear(), ResponseCodes.WEBSITE, pageable);

            return filterVehicleWithPagination.map(this::mapEntityToDTO);

        } catch (Exception e) {
            log.error("Method filterVehicleDetailsWithPagination : " + e.getMessage(), e);
            throw e;
        }
    }

    @Override
    public List<VehicleDTO> getAllVehicleDetails() {
        List<VehicleEntity> all = vehicleRepository.findAll();
        return modelMapper.map(all, new TypeToken<List<VehicleDTO>>() {
        }.getType());
    }

    private VehicleDTO mapEntityToDTO(VehicleEntity vehicleEntity) {
        return modelMapper.map(vehicleEntity, VehicleDTO.class);
    }
}
