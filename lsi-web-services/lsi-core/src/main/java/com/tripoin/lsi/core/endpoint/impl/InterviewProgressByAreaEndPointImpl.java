package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewProgressByAreaDTO;
import com.tripoin.lsi.core.data.dto.response.ContentAreaProgress2DTO;
import com.tripoin.lsi.core.endpoint.IInterviewProgressByAreaEndPoint;
import com.tripoin.lsi.core.service.IInterviewProgressByAreaService;
import com.tripoin.lsi.core.util.HttpUtil;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.scaffolding.service.IScaffoldingResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created on 12/5/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class InterviewProgressByAreaEndPointImpl implements IInterviewProgressByAreaEndPoint {

    @Autowired
    private IInterviewProgressByAreaService interviewProgressByAreaService;

    @Autowired
    private IScaffoldingResponseBuilder scaffoldingResponseBuilder;

    private static final Logger LOGGER = LoggerFactory.getLogger(InterviewProgressByAreaEndPointImpl.class);

    @Override
    public Page<ContentAreaProgress2DTO> findByArea(HttpServletRequest httpServletRequest, Principal p_Principal, @RequestBody RequestInterviewProgressByAreaDTO p_RequestInterviewProgressByAreaDTO) {
        String bearer = HttpUtil.getHeaderAuthorization(httpServletRequest);
        PageRequest pageRequest = scaffoldingResponseBuilder.buildPageRequest(p_RequestInterviewProgressByAreaDTO);
        if (p_Principal.getName() != null) {
            try {
                return interviewProgressByAreaService.findByArea(bearer, p_Principal.getName(), p_RequestInterviewProgressByAreaDTO, pageRequest);
            } catch (ServiceException e) {
                LOGGER.error("Error Find By Area {}" ,e.toString());
                return null;
            }
        }else {
            LOGGER.error("Principal is Not Found");
            return null;
        }
    }
}
