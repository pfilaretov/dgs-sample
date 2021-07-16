package pro.filaretov.spring.dgs.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import pro.filaretov.spring.dgs.entity.ShowEntity;
import pro.filaretov.spring.dgs.types.Show;

/**
 * Mapper for Show entity
 */
@Mapper(componentModel = "spring")
public interface ShowMapper {

    /**
     * Ignore actors field because we have lazy loading and do not want Spring Data JPA to load it here
     * @param showEntity
     * @return
     */
    @Named("mapWithoutActors")
    @Mapping(target = "actors", ignore = true)
    Show mapWithoutActors(ShowEntity showEntity);

    /**
     * Explicitly specify the mapping function for each collection element, so that some fields can be ignored
     * @param showEntities
     * @return
     */
    @IterableMapping(qualifiedByName = "mapWithoutActors")
    List<Show> map(List<ShowEntity> showEntities);
}
