package be.ictdynamic.dynarouteservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by wvdbrand on 30/03/2017.
 */
@XmlRootElement(name = "DynaRouteServiceResponse")
public class DynaRouteServiceResponse implements Serializable {
    @Getter
    @Setter
    private String dummy1;
}
