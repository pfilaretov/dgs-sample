package pro.filaretov.spring.dgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data fetcher for Shows
 */
@DgsComponent
public class ShowsDataFetcher {

    private final List<Show> shows = List.of(
        new Show("The Lord of The Rings: Fellowship of the Ring", 2001),
        new Show("The Lord of The Rings: The Two Towers", 2002),
        new Show("The Lord of The Rings: The Return of the King", 2003),
        new Show("I, robot", 2004)
    );

    @DgsQuery
    public List<Show> shows(@InputArgument String titleFilter) {
        if (titleFilter == null) {
            return shows;
        }

        return shows.stream()
            .filter(show -> show.getTitle().contains(titleFilter))
            .collect(Collectors.toList());
    }

}
