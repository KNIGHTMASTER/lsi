package com.wissensalt.api.logger.mapper;

import com.wissensalt.api.logger.entity.jwt.JwtLog;
import com.wissensalt.shared.dto.request.jwtlog.RequestJwtLogDTO;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import org.springframework.stereotype.Service;

/**
 * Created on 6/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class JwtLogMapper extends ADATAMapper<RequestJwtLogDTO, JwtLog> {
    @Override
    public JwtLog convert(RequestJwtLogDTO p_RequestJwtLogDTO) {
        JwtLog result = new JwtLog();
        result.setAudience(p_RequestJwtLogDTO.getAudience());
        result.setCreatedDate(p_RequestJwtLogDTO.getCreatedDate());
        result.setExpirationTime(p_RequestJwtLogDTO.getExpirationTime());
        result.setSub(p_RequestJwtLogDTO.getSub());
        return result;
    }
}
