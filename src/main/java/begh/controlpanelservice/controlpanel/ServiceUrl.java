package begh.controlpanelservice.controlpanel;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "service_url")
public class ServiceUrl {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "URL")
    private String URL;
    @Column(name = "local_url")
    private String localURL;
}
