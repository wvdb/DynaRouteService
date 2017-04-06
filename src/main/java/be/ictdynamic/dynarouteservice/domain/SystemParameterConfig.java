package be.ictdynamic.dynarouteservice.domain;

import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created by wvdbrand on 5/04/2017.
 */
@Component
public class SystemParameterConfig {
    private HashMap<String, Object> systemParameters;

    public SystemParameterConfig() {
        systemParameters = new HashMap<>();
        systemParameters.put("DUMMY", "this parameter is being used by a unit test");
        systemParameters.put("key1", 500);
        systemParameters.put("key2", 500);
        systemParameters.put("key3", 1000);
    }

    public HashMap<String, Object> getSystemParameters() {
        return systemParameters;
    }

}
