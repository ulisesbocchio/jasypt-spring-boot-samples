package demo;

import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Data
@RequiredArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
}
