package com.tripoin.lsi.thirdparty.service;

import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.dto.response.base.ResponseData;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created on 9/11/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IRegistrationService {

    @Transactional
    ResponseData registerBulkUser(String p_Bearer, MultipartFile p_MultiPartFile) throws ServiceException;

    @Transactional
    int registerBulkArea(MultipartFile p_MultiPartFile) throws ServiceException;
}
