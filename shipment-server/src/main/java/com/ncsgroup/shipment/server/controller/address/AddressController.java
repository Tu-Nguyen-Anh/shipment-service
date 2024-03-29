package com.ncsgroup.shipment.server.controller.address;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.ResponseGeneral;
import com.ncsgroup.shipment.server.dto.address.AddressResponse;
import com.ncsgroup.shipment.server.facade.AddressFacadeService;
import com.ncsgroup.shipment.server.service.MessageService;
import com.ncsgroup.shipment.server.service.address.AddressService;
import com.ncsgroup.shipment.client.dto.address.AddressRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.ncsgroup.shipment.server.constanst.Constants.CommonConstants.*;
import static com.ncsgroup.shipment.server.constanst.Constants.MessageCode.*;
import static com.ncsgroup.shipment.server.constanst.Constants.VariableConstants.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/addresses")
@Slf4j
public class AddressController {
  private final AddressService addressService;
  private final AddressFacadeService addressFacadeService;
  private final MessageService messageService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public ResponseGeneral<AddressResponse> create(
        @Valid @RequestBody AddressRequest request,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(create)request: {}", request);

    return ResponseGeneral.ofCreated(
          messageService.getMessage(CREATE_ADDRESS_SUCCESS, language),
          addressFacadeService.createAddress(request)
    );
  }

  @GetMapping
  public ResponseGeneral<PageResponse<AddressResponse>> list(
        @RequestParam(name = PARAM_KEYWORD, required = false) String keyword,
        @RequestParam(name = PARAM_SIZE, defaultValue = SIZE_DEFAULT) int size,
        @RequestParam(name = PARAM_PAGE, defaultValue = PAGE_DEFAULT) int page,
        @RequestParam(name = PARAM_ALL, defaultValue = IS_ALL_DEFAULT, required = false) boolean isAll,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(list) keyword: {}, size : {}, page: {}, isAll: {}", keyword, size, page, isAll);
    return ResponseGeneral.ofSuccess(messageService.getMessage(LIST_ADDRESS, language),
          addressService.list(keyword, size, page, isAll)
    );
  }

  @GetMapping("{id}")
  public ResponseGeneral<AddressResponse> detail(
        @PathVariable String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(detail) id: {}", id);

    return ResponseGeneral.ofSuccess(
          messageService.getMessage(DETAIL_ADDRESS, language),
          addressService.detail(id)
    );
  }

  @PutMapping("{id}")
  public ResponseGeneral<AddressResponse> update(
        @PathVariable String id, @Valid @RequestBody AddressRequest request,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(update) id: {}", id);
    return ResponseGeneral.ofSuccess(
          messageService.getMessage(UPDATE_ADDRESS, language),
          addressFacadeService.updateAddress(request, id)
    );
  }

  @DeleteMapping("{id}")
  public ResponseGeneral<Void> delete(
        @PathVariable String id,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(delete) id: {}", id);
    addressService.delete(id);
    return ResponseGeneral.ofSuccess(messageService.getMessage(DELETE_ADDRESS, language));
  }
}
