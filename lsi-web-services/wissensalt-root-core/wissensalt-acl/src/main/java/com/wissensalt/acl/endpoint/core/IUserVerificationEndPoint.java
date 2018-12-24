package com.wissensalt.acl.endpoint.core;

import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.shared.dto.response.security.ResponseUserVerificationDTO;
import com.wissensalt.shared.entity.security.SecurityUserVerification;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.USER_VERIFICATION)
public interface IUserVerificationEndPoint extends IScaffoldingEndPoint<SecurityUserVerification, ResponseUserVerificationDTO> {
}
