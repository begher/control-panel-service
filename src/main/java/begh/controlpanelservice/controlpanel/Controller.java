package begh.controlpanelservice.controlpanel;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/servicestatus")
public class Controller {
    private final Service service;

    @GetMapping()
    public Mono<ResponseEntity<List<ServiceResponse>>> getServiceStatus() {
        return service.getServiceStatus()
                .map(ResponseEntity::ok);
    }
}
