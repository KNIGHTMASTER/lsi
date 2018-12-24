package com.tripoin.lsi.thirdparty.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created on 9/15/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public class HttpUtil {

    public static String getHeaderAuthorization(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("Authorization");
    }
}
