package pro.filaretov.spring.dgs.fetcher;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import pro.filaretov.spring.dgs.DgsConstants.SHOW;
import pro.filaretov.spring.dgs.types.Actor;
import pro.filaretov.spring.dgs.types.Show;

/**
 * Data fetcher for Actors
 */
@Slf4j
@DgsComponent
public class ActorDataFetcher {

    @DgsData(parentType = SHOW.TYPE_NAME, field = SHOW.Actors)
    public CompletableFuture<List<Actor>> actors(DgsDataFetchingEnvironment environment) {
        DataLoader<List<String>, List<Actor>> dataLoader = environment.getDataLoader(ActorsDataLoader.class);

        // TODO - show.getActors() is null because it's a lazy loading collection, so how to get a list of actor IDs here?
//        Show show = environment.getSource();
//        return dataLoader.load(show.getActors().stream().map(Actor::getId).collect(Collectors.toList()));
        return dataLoader.load(List.of("1", "2", "3"));
    }

}
