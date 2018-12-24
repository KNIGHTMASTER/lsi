package com.tripoin.lsi.core;

/**
 * Created on 9/13/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public interface LsiCoreConstant {

    interface InterviewStatus {
        String NOT_COMPLETED = "IS0002";
        String COMPLETED = "IS0001";
    }

    interface ValidityStatus {
        String VALID = "VS0004";
        String VALID_ENOUGH = "VS0003";
        String LESS_VALID = "VS0002";
        String NOT_VALID = "VS0001";
    }

    interface InterviewComponentType {
        String QUESTION = "CT0001";
        String STATEMENT = "CT0002";
    }

    interface QuestionType {
        String MULTIPLE_CHOICE = "QT0001";
        String ESSAY = "QT0002";
    }

    interface InterviewProgress {
        String NOT_STARTED = "IP0001";
        String STARTED = "IP0002";
        String FINISHED = "IP0003";
    }

    interface ImportAgentIndex {
        int COMPANY_ID_NAME = 0;
        int COMPANY_ID = 1;
        int EVENT_NAME = 2;
        int EVENT_ID = 3;
        int USER_NAME = 4;
        int PROVINCE = 5;
        int PROVINCE_ID = 6;
        int CITY = 7;
        int CITY_ID = 8;
        int DISTRICT = 9;
        int DISTRICT_ID = 10;
        int VILLAGE = 11;
        int VILLAGE_ID = 12;
        int RESPONDENT_AMOUNT = 13;
    }

    interface WebParameter {
        String IP = "WP001";
        String CONTEXT_PATH = "WP002";
        String UPLOAD_PATH = "WP003";
        String EVENT_PATH = "WP004";
        String COMPONENT_PATH = "WP005";
        String NODE_IMAGE_PATH = "WP006";
    }

    int DEFAULT_AGENT_EXPIRED_DAY = 365;
}
