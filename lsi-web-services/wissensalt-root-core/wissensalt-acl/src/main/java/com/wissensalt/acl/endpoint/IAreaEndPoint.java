package com.wissensalt.acl.endpoint;

import com.wissensalt.acl.ACLConstant;
import com.wissensalt.acl.dto.request.RequestAreaDTO;
import com.wissensalt.acl.dto.response.ResponseAreaDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Area End Point", value = "Api Area", description = "Api for Areas")
@RequestMapping(value = ACLConstant.Path.AREA)
public interface IAreaEndPoint {

    @ApiOperation(value = "Find Areas")
    @PostMapping(ACLConstant.Path.FIND_AREA)
    Page<ResponseAreaDTO> findArea(HttpServletRequest httpServletRequest, @RequestBody RequestAreaDTO p_RequestAreaDTO) throws EndPointException;
}
