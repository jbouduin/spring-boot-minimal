package de.der_e_coach.minimal_service.service;

import java.util.List;

import de.der_e_coach.minimal_service.dto.ItemDto;
import de.der_e_coach.shared_lib.dto.result.ResultDto;

public interface ItemService {
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
   * @return      the created {@link ItemDto}
   */
  ResultDto<ItemDto> createItem(ItemDto item);
}
