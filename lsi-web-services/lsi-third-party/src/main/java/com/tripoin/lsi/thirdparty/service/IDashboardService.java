package com.tripoin.lsi.thirdparty.service;

import com.tripoin.lsi.thirdparty.data.dto.request.RequestInterviewDashboardDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseDashboardInterview;
import com.wissensalt.scaffolding.exception.ServiceException;

/**
 * Created on 9/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IDashboardService {

    ResponseDashboardInterview getDashboardInterview(String p_Bearer, String p_Principal, RequestInterviewDashboardDTO p_RequestInterviewDashboardDTO) throws ServiceException;
}
