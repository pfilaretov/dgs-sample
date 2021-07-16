package pro.filaretov.spring.dgs.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import pro.filaretov.spring.dgs.entity.ActorEntity;

/**
 * Actor repository
 */
public interface ActorRepository extends CrudRepository<ActorEntity, Long> {
    List<ActorEntity> findByIdIn(List<Long> ids);
}
