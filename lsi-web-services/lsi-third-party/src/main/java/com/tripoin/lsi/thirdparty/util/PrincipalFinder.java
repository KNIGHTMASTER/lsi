package com.tripoin.lsi.thirdparty.util;

import com.tripoin.lsi.thirdparty.client.impl.RoleClientImpl;
import com.tripoin.lsi.thirdparty.client.impl.UserClientImpl;
import com.wissensalt.scaffolding.exception.ServiceException;
import com.wissensalt.shared.dto.request.RequestFindByNameDTO;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.base.GenericSingleDATAResponseDTO;
import com.wissensalt.shared.dto.response.security.ResponseRoleDTO;
import com.wissensalt.shared.dto.response.security.ResponseUserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class PrincipalFinder {

    @Autowired
    private RoleClientImpl roleClient;

    @Autowired
    private UserClientImpl userClient;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrincipalFinder.class);

    public ResponseRoleDTO findRoleByUserName(String p_Bearer, String p_Principal) throws ServiceException{
        ResponseRoleDTO role = null;
        RequestFindByNameDTO requestRole = new RequestFindByNameDTO();
        requestRole.setName(p_Principal);
        GenericListResponseDTO<ResponseRoleDTO> responseRole = roleClient.findRolesByUserName(p_Bearer, requestRole);
        if (responseRole != null) {
            if (responseRole.getContent() != null) {
                role = responseRole.getContent().get(0);
            }else {
                LOGGER.error("Role Is Not Found");
            }
        } else {
            LOGGER.error("Role Is Not Found");
        }
        return role;
    }

    public ResponseUserDTO findUserByUserName(String p_Bearer, String p_UserName) throws ServiceException {
        GenericSingleDATAResponseDTO<ResponseUserDTO> user = userClient.findByUserName(p_Bearer, p_UserName);
        if (user != null) {
            if (user.getContent() != null) {
                return user.getContent();
            }else {
                LOGGER.error("User Is Not Found");
                return null;
            }
        }else {
            LOGGER.error("User is Not Found");
            return null;
        }
    }
}
