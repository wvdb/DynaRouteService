package be.ictdynamic.dynarouteservice.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by wvdbrand on 14/03/2017.
 */
@Slf4j
public class Dummy {
    @Getter
    @Setter
    private String Voornaam;

    @Getter
    @Setter
    private String Achternaam;

    public Dummy(String voornaam, String achternaam) {
        log.info("this is a test");
        Voornaam = voornaam;
        Achternaam = achternaam;
    }
}
