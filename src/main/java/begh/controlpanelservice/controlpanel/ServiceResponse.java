package begh.controlpanelservice.controlpanel;

import lombok.*;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceResponse {
    private Integer id;
    private String name;
    private String URL;
    private String uptime;
    private String health;
    private int statusCode;
    private String status;
    private boolean online;
}
