package be.ictdynamic.mobiscan.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created by Wim Van den Brande on 24/09/2018.
 */
@Embeddable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MobiscanParameter {
    @Getter
    @Setter
    @NotNull
    private String parameterKey;

    @Getter
    @Setter
    @NotNull
    private String parameterValue;

}
