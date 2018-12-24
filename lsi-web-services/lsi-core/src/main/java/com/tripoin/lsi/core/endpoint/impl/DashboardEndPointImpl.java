package com.tripoin.lsi.core.endpoint.impl;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewDashboardDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseDashboardInterview;
import com.tripoin.lsi.core.endpoint.IDashboardEndPoint;
import com.tripoin.lsi.core.service.IDashboardService;
import com.tripoin.lsi.core.util.HttpUtil;
import com.wissensalt.scaffolding.apilogger.RequestLogger;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created on 9/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RestController
public class DashboardEndPointImpl implements IDashboardEndPoint {

    @Autowired
    private IDashboardService dashboardService;

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardEndPointImpl.class);

    @RequestLogger(name = "Dashboard|Interview")
    @Override
    public ResponseDashboardInterview getDashboardInterview(HttpServletRequest httpServletRequest, Principal p_Principal,  @RequestBody RequestInterviewDashboardDTO p_RequestInterviewDashboardDTO) throws EndPointException {
        String bearer = HttpUtil.getHeaderAuthorization(httpServletRequest);
        try {
            if (p_Principal.getName() != null) {
                return dashboardService.getDashboardInterview(bearer, p_Principal.getName(), p_RequestInterviewDashboardDTO);
            }else {
                LOGGER.error("Principal Is Not Found");
                return null;
            }
        } catch (ServiceException e) {
            LOGGER.error("Error Get Dashboard Interview {}", e.toString());
            return null;
        }
    }
}
