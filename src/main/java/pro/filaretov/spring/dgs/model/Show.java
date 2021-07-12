package pro.filaretov.spring.dgs.model;

import java.util.List;
import lombok.Data;

/**
 *
 */
@Data
public class Show {
    private final String title;
    private final Integer releaseYear;
    private final List<Actor> actors;
}
