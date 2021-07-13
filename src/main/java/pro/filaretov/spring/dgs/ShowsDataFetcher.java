package pro.filaretov.spring.dgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import pro.filaretov.spring.dgs.DgsConstants.MUTATION;
import pro.filaretov.spring.dgs.DgsConstants.QUERY;
import pro.filaretov.spring.dgs.DgsConstants.SHOW;
import pro.filaretov.spring.dgs.types.Actor;
import pro.filaretov.spring.dgs.types.Rating;
import pro.filaretov.spring.dgs.types.RatingInput;
import pro.filaretov.spring.dgs.types.Show;
import pro.filaretov.spring.dgs.types.Show.Builder;
import pro.filaretov.spring.dgs.types.ShowFilter;

/**
 * Data fetcher for Shows
 */
@Slf4j
@DgsComponent
public class ShowsDataFetcher {

    private final List<Show> shows;

    public ShowsDataFetcher() {
        Actor frodo = new Actor("Elijah", "Wood", "Frodo");
        Actor aragorn = new Actor("Viggo", "Mortensen", "Aragorn");
        Actor sauron = new Actor("Sala", "Baker", "Sauron");

        Show theLordOfTheRings1 = new Show.Builder()
            .title("The Lord of The Rings: Fellowship of the Ring")
            .releaseYear(2001)
            .actors(List.of(frodo, aragorn, sauron))
            .scores(new ArrayList<>())
            .rating(new Rating(0.0, 0))
            .build();
        Show theLordOfTheRings2 = new Show.Builder()
            .title("The Lord of The Rings: The Two Towers")
            .releaseYear(2002)
            .actors(List.of(frodo, aragorn))
            .scores(new ArrayList<>())
            .rating(new Rating(0.0, 0))
            .build();
        Show theLordOfTheRings3 = new Show.Builder()
            .title("The Lord of The Rings: The Return of the King")
            .releaseYear(2003)
            .actors(List.of(frodo, aragorn, sauron))
            .scores(new ArrayList<>())
            .rating(new Rating(0.0, 0))
            .build();

        shows = new CopyOnWriteArrayList<>();
        shows.add(theLordOfTheRings1);
        shows.add(theLordOfTheRings2);
        shows.add(theLordOfTheRings3);
    }

    // field specified explicitly, so that we are not relying on method name as a field name (default behaviour)
    @DgsQuery(field = QUERY.Shows)
    public List<Show> shows(@InputArgument String title, @InputArgument ShowFilter filter) {
        return shows.stream()
            .filter(show -> title == null || show.getTitle().contains(title))
            .filter(show -> filter == null || filter.getActorRole() == null ||
                show.getActors().stream()
                    .anyMatch(actor -> actor.getRole().equalsIgnoreCase(filter.getActorRole())))
            .collect(Collectors.toList());
    }

    @DgsData(parentType = SHOW.TYPE_NAME, field = SHOW.Actors)
    public List<Actor> actors(DgsDataFetchingEnvironment environment) {
        Show show = environment.getSource();
        return show.getActors();
    }

    @DgsData(parentType = MUTATION.TYPE_NAME, field = MUTATION.AddRating)
    public Rating addRating(@InputArgument("input") RatingInput ratingInput) {
        Integer score = ratingInput.getScore();
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("score should be 1-5");
        }

        String title = ratingInput.getTitle();

        log.info("Adding rating '{}' to the show '{}'", score, title);

        for (int i = 0; i < shows.size(); i++) {
            Show show = shows.get(i);
            if (show.getTitle().equalsIgnoreCase(title)) {
                return updateShows(score, i, show);
            }
        }

        throw new RuntimeException("Show '" + title + "' not found");
    }

    @NotNull
    private Rating updateShows(Integer score, int i, Show show) {
        List<Integer> newScores = new ArrayList<>(show.getScores());
        newScores.add(score);

        Rating currentRating = show.getRating();
        int votesNumber = currentRating.getVotesNumber() + 1;
        double averageScore = (double) newScores.stream().mapToInt(value -> value).sum() / votesNumber;

        Rating newRating = new Rating(averageScore, votesNumber);

        Show newShow = new Builder()
            .title(show.getTitle())
            .releaseYear(show.getReleaseYear())
            .actors(show.getActors())
            .scores(newScores)
            .rating(newRating)
            .build();

        shows.set(i, newShow);
        return newRating;
    }


}
