package com.wissensalt.acl.endpoint.address;

import com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant;
import com.wissensalt.scaffolding.dto.request.RequestProvinceDTO;
import com.wissensalt.scaffolding.exception.EndPointException;
import com.wissensalt.scaffolding.integration.IScaffoldingEndPoint;
import com.wissensalt.shared.dto.response.base.GenericListResponseDTO;
import com.wissensalt.shared.dto.response.master.ResponseProvinceDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import static com.wissensalt.scaffolding.constant.ScaffoldingSecurityConstant.SecuredPath.ProvincePathDetails.LOV_PROVINCE_BY_COUNTRY_ID;

/**
 * Created on 5/25/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Api(tags = "Province End Point", value = "Api Scaffolding Province", description = "Api Scaffolding for Entity Province")
@RequestMapping(value = ScaffoldingSecurityConstant.SecuredPath.PROVINCE)
public interface IProvinceEndPoint extends IScaffoldingEndPoint<RequestProvinceDTO, ResponseProvinceDTO> {

    @ResponseBody
    @PostMapping(value = LOV_PROVINCE_BY_COUNTRY_ID, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Lov Province By Country Id", notes = "Lov Province By Country Id", response = GenericListResponseDTO.class)
    GenericListResponseDTO lovCityByProvinceId(HttpServletRequest httpServletRequest, @RequestBody Long p_CountryId) throws EndPointException;
}
