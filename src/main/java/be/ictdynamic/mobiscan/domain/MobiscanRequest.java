package be.ictdynamic.mobiscan.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * Class MobiscanRequest.
 *
 * @author Wim Van den Brande
 * @since 03/10/2015 - 20:35
 */
@Entity
public class MobiscanRequest extends BaseEntity {

    private static final long serialVersionUID = -5073135041934007777L;

    @Getter
    @Setter
    @NotNull
    private Long companyId;

    @Getter
    @Setter
    private String employeeId;

    @Getter
    @Setter
    @NotNull
    private String locationFrom;

    @Getter
    @Setter
    @NotNull
    private String locationTo;

    @Getter
    @Setter
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="mobiscan_request_type")
    @Column(name = "mobiscan_request_type_id")
    private Set<Long> mobiscanRequestTypeIds;

    @Getter
    @Setter
    @NotNull
    private Date departureDate;

    @Getter
    @Setter
    private Date processingDate;

    @Override
    public String toString() {
        return "MobiscanRequest{" +
                "companyId='" + companyId + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", locationFrom='" + locationFrom + '\'' +
                ", locationTo='" + locationTo + '\'' +
                ", mobiscanRequestTypeIds=" + mobiscanRequestTypeIds  +
                ", departureDate=" + departureDate +
                ", processingDate=" + processingDate +
                '}';
    }
}
