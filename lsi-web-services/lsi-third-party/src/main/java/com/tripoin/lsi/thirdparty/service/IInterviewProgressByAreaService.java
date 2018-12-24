package com.tripoin.lsi.thirdparty.service;

import com.tripoin.lsi.thirdparty.data.dto.request.RequestInterviewProgressByAreaDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ContentAreaProgress2DTO;
import com.wissensalt.scaffolding.exception.ServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created on 12/5/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IInterviewProgressByAreaService {

    Page<ContentAreaProgress2DTO> findByArea(String p_Bearer, String p_Principal, @RequestBody RequestInterviewProgressByAreaDTO p_RequestInterviewProgressByAreaDTO, PageRequest p_PageRequest) throws ServiceException ;
}
