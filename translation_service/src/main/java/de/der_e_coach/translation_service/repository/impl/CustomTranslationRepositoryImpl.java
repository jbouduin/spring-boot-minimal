package de.der_e_coach.translation_service.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import de.der_e_coach.shared_lib.dto.translation.TranslationMergeDto;
import de.der_e_coach.translation_service.entity.Translation;
import de.der_e_coach.translation_service.entity.Translation_;
import de.der_e_coach.translation_service.repository.CustomTranslationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CustomTranslationRepositoryImpl implements CustomTranslationRepository {
  //#region Private fields ----------------------------------------------------
  private final EntityManager entityManager;
  //#endregion

  //#region Constructor -------------------------------------------------------
  @Autowired
  public CustomTranslationRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
  //#endregion

  //#region CustomTranslationRepository Members -------------------------------
  @Override
  public List<Translation> findTranslationsForMerge(List<TranslationMergeDto> translationMergeDtos) {
    if (translationMergeDtos.isEmpty()) {
      throw new RuntimeException("List of TranslateMergeDto's may not be empty!");
    }

    /*
     * Query that should be executed
     * 
     * SELECT * FROM translation_service.translation t
     * WHERE (t.entity_name = 'item' AND t.entity_field = 'description' AND t.entity_id = 15) OR
     * (t.entity_name = 'item' AND t.entity_field = 'name' AND t.entity_id = 15) OR
     * (t.entity_name = 'item' AND t.entity_field = 'description' AND t.entity_id = 17) OR
     * (t.entity_name = 'item' AND t.entity_field = 'name' AND t.entity_id = 17)
     */
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Translation> query = cb.createQuery(Translation.class);
    Root<Translation> root = query.from(Translation.class);

    List<Predicate> orPredicates = new ArrayList<>();

    for (TranslationMergeDto dto : translationMergeDtos) {
      Predicate andPredicate = cb
        .and(
          cb.equal(root.get(Translation_.entityName), dto.getKey().entityName()),
          cb.equal(root.get(Translation_.entityField), dto.getKey().entityField()),
          cb.equal(root.get(Translation_.entityId), dto.getEntityId())
        );
      orPredicates.add(andPredicate);
    }

    query.select(root).where(cb.or(orPredicates.toArray(new Predicate[0])));

    return entityManager.createQuery(query).getResultList();
  }  
  //#endregion
}
