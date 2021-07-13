package pro.filaretov.spring.dgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import pro.filaretov.spring.dgs.DgsConstants.MUTATION;
import pro.filaretov.spring.dgs.DgsConstants.QUERY;
import pro.filaretov.spring.dgs.DgsConstants.SHOW;
import pro.filaretov.spring.dgs.types.Actor;
import pro.filaretov.spring.dgs.types.Rating;
import pro.filaretov.spring.dgs.types.RatingInput;
import pro.filaretov.spring.dgs.types.Show;
import pro.filaretov.spring.dgs.types.ShowFilter;

/**
 * Data fetcher for Shows
 */
@Slf4j
@DgsComponent
@RequiredArgsConstructor
public class ShowsDataFetcher {

    private final ShowService showService;

    // field specified explicitly, so that we are not relying on method name as a field name (default behaviour)
    @DgsQuery(field = QUERY.Shows)
    public List<Show> shows(@InputArgument String title, @InputArgument ShowFilter filter) {
        return showService.find(title, filter);
    }

    @DgsData(parentType = SHOW.TYPE_NAME, field = SHOW.Actors)
    public CompletableFuture<List<Actor>> actors(DgsDataFetchingEnvironment environment) {
        DataLoader<List<String>, List<Actor>> dataLoader = environment.getDataLoader(ActorsDataLoader.class);

        Show show = environment.getSource();
        return dataLoader.load(show.getActors().stream().map(Actor::getId).collect(Collectors.toList()));
    }

    @DgsData(parentType = MUTATION.TYPE_NAME, field = MUTATION.AddRating)
    public Rating addRating(@InputArgument("input") RatingInput ratingInput) {
        Integer score = ratingInput.getScore();
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("score should be 1-5");
        }

        String title = ratingInput.getTitle();
        return showService.addRating(score, title);
    }

}
