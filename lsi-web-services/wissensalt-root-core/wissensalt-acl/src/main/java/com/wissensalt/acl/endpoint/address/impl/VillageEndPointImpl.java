package com.wissensalt.acl.endpoint.address.impl;

import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.dto.request.RequestVillageDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.integration.AScaffoldingEndPoint;
import com.wissensalt.acl.endpoint.address.IVillageEndPoint;
import com.wissensalt.scaffolding.service.address.IVillageService;
import com.wissensalt.shared.constant.EResponseCode;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.ResponseData;
import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import com.wissensalt.shared.entity.mapper.master.VillageMapper;
import com.wissensalt.shared.entity.master.Village;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class VillageEndPointImpl extends AScaffoldingEndPoint<Village, RequestVillageDTO, ResponseVillageDTO> implements IVillageEndPoint {

    @Autowired
    private IVillageService villageService;

    @Autowired
    private VillageMapper villageMapper;

    @PostConstruct
    @Override
    public void initEndPoint() {
        scaffoldingService = villageService;
        dataMapperIntegration = villageMapper;
    }

    @RequestLogger(name = "Village|Lov Village By District Id")
    @Override
    public GenericListResponseDTO lovVillageByDistrictId(HttpServletRequest httpServletRequest, @RequestBody Long p_DistrictId) {
        GenericListResponseDTO result = new GenericListResponseDTO();
        result.setResponseData(new ResponseData(EResponseCode.RC_FAILURE.getResponseCode(), EResponseCode.RC_FAILURE.getResponseMsg()));
        try {
            try {
                result = scaffoldingResponseBuilder.buildListDTOResponse(villageService.selectLOVByDistrictId(p_DistrictId));
            } catch (ServiceException e) {
                LOGGER.error("Error Searching LOV Data : {}", e.toString());
            }
        } catch (EndPointException e) {
            LOGGER.error("Error Searching LOV Data : {}", e.toString());
        }
        return result;
    }
}
