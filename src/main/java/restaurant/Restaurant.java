package restaurant;

import common.BaseEntity;
import lombok.*;
import user.User;

import java.time.LocalDateTime;
import java.util.UUID;
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Restaurant extends BaseEntity<UUID> {
    private String name;
    private String location;

    public Restaurant(UUID id, LocalDateTime created, LocalDateTime modified, User creatBy, User modifiedBy, String name, String location) {
        super(id, created, modified, creatBy, modifiedBy);
        this.name = name;
        this.location = location;
    }
}
