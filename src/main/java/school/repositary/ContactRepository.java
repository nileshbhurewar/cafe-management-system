package school.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import school.entity.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
