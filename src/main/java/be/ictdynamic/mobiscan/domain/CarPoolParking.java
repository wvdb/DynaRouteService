package be.ictdynamic.mobiscan.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Table(uniqueConstraints={
	    @UniqueConstraint(columnNames = {"title"})
	})
@Entity
public class CarPoolParking extends BaseEntity {

    @NotEmpty
    @Getter
    @Setter
    private String title;

    @NotEmpty
    @Getter
    @Setter
	private String type;

    @NotEmpty
    @Getter
    @Setter
	private String commune;
	
	@NotNull
    @Getter
    @Setter
	private float latitude;

    @NotNull
    @Getter
    @Setter
    private float longitude;

    @Override
    public String toString() {
        return "CarPoolParking{" +
                "title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", commune='" + commune + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
