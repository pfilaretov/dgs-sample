package pro.filaretov.spring.dgs.mapper;

import org.mapstruct.Mapper;
import pro.filaretov.spring.dgs.entity.ActorEntity;
import pro.filaretov.spring.dgs.types.Actor;

/**
 * Mapper for Actor entity
 */
@Mapper(componentModel = "spring")
public interface ActorMapper {

    Actor map(ActorEntity actorEntity);
}
