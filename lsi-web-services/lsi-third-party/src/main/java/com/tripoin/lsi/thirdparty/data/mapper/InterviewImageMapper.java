package com.tripoin.lsi.thirdparty.data.mapper;

import com.tripoin.lsi.thirdparty.data.dto.response.ResponseBasicInterviewImageDTO;
import com.tripoin.lsi.thirdparty.data.dto.response.ResponseInterviewImageDTO;
import com.tripoin.lsi.thirdparty.data.model.InterviewImage;
import com.wissensalt.shared.entity.mapper.ADATAMapper;
import com.wissensalt.util.common.time.FormatDateConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 9/10/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Service
public class InterviewImageMapper extends ADATAMapper<InterviewImage, ResponseInterviewImageDTO> {

    @Autowired
    private InterviewDetailMapper interviewDetailsMapper;

    @Override
    public ResponseInterviewImageDTO convert(InterviewImage p_InterviewImage) {
        ResponseInterviewImageDTO result = new ResponseInterviewImageDTO();
        result.setId(p_InterviewImage.getId());
        result.setCode(p_InterviewImage.getCode());
        result.setName(p_InterviewImage.getName());
        result.setRemarks(p_InterviewImage.getRemarks());
        result.setStatus(p_InterviewImage.getStatus());

        if (p_InterviewImage.getUploadTimeStamp() != null) {
            result.setUploadTimeStamp(FormatDateConstant.DEFAULT.format(p_InterviewImage.getUploadTimeStamp()));
        }
        result.setFilePath(p_InterviewImage.getFilePath());
        result.setFileSize(p_InterviewImage.getFileSize());
        result.setFileType(p_InterviewImage.getFileType());

        if (p_InterviewImage.getInterviewDetail() != null) {
            result.setInterviewDetail(interviewDetailsMapper.convert(p_InterviewImage.getInterviewDetail()));
        }
        return result;
    }

    public ResponseBasicInterviewImageDTO convertBasic(InterviewImage p_InterviewImage) {
        ResponseBasicInterviewImageDTO result = new ResponseBasicInterviewImageDTO();
        result.setId(p_InterviewImage.getId());
        result.setCode(p_InterviewImage.getCode());
        result.setName(p_InterviewImage.getName());
        result.setRemarks(p_InterviewImage.getRemarks());
        result.setStatus(p_InterviewImage.getStatus());

        if (p_InterviewImage.getUploadTimeStamp() != null) {
            result.setUploadTimeStamp(FormatDateConstant.DEFAULT.format(p_InterviewImage.getUploadTimeStamp()));
        }
        result.setFilePath(p_InterviewImage.getFilePath());
        result.setFileSize(p_InterviewImage.getFileSize());
        result.setFileType(p_InterviewImage.getFileType());
        return result;
    }
}
