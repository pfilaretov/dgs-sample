package pro.filaretov.spring.dgs;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pro.filaretov.spring.dgs.DgsConstants.MUTATION;
import pro.filaretov.spring.dgs.DgsConstants.QUERY;
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
public class ShowDataFetcher {

    private final ShowService showService;

    // field specified explicitly, so that we are not relying on method name as a field name (default behaviour)
    @DgsQuery(field = QUERY.Shows)
    public List<Show> shows(@InputArgument String title, @InputArgument ShowFilter filter) {
        return showService.find(title, filter);
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
