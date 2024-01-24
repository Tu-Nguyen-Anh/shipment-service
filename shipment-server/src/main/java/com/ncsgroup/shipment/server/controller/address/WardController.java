package com.ncsgroup.shipment.server.controller.address;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.ResponseGeneral;
import com.ncsgroup.shipment.server.dto.address.ward.WardInfoResponse;
import com.ncsgroup.shipment.server.dto.address.ward.WardResponse;
import com.ncsgroup.shipment.server.service.MessageService;
import com.ncsgroup.shipment.server.service.address.WardService;
import com.ncsgroup.shipment.client.dto.address.SearchWardRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.ncsgroup.shipment.server.constanst.Constants.CommonConstants.*;
import static com.ncsgroup.shipment.server.constanst.Constants.CommonConstants.PARAM_ALL;
import static com.ncsgroup.shipment.server.constanst.Constants.MessageCode.*;
import static com.ncsgroup.shipment.server.constanst.Constants.VariableConstants.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wards")
public class WardController {
  private final WardService wardService;
  private final MessageService messageService;

  @PostMapping
  public ResponseGeneral<PageResponse<WardResponse>> list(
        @RequestBody(required = false) SearchWardRequest request,
        @RequestParam(name = PARAM_SIZE, defaultValue = SIZE_DEFAULT) int size,
        @RequestParam(name = PARAM_PAGE, defaultValue = PAGE_DEFAULT) int page,
        @RequestParam(name = PARAM_ALL, defaultValue = IS_ALL_DEFAULT, required = false) boolean isAll,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(search) request: {}, size:{}, page:{}, isAll:{}", request, size, page, isAll);
    return ResponseGeneral.ofSuccess(
          messageService.getMessage(GET_WARD_SUCCESS, language),
          wardService.search(request, size, page, isAll)
    );
  }

  @GetMapping("details/{code}")
  public ResponseGeneral<WardInfoResponse> detail(
        @PathVariable String code,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    return ResponseGeneral.ofSuccess(
          messageService.getMessage(DETAIL_WARD, language),
          wardService.detail(code)
    );
  }
}