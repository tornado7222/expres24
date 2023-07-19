package common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import user.User;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseEntity<Id> {
    private Id id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private User creatBy;
    private User modifiedBy;
}
