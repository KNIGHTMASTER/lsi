package com.tripoin.lsi.thirdparty.service;

import com.tripoin.lsi.thirdparty.data.dto.request.RequestAgentAssignmentDTO;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestFindUserAgentDTO;
import com.tripoin.lsi.thirdparty.data.dto.request.RequestInsertAssignUserAgentDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseDetailUserAgentById;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseUserAgentDTO;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created on 9/17/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IUserAgentService {

    Page<ResponseUserAgentDTO> findUserAgents(String p_Bearer, RequestFindUserAgentDTO p_RequestFindUserAgentDTO, PageRequest p_PageRequest) throws ServiceException;

    @Transactional
    ResponseData insertUserAgent(String p_Bearer, RequestInsertAssignUserAgentDTO p_RequestInsertAssignUserAgentDTO) throws ServiceException;

    ResponseDetailUserAgentById findUserAgentById(String p_Bearer, Long p_UserAgentId) throws ServiceException;

    @Transactional
    ResponseData assignUserAgent(String p_Bearer, RequestAgentAssignmentDTO p_RequestAgentAssignmentDTO) throws ServiceException;
}
