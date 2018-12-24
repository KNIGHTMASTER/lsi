package com.tripoin.lsi.core.endpoint;

import com.tripoin.lsi.core.data.dto.request.RequestInterviewProgressByAreaDTO;
import com.tripoin.lsi.core.data.dto.response.ContentAreaProgress2DTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created on 12/5/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@RequestMapping("/secured/interviewProgressByArea")
public interface IInterviewProgressByAreaEndPoint {

    @PostMapping("/byArea")
    Page<ContentAreaProgress2DTO> findByArea(HttpServletRequest httpServletRequest, Principal p_Principal, @RequestBody RequestInterviewProgressByAreaDTO p_RequestInterviewProgressByAreaDTO);
}
