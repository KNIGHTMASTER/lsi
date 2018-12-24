package com.tripoin.lsi.core.service;

import com.tripoin.lsi.core.data.dto.request.mobile.RequestMobilePointFilteringDTO;
import com.tripoin.lsi.core.data.dto.request.mobile.RequestMobilePointFilteringIncludeAgentDTO;
import com.tripoin.lsi.core.data.dto.response.ResponseCheckAuthority;
import com.tripoin.lsi.core.data.dto.response.mobile.ResponseMobileFindDetailAssignmentByAgentDTO;
import com.tripoin.lsi.core.data.dto.response.mobile.ResponseMobileInterviewComponent;
import com.tripoin.lsi.core.data.dto.response.mobile.ResponseMobilePointFilteringDTO;
import com.wissensalt.scaffolding.exception.ServiceException;

import java.security.Principal;

/**
 * Created on 9/20/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewHeaderMobileService {

    ResponseMobileFindDetailAssignmentByAgentDTO findDetailAssignment(String p_Bearer, Principal p_Principal) throws ServiceException;

    ResponseCheckAuthority checkAuthority(String p_Bearer, Principal p_Principal, String expectedAuthority) throws ServiceException;

    ResponseMobileInterviewComponent findInterviewComponentByPrincipal(String p_Bearer, Principal p_Principal) throws ServiceException;

    ResponseMobilePointFilteringDTO findPointFiltering(String p_Bearer, Principal p_Principal, RequestMobilePointFilteringDTO p_RequestMobilePointFilteringDTO) throws ServiceException;

    ResponseMobilePointFilteringDTO findPointFilteringIncludeAgent(String p_Bearer, Principal p_Principal, RequestMobilePointFilteringIncludeAgentDTO p_RequestMobilePointFilteringIncludeAgentDTO) throws ServiceException;
}
