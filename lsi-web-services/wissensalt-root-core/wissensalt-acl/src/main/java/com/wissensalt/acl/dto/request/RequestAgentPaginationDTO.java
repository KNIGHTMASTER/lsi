package com.wissensalt.acl.dto.request;

import com.wissensalt.shared.dto.request.PaginationDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 9/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
@Getter
@Setter
public class RequestAgentPaginationDTO extends PaginationDTO{
    /**
     *
     *
     */
    private static final long serialVersionUID = 6530148073147630715L;

    private String name;
    private String userName;
}
