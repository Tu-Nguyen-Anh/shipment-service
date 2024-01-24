package com.ncsgroup.shipment.server.controller;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.ResponseGeneral;
import com.ncsgroup.shipment.server.dto.shipment.ShipmentResponse;
import com.ncsgroup.shipment.server.facade.ShipmentFacadeService;
import com.ncsgroup.shipment.server.service.MessageService;
import com.ncsgroup.shipment.client.dto.ShipmentRequest;
import com.ncsgroup.shipment.server.service.ShipmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.ncsgroup.shipment.server.constanst.Constants.CommonConstants.*;
import static com.ncsgroup.shipment.server.constanst.Constants.CommonConstants.PARAM_ALL;
import static com.ncsgroup.shipment.server.constanst.Constants.MessageCode.*;
import static com.ncsgroup.shipment.server.constanst.Constants.VariableConstants.*;

@RestController
@RequestMapping("/api/v1/shipments")
@Slf4j
@RequiredArgsConstructor

public class ShipmentController {
  private final ShipmentFacadeService shipmentFacadeService;
  private final MessageService messageService;
  private final ShipmentService shipmentService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseGeneral<ShipmentResponse> create(
        @Valid @RequestBody ShipmentRequest request,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(create)request: {}", request);

    return ResponseGeneral.ofCreated(
          messageService.getMessage(CREATE_SHIPMENT_SUCCESS, language),
          shipmentFacadeService.create(request));
  }


  @PutMapping("{id}")
  public ResponseGeneral<ShipmentResponse> update(
        @Valid @RequestBody ShipmentRequest request,
        @PathVariable String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(update)request:{}, id: {}", request, id);

    return ResponseGeneral.ofSuccess(
          messageService.getMessage(UPDATE_SHIPMENT_SUCCESS, language),
          shipmentFacadeService.update(request, id));
  }

  @DeleteMapping("{id}")
  public ResponseGeneral<Void> delete(
        @PathVariable String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(delete) id: {}", id);
    shipmentService.delete(id);
    return ResponseGeneral.ofSuccess(
          messageService.getMessage(DELETE_SHIPMENT_SUCCESS, language)
    );
  }

  @GetMapping("{id}")
  public ResponseGeneral<ShipmentResponse> detail(
        @PathVariable String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(detail) id: {}", id);

    return ResponseGeneral.ofSuccess(
          messageService.getMessage(DETAIL_SHIPMENT_SUCCESS, language),
          shipmentFacadeService.detail(id));
  }

  @GetMapping
  public ResponseGeneral<PageResponse<ShipmentResponse>> list(
        @RequestParam(name = PARAM_KEYWORD, required = false) String keyword,
        @RequestParam(name = PARAM_SIZE, defaultValue = SIZE_DEFAULT) int size,
        @RequestParam(name = PARAM_PAGE, defaultValue = PAGE_DEFAULT) int page,
        @RequestParam(name = PARAM_ALL, defaultValue = IS_ALL_DEFAULT, required = false) boolean isAll,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(list) keyword: {}, size : {}, page: {}, isAll: {}", keyword, size, page, isAll);
    return ResponseGeneral.ofSuccess(messageService.getMessage(LIST_SHIPMENT_SUCCESS, language),
          shipmentService.list(keyword, size, page, isAll)
    );
  }

}
