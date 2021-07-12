package pro.filaretov.spring.dgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Data fetcher for Shows
 */
@DgsComponent
public class ShowsDataFetcher {

    private final List<Show> shows;

    public ShowsDataFetcher() {
        Actor frodo = new Actor("Elijah", "Wood", "Frodo");
        Actor aragorn = new Actor("Viggo", "Mortensen", "Aragorn");
        Actor sauron = new Actor("Sala", "Baker", "Sauron");

        Show theLordOfTheRings1 = new Show("The Lord of The Rings: Fellowship of the Ring", 2001,
            List.of(frodo, aragorn, sauron));
        Show theLordOfTheRings2 = new Show("The Lord of The Rings: The Two Towers", 2002,
            List.of(frodo, aragorn));
        Show theLordOfTheRings3 = new Show("The Lord of The Rings: The Return of the King", 2003,
            List.of(frodo, aragorn, sauron));

        shows = List.of(
            theLordOfTheRings1,
            theLordOfTheRings2,
            theLordOfTheRings3
        );
    }

    // field specified explicitly, so that we are not relying on method name as a field name (default behaviour)
    @DgsQuery(field = "shows")
    public List<Show> shows(@InputArgument String title, @InputArgument String role) {
        return shows.stream()
            .filter(show -> title == null || show.getTitle().contains(title))
            .filter(show -> role == null || show.getActors().stream()
                .anyMatch(actor -> actor.getRole().equalsIgnoreCase(role)))
            .collect(Collectors.toList());
    }

    @DgsData(parentType = "Show", field = "actors")
    public List<Actor> actors(DgsDataFetchingEnvironment environment) {
        Show show = environment.getSource();
        return show.getActors();
    }


}
