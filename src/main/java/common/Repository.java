package common;

import java.util.List;
import java.util.Optional;

public interface Repository<Entity extends BaseEntity<ID>, ID>{
    Optional<Entity> findById(ID id);
    List<Entity> findAll();
    void add(Entity entity);
    void delete(ID id);
}
