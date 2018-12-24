package com.tripoin.lsi.core.data.mapper;

import com.tripoin.lsi.core.data.dto.response.ResponseParameterGroupDTO;
import com.tripoin.lsi.core.data.model.ParameterGroup;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.springframework.stereotype.Service;

/**
 * Created on 9/12/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class ParameterGroupMapper extends ADATAMapper<ParameterGroup, ResponseParameterGroupDTO> {
    @Override
    public ResponseParameterGroupDTO convert(ParameterGroup parameterGroup) {
        ResponseParameterGroupDTO result = new ResponseParameterGroupDTO();
        result.setId(parameterGroup.getId());
        result.setCode(parameterGroup.getCode());
        result.setName(parameterGroup.getName());
        result.setRemarks(parameterGroup.getRemarks());
        result.setStatus(parameterGroup.getStatus());
        return result;
    }
}
