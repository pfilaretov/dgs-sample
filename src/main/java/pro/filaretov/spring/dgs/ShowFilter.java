package pro.filaretov.spring.dgs;

import lombok.Data;
import pro.filaretov.spring.dgs.model.Genre;

/**
 * Criteria to filter shows
 */
@Data
public class ShowFilter {

    private String actorRole;
    private Genre genre;
}
