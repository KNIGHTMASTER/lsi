package com.wissensalt.api.logger.exception;

/**
 * Created on 6/3/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public class APILoggerServiceException extends Exception {
    /**
     *
     *
     */
    private static final long serialVersionUID = -6093678957188917855L;

    public APILoggerServiceException(String p_Message) {
        super(p_Message);
    }

    public APILoggerServiceException(String p_Message, Throwable p_Throwable) {
        super(p_Message, p_Throwable);
    }
}
