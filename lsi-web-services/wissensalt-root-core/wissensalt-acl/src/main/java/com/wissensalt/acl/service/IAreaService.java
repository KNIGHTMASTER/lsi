package com.wissensalt.acl.service;

import com.wissensalt.acl.dto.request.RequestAreaDTO;
import com.wissensalt.acl.dto.response.ResponseFindAreaService;
import com.wissensalt.scaffolding.exception.ServiceException;
import org.springframework.data.domain.Pageable;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IAreaService {

    ResponseFindAreaService findAreaPagination(RequestAreaDTO p_RequestAreaDTO, Pageable p_Pageable) throws ServiceException;
}
