package com.wissensalt.acl.service.impl;

import com.wissensalt.acl.dto.request.RequestAreaDTO;
import com.wissensalt.acl.dto.response.ResponseAreaDTO;
import com.wissensalt.acl.dto.response.ResponseFindAreaService;
import com.wissensalt.acl.service.IAreaService;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.address.*;
import com.wissensalt.shared.entity.mapper.master.VillageMapper;
import com.wissensalt.shared.entity.master.Village;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class AreaServiceImpl implements IAreaService {

    @Autowired
    private IVillageService villageService;

    @Autowired
    private VillageMapper villageMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaServiceImpl.class);


    public ResponseFindAreaService findAreaPagination(RequestAreaDTO p_RequestAreaDTO, Pageable pageRequest) throws ServiceException {
        ResponseFindAreaService result = new ResponseFindAreaService();
        List<ResponseAreaDTO> contents = new ArrayList<>();

        List<Village> villages = new ArrayList<>();
        Integer villageSize = 0;

        if (p_RequestAreaDTO.getProvinceId() != null && p_RequestAreaDTO.getCityId() != null && p_RequestAreaDTO.getDistrictId() != null && p_RequestAreaDTO.getVillageId() != null) {
            LOGGER.info("Filter All");
            villageSize = villageService.countFindById(p_RequestAreaDTO.getVillageId());
            villages.add(villageService.findById(p_RequestAreaDTO.getVillageId()));
        }
        if (p_RequestAreaDTO.getProvinceId() == null && p_RequestAreaDTO.getCityId() == null && p_RequestAreaDTO.getDistrictId() == null && p_RequestAreaDTO.getVillageId() == null) {
            LOGGER.info("No Filter All");
            villageSize = villageService.countAll();
            villages = villageService.findAll(pageRequest).getContent();
        }
        if (p_RequestAreaDTO.getProvinceId() != null && p_RequestAreaDTO.getCityId() == null && p_RequestAreaDTO.getDistrictId() == null && p_RequestAreaDTO.getVillageId() == null) {
            LOGGER.info("Filter Only Province");
            villageSize = villageService.countByProvinceId(p_RequestAreaDTO.getProvinceId());
            villages = villageService.findByProvinceId(p_RequestAreaDTO.getProvinceId(), pageRequest).getContent();
        }
        if (p_RequestAreaDTO.getProvinceId() != null && p_RequestAreaDTO.getCityId() != null && p_RequestAreaDTO.getDistrictId() == null && p_RequestAreaDTO.getVillageId() == null) {
            LOGGER.info("Filter Province And City");
            villageSize = villageService.countByCityId(p_RequestAreaDTO.getCityId());
            villages = villageService.findByCityId(p_RequestAreaDTO.getCityId(), pageRequest).getContent();
        }
        if (p_RequestAreaDTO.getProvinceId() != null && p_RequestAreaDTO.getCityId() != null && p_RequestAreaDTO.getDistrictId() != null && p_RequestAreaDTO.getVillageId() == null) {
            LOGGER.info("Filter Province And City And District");
            villageSize = villageService.countByDistrictId(p_RequestAreaDTO.getDistrictId());
            villages = villageService.findVillagesByDistrictId(p_RequestAreaDTO.getDistrictId(), pageRequest).getContent();
        }

        if (villages.size() > 0) {
            for (Village village : villages) {
                ResponseAreaDTO responseAreaDTO = new ResponseAreaDTO();
                if (village.getDistrict() != null) {
                    responseAreaDTO.setDistrict(village.getDistrict().getName());
                    if (village.getDistrict().getCity() != null) {
                        responseAreaDTO.setCity(village.getDistrict().getCity().getName());
                        if (village.getDistrict().getCity().getProvince() != null) {
                            responseAreaDTO.setProvince(village.getDistrict().getCity().getProvince().getName());
                        }
                    }
                }
                if (village.getName() != null) {
                    responseAreaDTO.setVillage(villageMapper.convert(village));
                }
                contents.add(responseAreaDTO);
            }
        }
        result.setContent(contents);
        result.setVillageSize(villageSize);
        return result;
    }
}
