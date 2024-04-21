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
                .uri(serviceUrl.getLocalURL() + "/health")
                .retrieve()
                .bodyToMono(ServiceResponse.class)
                .onErrorResume(e -> Mono.just(
                        ServiceResponse.builder()
                                .name(serviceUrl.getName())
                                .URL(serviceUrl.getURL())
                                .uptime("/actuator/uptime")
                                .health("/actuator/health")
                                .online(false)
                                .build()
                ))
                .map(response -> ServiceResponse.builder()
                        .name(serviceUrl.getName())
                        .URL(serviceUrl.getURL())
                        .uptime("/actuator/uptime")
                        .health("/actuator/health")
                        .online(true)
                        .build() );
    }
}
