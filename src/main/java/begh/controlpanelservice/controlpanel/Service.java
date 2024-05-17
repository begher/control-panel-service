package begh.controlpanelservice.controlpanel;

import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class Service {
    private final ServiceUrlRepository repo;
    private final WebClient webClient;
    public Mono<List<ServiceResponse>> getServiceStatus() {
        List<ServiceUrl> serviceList = repo.findAll();
        return Flux.fromIterable(serviceList)
                .flatMap(this::map)
                .collectList();
    }
    private Mono<ServiceResponse> map(ServiceUrl serviceUrl) {
        return webClient.get()
                .uri(serviceUrl.getURL() + "/health")
                .exchangeToMono(response -> {
                    String statusText;
                    if (response.statusCode().is2xxSuccessful()) {
                        statusText = "Good";
                    } else if (response.statusCode().value() == 404) {
                        statusText = "Not found";
                    } else {
                        statusText = "Warning";
                    }
                    return response.bodyToMono(ServiceResponse.class)
                            .map(body -> ServiceResponse.builder()
                                    .id(serviceUrl.getId())
                                    .name(serviceUrl.getName())
                                    .URL(serviceUrl.getURL())
                                    .uptime("/actuator/uptime")
                                    .health("/actuator/health")
                                    .statusCode(response.statusCode().value())
                                    .status(statusText)
                                    .online(true)
                                    .build());
                })
                .onErrorResume(e -> Mono.just(
                        ServiceResponse.builder()
                                .id(serviceUrl.getId())
                                .name(serviceUrl.getName())
                                .URL(serviceUrl.getURL())
                                .uptime("/actuator/uptime")
                                .health("/actuator/health")
                                .statusCode(0)
                                .status("Error")
                                .online(false)
                                .build()));
    }
}
