package com.ncsgroup.shipment.server.controller.address;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.ResponseGeneral;
import com.ncsgroup.shipment.server.dto.address.district.DistrictInfoResponse;
import com.ncsgroup.shipment.server.dto.address.district.DistrictResponse;
import com.ncsgroup.shipment.server.service.MessageService;
import com.ncsgroup.shipment.server.service.address.DistrictService;
import com.ncsgroup.shipment.client.dto.address.SearchDistrictRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.ncsgroup.shipment.server.constanst.Constants.CommonConstants.*;
import static com.ncsgroup.shipment.server.constanst.Constants.CommonConstants.PARAM_ALL;
import static com.ncsgroup.shipment.server.constanst.Constants.MessageCode.*;
import static com.ncsgroup.shipment.server.constanst.Constants.VariableConstants.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/districts")
@RequiredArgsConstructor
public class DistrictController {
  private final MessageService messageService;
  private final DistrictService districtService;

  @PostMapping
  public ResponseGeneral<PageResponse<DistrictResponse>> list(
        @RequestBody(required = false) SearchDistrictRequest request,
        @RequestParam(name = PARAM_SIZE, defaultValue = SIZE_DEFAULT) int size,
        @RequestParam(name = PARAM_PAGE, defaultValue = PAGE_DEFAULT) int page,
        @RequestParam(name = PARAM_ALL, defaultValue = IS_ALL_DEFAULT, required = false) boolean isAll,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(search) request: {}, size:{}, page:{}, isAll:{}", request, size, page, isAll);

    return ResponseGeneral.ofSuccess(
          messageService.getMessage(GET_DISTRICT_SUCCESS, language),
          districtService.search(request, size, page, isAll)
    );
  }

  @GetMapping("details/{code}")
  public ResponseGeneral<DistrictInfoResponse> detail(
        @PathVariable String code,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    return ResponseGeneral.ofSuccess(
          messageService.getMessage(DETAIL_DISTRICT, language),
          districtService.detail(code)
    );
  }
}