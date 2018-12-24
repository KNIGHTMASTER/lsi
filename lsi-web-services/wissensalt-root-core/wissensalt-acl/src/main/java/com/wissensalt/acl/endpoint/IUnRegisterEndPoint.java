package com.wissensalt.acl.endpoint;

import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.shared.dto.response.base.ResponseData;

/**
 * Created on 7/9/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface IUnRegisterEndPoint {

    ResponseData unRegisterCompanyAndAllComponents() throws EndPointException;

    ResponseData unRegisterBranchAndAllComponents() throws EndPointException;

    ResponseData unRegisterUser() throws EndPointException;
}
