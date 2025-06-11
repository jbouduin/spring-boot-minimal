package de.der_e_coach.translation_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.der_e_coach.translation_service.entity.Translation;
import de.der_e_coach.translation_service.entity.TranslationId;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, TranslationId> {

}
