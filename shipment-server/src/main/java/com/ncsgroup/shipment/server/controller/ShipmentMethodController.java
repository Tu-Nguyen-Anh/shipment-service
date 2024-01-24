package com.ncsgroup.shipment.server.controller;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.ResponseGeneral;
import com.ncsgroup.shipment.server.dto.shipmentmethod.ShipmentMethodResponse;
import com.ncsgroup.shipment.server.service.MessageService;
import com.ncsgroup.shipment.server.service.ShipmentMethodService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import com.ncsgroup.shipment.client.dto.ShipmentMethodRequest;

import static com.ncsgroup.shipment.server.constanst.Constants.CommonConstants.*;
import static com.ncsgroup.shipment.server.constanst.Constants.CommonConstants.PARAM_ALL;
import static com.ncsgroup.shipment.server.constanst.Constants.MessageCode.*;
import static com.ncsgroup.shipment.server.constanst.Constants.VariableConstants.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shipment-methods")
@Slf4j
public class ShipmentMethodController {
  private final ShipmentMethodService shipmentMethodService;
  private final MessageService messageService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseGeneral<ShipmentMethodResponse> create(
        @Valid @RequestBody ShipmentMethodRequest request,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    return ResponseGeneral.ofCreated(
          messageService.getMessage(CREATE_SHIPMENT_METHOD_SUCCESS, language),
          shipmentMethodService.create(request)
    );
  }

  @PutMapping("/{id}")
  public ResponseGeneral<ShipmentMethodResponse> update(
        @Valid @RequestBody ShipmentMethodRequest request,
        @PathVariable String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("Update with id:{}", id);
    return ResponseGeneral.ofSuccess(
          messageService.getMessage(UPDATE_SHIPMENT_METHOD_SUCCESS, language),
          shipmentMethodService.update(id, request)
    );
  }

  @GetMapping
  public ResponseGeneral<PageResponse<ShipmentMethodResponse>> list(
        @RequestParam(name = PARAM_KEYWORD, required = false) String keyword,
        @RequestParam(name = PARAM_SIZE, defaultValue = SIZE_DEFAULT) int size,
        @RequestParam(name = PARAM_PAGE, defaultValue = PAGE_DEFAULT) int page,
        @RequestParam(name = PARAM_ALL, defaultValue = IS_ALL_DEFAULT, required = false) boolean isAll,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(list) keyword: {}, size : {}, page: {}, isAll: {}", keyword, size, page, isAll);
    return ResponseGeneral.ofSuccess(messageService.getMessage(GET_SHIPMENT_METHOD_SUCCESS, language),
          shipmentMethodService.list(keyword, size, page, isAll)
    );
  }

  @DeleteMapping("/{id}")
  public ResponseGeneral<Void> delete(
        @PathVariable String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("delete by id:{} ", id);
    shipmentMethodService.delete(id);
    return ResponseGeneral.ofSuccess(messageService.getMessage(DELETE_SUCCESS, language));
  }

  @GetMapping("/{id}")
  public ResponseGeneral<ShipmentMethodResponse> detail(
        @PathVariable String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("detail with id:{}", id);
    return ResponseGeneral.ofSuccess(
          messageService.getMessage(DETAIL_SHIPMENT_METHOD, language),
          shipmentMethodService.detail(id)
    );
  }
}
