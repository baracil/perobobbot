package perobobbot.data.service.jpa.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import lombok.NonNull;
import perobobbot.data.service.jpa.domain.Customer;

import java.util.List;
import java.util.stream.Stream;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long> {

    @NonNull List<Customer> findByLastName(@NonNull String lastName);
}
