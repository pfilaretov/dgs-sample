package pro.filaretov.spring.dgs.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pro.filaretov.spring.dgs.entity.ActorEntity;
import pro.filaretov.spring.dgs.mapper.ActorMapper;
import pro.filaretov.spring.dgs.repository.ActorRepository;
import pro.filaretov.spring.dgs.types.Actor;

/**
 *
 */
@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    /**
     * Load actors list for Shows
     *
     * @param actorIdsList list of elements, each element corresponds to a Show and contains list with Actor ids
     * @return list of elements, each element corresponds to a Show
     */
    public List<List<Actor>> loadActors(List<List<String>> actorIdsList) {
        List<Long> distinctIds = actorIdsList.stream()
            .flatMap(Collection::stream)
            .distinct()
            .map(Long::parseLong)
            .collect(Collectors.toList());

        List<ActorEntity> actors = actorRepository.findByIdIn(distinctIds);

        return actorIdsList.stream()
            .map(ids -> actors.stream()
                .filter(actor -> ids.contains(actor.getId().toString()))
                .map(actorMapper::map)
                .collect(Collectors.toList()))
            .collect(Collectors.toList());
    }

}
