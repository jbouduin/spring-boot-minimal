# Multi language support and the translation service

## Key points
- the translation service can be used to store any kind of translation
- the database schema for the translation service has an enum type `language_code`. Currently possible values are `DE`, `EN`, `NL`, `FR`
- the shared library has an enum [`LanguageCode`](/shared_lib/src/main/java/de/der_e_coach/shared_lib/dto/translation/LanguageCode.java), which has the same enum values.
- Entity class [`Translation`](/translation_service/src/main/java/de/der_e_coach/translation_service/entity/Translation.java) has a composite key defined in class [`TranslationId`](/translation_service/src/main/java/de/der_e_coach/translation_service/entity/TranslationId.java)
  ```Java
   private String entityName;
   private String entityField;
   private Long entityId;
   private LanguageCode language;
  ```
- Examples of stored translations:

  | Entity Name | Entity Field | Entity Id | Language Code | Contains |
  | --- | --- | --- | --- | --- |
  | item | description | 1 | EN | The english description of the item with id 1 |
  | item | description | 1 | DE | The german description of the item with id 1 |
  | item | name | 1 | EN | The english item name of the item with id 1 |
  | item | name | 1 | DE | The german item name of the item with id 1 |
  | lng | de | -1 | DE | The german display value for language DE |
  | lng | de | -1 | EN | The english display value for language DE |

  - The entity id `-1`, to be used for translations which are not linked to an entity, is defined in the Entity class [`Translation`](/translation_service/src/main/java/de/der_e_coach/translation_service/entity/Translation.java) as
    ```Java
    public final static Long NONE_ENTITY_ID = -1L;
    ```
  - The translations for the supported languages are created by flyway migration in the translation service [V01_00_003__Language_Code_Translations.java](/translation_service/src/main/java/de/der_e_coach/translation_service/db/migration/V01_00_003__Language_Code_Translations.java)

To work with translations that are not linked to an entity use the constructors which do not take entityId as input parameter.

# Best practice (in fact: the only correct way to use the translation service)
## Use [`TranslationKeyDto`](/shared_lib/src/main/java/de/der_e_coach/shared_lib/dto/translation/TranslationKeyDto.java) record.
By defining these records in the entity class, consistent use of the same entity name and key are guaranteed.

e.g. in the Item class
```JAVA
public final static TranslationKeyDto DESCRIPTION_TRANSLATION_KEY = new TranslationKeyDto("item", "description");
public final static TranslationKeyDto NAME_TRANSLATION_KEY = new TranslationKeyDto("item", "name");
```

## Use Entity Listeners
Because there is no Foreign Key relation between entities and their translations, using entity listeners are the best way to delete translations if an entity is deleted.
e.g. [ItemListener.java](/minimal_service/src/main/java/de/der_e_coach/minimal_service/entity/listener/ItemListener.java).

Orphan translations will unnecessarily consume database resources and slow down queries.

## Use the merge translation endpoint of the translation service 
It will create, update and delete entity related translations.