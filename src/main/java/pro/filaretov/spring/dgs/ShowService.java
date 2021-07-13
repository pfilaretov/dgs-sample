package pro.filaretov.spring.dgs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import pro.filaretov.spring.dgs.types.Actor;
import pro.filaretov.spring.dgs.types.Rating;
import pro.filaretov.spring.dgs.types.Show;
import pro.filaretov.spring.dgs.types.Show.Builder;
import pro.filaretov.spring.dgs.types.ShowFilter;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShowService {

    private final ActorService actorService;

    private List<Show> shows;

    @PostConstruct
    public void init() {
        Actor frodo = actorService.getFrodo();
        Actor aragorn = actorService.getAragorn();
        Actor sauron = actorService.getSauron();

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

    public List<Show> find(String title, ShowFilter filter) {
        return shows.stream()
            .filter(show -> title == null || show.getTitle().contains(title))
            .filter(show -> filter == null || filter.getActorRole() == null ||
                show.getActors().stream()
                    .anyMatch(actor -> actor.getRole().equalsIgnoreCase(filter.getActorRole())))
            .collect(Collectors.toList());
    }

    public Rating addRating(Integer score, String title) {
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
