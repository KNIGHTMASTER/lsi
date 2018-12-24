package com.wissensalt.acl.endpoint.address;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestVillageDTO;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Village End Point", value = "Api Scaffolding Village", description = "Api Scaffolding for Entity Village")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.VILLAGE)
public interface IVillageEndPoint extends IScaffoldingEndPoint<RequestVillageDTO, ResponseVillageDTO> {

    @ResponseBody
    @PostMapping(value = ScaffoldingSecurityConstant.SecuredPath.LOV_VILLAGE_BY_DISTRICT_ID, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Lov Village By District Id", notes = "Lov Village By District Id", response = GenericListResponseDTO.class)
    GenericListResponseDTO lovVillageByDistrictId(HttpServletRequest httpServletRequest, @RequestBody Long p_DistrictId);

}
