package de.der_e_coach.translation_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.der_e_coach.shared_lib.dto.translation.LanguageCode;
import de.der_e_coach.translation_service.entity.Translation;
import de.der_e_coach.translation_service.entity.TranslationId;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, TranslationId>, CustomTranslationRepository {
  /**
   * Filtered query method to retrieve {@link Translation}.
   * 
   * @param  entityNames  optional list of entity names
   * @param  entityFields optional list of entity fields
   * @param  entityIds    optional list of entity id's
   * @param  languages    optional list of languages
   * @return              list of found {@link Translation}
   */
  @Query(
    "SELECT t FROM Translation t WHERE " +
      "(:entityNames IS NULL OR t.entityName IN :entityNames) AND " +
      "(:entityFields IS NULL OR t.entityField IN :entityFields) AND " +
      "(:entityIds IS NULL OR t.entityId IN :entityIds) AND " +
      "(:languages IS NULL OR t.language IN :languages)"
  )
  List<Translation> findTranslations(
    @Param("entityNames") List<String> entityNames,
    @Param("entityFields") List<String> entityFields,
    @Param("entityIds") List<Long> entityIds,
    @Param("languages") List<LanguageCode> languages
  );
}
