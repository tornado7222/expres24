package common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class BaseRepository<Entity extends BaseEntity<ID>, ID> implements Repository<Entity, ID>{
    private final List<Entity> entities = new ArrayList<>();
    @Override
    public Optional<Entity> findById(ID id) {
        return entities.stream()
                .filter(entity -> entity.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Entity> findAll() {
        return entities;
    }

    @Override
    public void add(Entity entity) {
        entities.add(entity);
    }

    @Override
    public void delete(ID id) {
        entities.removeIf(entity -> entity.getId().equals(id));
    }
}
