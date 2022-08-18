package perobobbot.server.service.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import perobobbot.server.service.jpa.domain.Customer;

import java.util.stream.Stream;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @NonNull Stream<Customer> findByLastName(@NonNull String lastName);
}
