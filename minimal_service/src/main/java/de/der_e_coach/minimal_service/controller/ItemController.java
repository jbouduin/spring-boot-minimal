package de.der_e_coach.minimal_service.controller;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.der_e_coach.minimal_service.dto.ItemDto;
import de.der_e_coach.minimal_service.service.ItemService;
import de.der_e_coach.shared_lib.dto.result.ResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Item")
@RequestMapping("/item")
public class ItemController {
  //#region Private fields ----------------------------------------------------
  private final ItemService itemService;
  //#endregion

  //#region Constructor -------------------------------------------------------
  public ItemController(ItemService itemService) {
    this.itemService = itemService;
  }
  //#endregion

  //#region Delete ------------------------------------------------------------
  @Operation(summary = "Delete item", description = "Delete an item.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "No content (success)"),
        @ApiResponse(responseCode = "404", description = "Item not found")
      }
  )
  @DeleteMapping(path = "{item-id}")
  public ResponseEntity<ResultDto<Object>> deleteItem(@PathVariable("item-id") Long itemId) {
    ResultDto<Object> result = itemService.deleteItem(itemId);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }

  //#endregion

  //#region Get ---------------------------------------------------------------
  @Operation(summary = "Get items", description = "Get all items.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
      }
  )
  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResultDto<List<ItemDto>>> getItems() {
    ResultDto<List<ItemDto>> result = itemService.getItems();
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion

  //#region Post --------------------------------------------------------------
  @Operation(summary = "Create item", description = "Create an item.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "401", description = "Not authenticated")
      }
  )
  @PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResultDto<ItemDto>> createItem(@RequestBody ItemDto item) {
    ResultDto<ItemDto> result = itemService.createItem(item);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion

  //#region Put --------------------------------------------------------------
  @Operation(summary = "Update item", description = "Update an item.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Success"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "401", description = "Not authenticated"),
        @ApiResponse(responseCode = "404", description = "Item not found")
      }
  )
  @PutMapping(path = "{item-id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ResultDto<ItemDto>> updateItem(
    @PathVariable("item-id") Long itemId,
    @RequestBody ItemDto item
  ) {
    ResultDto<ItemDto> result = itemService.updateItem(itemId, item);
    return ResponseEntity.status(result.getStatus().getValue()).body(result);
  }
  //#endregion
}
