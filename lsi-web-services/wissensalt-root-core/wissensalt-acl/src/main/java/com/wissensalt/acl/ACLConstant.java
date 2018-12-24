package com.wissensalt.acl;

/**
 * Created on 8/15/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface ACLConstant {
    interface Path {
        String MENU = "/secured/menu";
        String GENERATE_MENU = "/generateMenu";
        String FIND_MENU_BY_ROLE = "/findMenuByRole";
        String CREATE_MENU = "/createMenu";
        String CREATE_MENU_ASSIGN_TO_ROLE = "/createMenuAssignToRole";
        String ASSIGN_MENU_TO_ROLE = "/assignMenuToRole";
        String MERGE_MENU_TO_ROLE = "/mergeMenuToRole";
        String REGISTER_USER = "/secured/registerUser";
        String USER_AGENT = "/secured/userAgent";
        String FIND_LOV_AGENT_LIMIT = "/secured/findLovAgentLimit";
        String FIND_PAGINATION_AGENT_BY_NAME_USERNAME = "/secured/findPaginationAgentByNameAndUserName";
        String FIND_AGENT_BY_USERNAME = "/findAgentByUserName";
        String UPDATE_AGENT_ATTRIBUTE = "/secured/updateAgentAttribute";

        String AREA = "/secured/area";
        String FIND_AREA = "/findArea";
        String INSERT_AREA = "/insertArea";

    }


}
