package pro.filaretov.spring.dgs.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import pro.filaretov.spring.dgs.entity.ShowEntity;

/**
 * Show repository
 */
public interface ShowRepository extends CrudRepository<ShowEntity, Long> {
    List<ShowEntity> findByTitleContains(String title);
}
