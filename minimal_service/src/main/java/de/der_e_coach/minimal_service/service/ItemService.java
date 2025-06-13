package de.der_e_coach.minimal_service.service;

import java.util.List;

import de.der_e_coach.minimal_service.dto.ItemDto;
import de.der_e_coach.shared_lib.dto.result.ResultDto;

public interface ItemService {
  /**
   * Delete an item.
   * 
   * @param  itemId The Item id
   * @return        {@link ResultDto}. Status is NO_CONTENT if item was successfully deleted
   */
  ResultDto<Object> deleteItem(Long itemId);
  /**
   * Get items
   * 
   * @return List of {@link ItemDto}
   */
  ResultDto<List<ItemDto>> getItems();
  /**
   * Create an item.
   * 
   * @param  item {@link ItemDto}
   * @return      {@link ResultDto} containing the created {@link ItemDto}
   */
  ResultDto<ItemDto> createItem(ItemDto item);
  /**
   * Update an item.
   * 
   * @param  itemId the item id.
   * @param  item   {@link ItemDto}
   * @return        {@link ResultDto} containing the created {@link ItemDto}
   */
  ResultDto<ItemDto> updateItem(Long itemId, ItemDto item);
}
