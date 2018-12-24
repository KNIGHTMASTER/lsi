package com.wissensalt.acl.endpoint.impl;

import com.wissensalt.acl.dto.request.RequestAreaDTO;
import com.wissensalt.acl.dto.response.ResponseAreaDTO;
import com.wissensalt.acl.dto.response.ResponseFindAreaService;
import com.wissensalt.acl.endpoint.IAreaEndPoint;
import com.wissensalt.acl.service.IAreaService;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class AreaEndPointImpl implements IAreaEndPoint {

    @Autowired
    private IAreaService areaService;

    @Autowired
    private IScaffoldingResponseBuilder scaffoldingResponseBuilder;

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaEndPointImpl.class);

    @RequestLogger(name = "Area|Find Area")
    @Override
    public Page<ResponseAreaDTO> findArea(HttpServletRequest httpServletRequest, @RequestBody RequestAreaDTO p_RequestAreaDTO) throws EndPointException {
        PageRequest pageRequest = scaffoldingResponseBuilder.buildPageRequest(p_RequestAreaDTO);
        try {
            ResponseFindAreaService areaServiceDTO = areaService.findAreaPagination(p_RequestAreaDTO, pageRequest);
            return new PageImpl<>(areaServiceDTO.getContent(), pageRequest, areaServiceDTO.getVillageSize());
        } catch (ServiceException e) {
            LOGGER.error("Error Finding Area {}", e.toString());
            return null;
        }
    }
}
