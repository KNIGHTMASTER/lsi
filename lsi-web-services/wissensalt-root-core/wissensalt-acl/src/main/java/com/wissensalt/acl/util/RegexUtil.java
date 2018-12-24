package com.wissensalt.acl.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created on 9/11/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public class RegexUtil {

    public static boolean checkIfStringContainsNumber(String p_StringToCheck) {
        Pattern numberPat = Pattern.compile("\\d+");
        Matcher matcher1 = numberPat.matcher(p_StringToCheck);
        return matcher1.find();
    }
}
