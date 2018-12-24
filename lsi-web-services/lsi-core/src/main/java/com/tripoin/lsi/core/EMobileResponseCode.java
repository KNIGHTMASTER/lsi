package com.tripoin.lsi.core;

/**
 * Created on 9/20/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public enum EMobileResponseCode {
    /*START 2XX SUCCESS*/
    RC_SUCCESS(200, "V-200", "Success"),
    RC_CREATED(201, "V-201", "Created"),
    RC_ACCEPTED(202, "V-202", "Accepted"),
    RC_GET_PROFILE_SUCCESS(200, "X-203", "Get Profile Success"),
    RC_UPDATE_PROFILE_SUCCESS(200, "X-204", "Update Profile Success"),
    RC_UPDATE_PASSWORD_SUCCESS(200, "X-205", "Update Password Success"),
    RC_REQUEST_FORGOT_PASSWORD_SUCCESS(200, "X-206", "Request Forgot Password Success"),
    RC_RESET_PASSWORD_SUCCESS(200, "X-207", "Reset Password Success"),
    RC_LOGIN_SUCCESS(200, "208", "Login Success"),
    RC_LOGOUT_SUCCESS(200, "209", "Logout Success"),
    RC_ACCESS_GRANTED(200, "210", "Access Granted"),
	/*END 2XX SUCCESS*/

    /*START 4XX CLIENT ERROR*/
    RC_BAD_REQUEST(400, "X-400", "Bad Request"),
    RC_UNAUTHORIZED(401, "X-401", "Unauthorized"),
    RC_PAYMENT_REQUIRED(402, "X-402", "Payment Required"),
    RC_FORBIDDEN(403, "X-403", "Forbidden"),
    RC_NOT_FOUND(404, "X-404", "Not Found"),
    RC_METHOD_NOT_ALLOWED(405, "X-405", "Method Not Allowed"),
    RC_NOT_ACCEPTABLE(406, "X-406", "Not Acceptable"),
    RC_PROXY_AUTHENTICATION_REQUIRED(407, "X-407", "Proxy Authentication Required"),
    RC_REQUEST_TIMEOUT(408, "X-408", "Request Timeout"),
    RC_CONFLICT(409, "X-409", "Conflict"),
    RC_BAD_CREDENTIALS(401, "X-Q01", "Web Service Bad Credentials"),
    RC_INVALID_TOKEN(401, "X-Q02", "Web Service Invalid Access Token"),
    RC_ACCESS_DENIED(403, "X-Q04", "Web Service Access is denied"),
    RC_BAD_KEY(400, "X-Q05", "Invalid Key"),
    RC_BAD_TIMESTAMP(400, "X-Q06", "Invalid X-Timestamp (ISO8601)"),
    RC_BAD_SIGNATURE(400, "X-Q07", "Invalid X-Signature"),
    RC_USERNAME_EXISTS(409, "X-Q08", "Username already exists, please try others!"),
    RC_USERNAME_NOT_EXISTS(404, "X-Q09", "Username not exists"),
    RC_FAIL_PASSWORD(400, "X-Q10", "Invalid Password"),
    RC_EMAIL_EXISTS(409, "X-Q11", "Email already exists, please try others!"),
    RC_EMAIL_NOT_EXISTS(404, "X-Q12", "Email is not registered"),
    RC_PHONE_EXISTS(409, "X-Q13", "Phone No. already exists, please try others!"),
    RC_PHONE_NOT_EXISTS(404, "X-Q14", "Phone No. not exists!"),
    RC_GENDER_NOT_DEFINE(404, "X-Q15", "Gender Not Define"),
    RC_ACCOUNT_EXPIRED(410, "X-Q16", "Account is expired"),
    RC_ACCOUNT_NOTACTIVE(410, "X-Q17", "Account is no longer active"),
    RC_URL_EXPIRED(410, "X-Q18", "Link has been expired"),
    RC_URL_NOTFOUND(404, "X-Q19", "Link is not found"),
    RC_TYPEFILE_NOTALLOW(405, "X-Q20", "Type File not Allowed"),
    RC_MAX_FILE_SIZE(413, "X-Q21", "Maximum file upload"),
    RC_DATA_USED(400, "X-Q22", "Some data already being used"),
    RC_ACCESS_NOT_VALID(400, "X-Q23", "Current Access not valid"),
    RC_LOCALE_NOT_NULL(400, "X-Q24", "Locale Code not null"),
    RC_LOCALE_NOT_FOUND(400, "X-Q25", "Locale not found"),
    RC_GROUP_NOT_FOUND(400, "X-Q26", "Group not found"),
    RC_MANDATORY_NOT_COMPLETED(400, "X-Q27", "Mandatory is not completed"),
    RC_EMAIL_NOT_VALID(410, "X-Q28", "Email is not valid! || Already Registered"),
    RC_USER_NOT_EXISTS(410, "X-Q29", "User not exists"),
    RC_TOKEN_EXPIRED(410, "X-Q30", "Token is Expired"),
    RC_PASSWORD_NOT_MATCH(410, "X-Q31", "Password Does Not Match"),
    RC_BAD_DATE_FORMAT(410, "X-Q32", "Bad Date Format, should be dd-MM-yyyy"),
    RC_OLD_PASSWORD_FALSE(410, "X-Q33", "Old Password is Not Correct"),
    RC_NEW_PASSWORD_NOT_EQUAL_RETYPE(410, "X-Q34", "New Password Did Not Equal Retype New Password"),
    RC_DATA_IS_EMPTY(410, "X-Q35", "Data is Empty"),
    RC_DATA_NOT_FOUND(410, "X-Q36", "Data Not Found"),
    RC_LOGOUT_FAILED(500, "X-037", "Logout Failed"),
    RC_LOGIN_FAILED(500, "X-037", "Login Failed"),
    RC_PASSWORD_UNMATCH(410, "X-038", "Password doesn't match policy"),

    RC_FAILURE(500, "X-VVV", "Web Service Failure"),
    RC_FAIL_UPLOAD(503, "X-V01", "File upload failure"),
    RC_REGISTRATION_MEMBER_FAILED(400, "X-V02", "Registration Member Failed"),

    RC_GET_PROFILE_FAILED(500, "X-V09", "Get Profile Failed"),
    RC_UPDATE_PROFILE_FAILED(500, "X-V10", "Update Profile Failed"),
    RC_UPDATE_PASSWORD_FAILED(500, "X-V11", "Update Password Failed"),
    RC_REQUEST_FORGOT_PASSWORD_FAILED(500, "X-V12", "Request Forgot Password Failed"),
    RC_RESET_PASSWORD_FAILED(500, "X-V13", "Reset Password Failed"),
    RC_DUPLICATE(500, "X-V14", "Data Interview Duplicate");
	/*END 4XX CLIENT ERROR*/


    private int httpResponse;
    private String responseCode;
    private String responseMsg;

    private EMobileResponseCode(int httpResponse, String responseCode, String responseMsg){
        this.httpResponse = httpResponse ;
        this.responseCode = responseCode;
        this.responseMsg = responseMsg;
    }

    public int getHttpResponse() {
        return httpResponse;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    @Override
    public String toString() {
        return responseMsg;
    }
}
