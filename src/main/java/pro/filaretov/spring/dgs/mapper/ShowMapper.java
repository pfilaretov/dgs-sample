package pro.filaretov.spring.dgs.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import pro.filaretov.spring.dgs.entity.ShowEntity;
import pro.filaretov.spring.dgs.types.Show;

/**
 * Mapper for Show entity
 */
@Mapper(componentModel = "spring")
public interface ShowMapper {

    List<Show> map(List<ShowEntity> showEntity);
}
