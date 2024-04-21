package begh.controlpanelservice.controlpanel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceUrlRepository extends JpaRepository<ServiceUrl, Integer> {
}
