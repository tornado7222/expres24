package user;


import common.BaseEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseEntity<UUID> {
    private String name;
    private String surname;
    private double balance;
    private String phoneNumber;
    private String password;
    private Role role;
    private LocalDateTime employmentDate;
    private double salary;
    private UUID restaurantId;
    private String location;
    @Builder
    public User(UUID id, LocalDateTime created, LocalDateTime modified, User creatBy, User modifiedBy, String name, String surname, double balance, String phoneNumber, String password, Role role, LocalDateTime employmentDate, double salary, UUID restaurantId, String location) {
        super(id, created, modified, creatBy, modifiedBy);
        this.name = name;
        this.surname = surname;
        this.balance = balance;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
        this.employmentDate = employmentDate;
        this.salary = salary;
        this.restaurantId = restaurantId;
        this.location = location;
    }
}
