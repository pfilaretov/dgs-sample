package pro.filaretov.spring.dgs;

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
