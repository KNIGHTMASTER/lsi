package com.wissensalt.acl.dto.response;

import com.wissensalt.shared.dto.response.master.ResponseCityDTO;
import com.wissensalt.shared.dto.response.master.ResponseDistrictDTO;
import com.wissensalt.shared.dto.response.master.ResponseProvinceDTO;
import com.wissensalt.shared.dto.response.master.ResponseVillageDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created on 9/15/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class ContentVolunteerDTO implements Serializable {
    /**
     *
     *
     */
    private static final long serialVersionUID = 2261267288941716307L;

    private String userName;
    private String name;
    private String phoneNumber;
    private ResponseProvinceDTO province;
    private ResponseCityDTO city;
    private ResponseDistrictDTO district;
    private ResponseVillageDTO village;
    private String status;
}
