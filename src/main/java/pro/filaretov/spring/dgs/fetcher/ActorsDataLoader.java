package pro.filaretov.spring.dgs.fetcher;

import com.netflix.graphql.dgs.DgsDataLoader;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import lombok.RequiredArgsConstructor;
import org.dataloader.BatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import pro.filaretov.spring.dgs.service.ActorService;
import pro.filaretov.spring.dgs.types.Actor;
import pro.filaretov.spring.dgs.types.Show;

/**
 * Data loader for Actors list, avoids N+1 problem when querying a list of {@link Show}s.
 */
@DgsDataLoader(name = "actors")
@RequiredArgsConstructor
public class ActorsDataLoader implements BatchLoader<List<String>, List<Actor>> {

    // TODO - how Hibernate solves N+1 problem?

    @Autowired
    private final ActorService actorService;

    @Override
    public CompletionStage<List<List<Actor>>> load(List<List<String>> actorIdsList) {
        return CompletableFuture.supplyAsync(() -> actorService.loadActors(actorIdsList));
    }
}
