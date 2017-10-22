package be.ictdynamic.dynarouteservice.domain;

/**
 * Created by admin on 22/10/2017.
 */
public class SystemParameterResponse {
    private String responseError;

    public SystemParameterResponse(String responseError) {
        this.responseError = responseError;
    }

    public String getResponseError() {
        return responseError;
    }

    public void setResponseError(String responseError) {
        this.responseError = responseError;
    }
}
