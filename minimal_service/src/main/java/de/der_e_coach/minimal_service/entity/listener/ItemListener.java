package de.der_e_coach.minimal_service.entity.listener;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import de.der_e_coach.minimal_service.entity.Item;
import de.der_e_coach.minimal_service.service.feign.translation_service.TranslationServiceClient;
import de.der_e_coach.shared_lib.dto.translation.TranslationMergeDto;
import jakarta.persistence.PreRemove;

/**
 * EntityListener for the Item entity.
 * Not implemented as a bean, as this causes a circular reference
 */
public class ItemListener implements ApplicationContextAware {
  // #region Private fields ---------------------------------------------------
  // Application context has to be static, as the lifecycle hooks are called in a static context
  private static ApplicationContext applicationContext;
  // #endregion

  // #region ApplicationContextAware members ----------------------------------
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    ItemListener.applicationContext = applicationContext;
  }
  // #endregion

  // #region Entity Lifecycle hooks -------------------------------------------
  @PreRemove
  public void preRemove(Item item) {
    
    List<TranslationMergeDto> mergeDtos = List
      .of(
        new TranslationMergeDto().setKey(Item.DESCRIPTION_TRANSLATION_KEY).setEntityId(item.getId()),
        new TranslationMergeDto().setKey(Item.NAME_TRANSLATION_KEY).setEntityId(item.getId())
      );
    TranslationServiceClient translationServiceClient = applicationContext.getBean(TranslationServiceClient.class);
    // for the time being we silently ignore if translations are not deleted
    translationServiceClient.mergeTranslations(mergeDtos);
  }
  // #endregion
}
