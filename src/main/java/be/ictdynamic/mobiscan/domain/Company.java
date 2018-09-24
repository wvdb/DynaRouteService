package be.ictdynamic.mobiscan.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Class Company.
 *
 * @author Wim Van den Brande
 * @since 03/10/2015 - 20:35
 */
@Entity
public class Company extends BaseEntity {
    private static final long serialVersionUID = -5073135041934007777L;

    @Getter
    @Setter
    @NotNull
    private String companyName;

    @Getter
    @Setter
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name = "company_parameters")
    private List<MobiscanParameter> parameters;

}
