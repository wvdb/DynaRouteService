package be.ictdynamic.dynarouteservice.domain;

/**
 * Created by admin on 22/10/2017.
 */
public class SystemParameterRequest {
    private String parameterKey;
    private String parameterValue;

    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }
}
