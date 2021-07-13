package pro.filaretov.spring.dgs;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import pro.filaretov.spring.dgs.types.Actor;

/**
 *
 */
@Service
public class ActorService {

    private final Actor frodo = new Actor.Builder()
        .id(UUID.randomUUID().toString())
        .firstName("Elijah")
        .lastName("Wood")
        .role("Frodo")
        .build();

    private final Actor aragorn = new Actor.Builder()
        .id(UUID.randomUUID().toString())
        .firstName("Viggo")
        .lastName("Mortensen")
        .role("Aragorn")
        .build();

    private final Actor sauron = new Actor.Builder()
        .id(UUID.randomUUID().toString())
        .firstName("Sala")
        .lastName("Baker")
        .role("Sauron")
        .build();

    private final List<Actor> actors = List.of(frodo, aragorn, sauron);

    /**
     * Load actors list for Shows
     *
     * @param actorIdsList list of elements, each element corresponds to a Show and contains list with Actor ids
     * @return list of elements, each element corresponds to a Show
     */
    public List<List<Actor>> loadActors(List<List<String>> actorIdsList) {
        return actorIdsList.stream()
            .map(ids -> actors.stream()
                .filter(actor -> ids.contains(actor.getId()))
                .collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

    public Actor getFrodo() {
        return frodo;
    }

    public Actor getAragorn() {
        return aragorn;
    }

    public Actor getSauron() {
        return sauron;
    }
}
