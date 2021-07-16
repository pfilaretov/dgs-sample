package pro.filaretov.spring.dgs.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pro.filaretov.spring.dgs.entity.ShowEntity;
import pro.filaretov.spring.dgs.mapper.ShowMapper;
import pro.filaretov.spring.dgs.repository.ShowRepository;
import pro.filaretov.spring.dgs.types.Show;
import pro.filaretov.spring.dgs.types.ShowFilter;

/**
 *
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ShowService {

    private final ActorService actorService;
    private final ShowRepository showRepository;
    private final ShowMapper showMapper;

    public List<Show> find(String title, ShowFilter filter) {
        // TODO - use filter

        List<ShowEntity> entities = showRepository.findByTitleContains(title);
        return showMapper.map(entities);
    }

    // TODO - fix this
    /*public Rating addRating(Integer score, String title) {
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
    }*/

}
