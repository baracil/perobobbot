package perobobbot.data.service.jpa.domain;

import lombok.*;
import perobobbot.data.io.view.CustomerView;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Customer {

    private @NonNull @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;
    private @NonNull String firstName;
    private @NonNull String lastName;

    public Customer(@NonNull String firstName, @NonNull String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public @NonNull CustomerView toView() {
        return new CustomerView(id,firstName,lastName);
    }
}
