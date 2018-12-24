package com.tripoin.lsi.core.util;

import static com.tripoin.lsi.core.LsiCoreConstant.ValidityStatus.*;

/**
 * Created on 9/16/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public class PointCalculator {

    public static Double getPoint(String p_ValidityStatus) {
        switch (p_ValidityStatus) {
            case VALID : return Double.valueOf(100);
            case VALID_ENOUGH : return Double.valueOf(80);
            case LESS_VALID : return Double.valueOf(60);
            case NOT_VALID : return Double.valueOf(20);
            default: return null;
        }
    }
}
