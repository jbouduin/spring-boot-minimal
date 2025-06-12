package de.der_e_coach.minimal_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.der_e_coach.minimal_service.entity.InvoiceLine;

@Repository
public interface InvoiceLineRepository extends JpaRepository<InvoiceLine, Long> {

}
