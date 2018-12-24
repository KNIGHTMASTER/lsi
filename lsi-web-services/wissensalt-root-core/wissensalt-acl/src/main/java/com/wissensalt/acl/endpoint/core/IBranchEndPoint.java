package com.wissensalt.acl.endpoint.core;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestBranchDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.security.ResponseBranchDTO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Branch End Point", value = "Api Scaffolding Branch", description = "Api Scaffolding for Entity Branch")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.BRANCH)
public interface IBranchEndPoint extends IScaffoldingEndPoint<RequestBranchDTO, ResponseBranchDTO> {

    @GetMapping("/findByUserName")
    ResponseBranchDTO getBranchByUserName(HttpServletRequest httpServletRequest, Principal p_Principal) throws EndPointException;
}
