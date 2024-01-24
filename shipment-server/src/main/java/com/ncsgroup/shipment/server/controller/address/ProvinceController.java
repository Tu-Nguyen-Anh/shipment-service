package com.ncsgroup.shipment.server.controller.address;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.ResponseGeneral;
import com.ncsgroup.shipment.server.dto.address.province.ProvinceInfoResponse;
import com.ncsgroup.shipment.server.dto.address.province.ProvinceResponse;
import com.ncsgroup.shipment.server.service.MessageService;
import com.ncsgroup.shipment.server.service.address.ProvinceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.ncsgroup.shipment.server.constanst.Constants.CommonConstants.*;
import static com.ncsgroup.shipment.server.constanst.Constants.CommonConstants.PARAM_ALL;
import static com.ncsgroup.shipment.server.constanst.Constants.MessageCode.DETAIL_PROVINCE;
import static com.ncsgroup.shipment.server.constanst.Constants.MessageCode.GET_PROVINCE_SUCCESS;
import static com.ncsgroup.shipment.server.constanst.Constants.VariableConstants.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/provinces")
public class ProvinceController {
  private final ProvinceService provinceService;
  private final MessageService messageService;

  @GetMapping
  public ResponseGeneral<PageResponse<ProvinceResponse>> list(
        @RequestParam(name = PARAM_KEYWORD, required = false) String keyword,
        @RequestParam(name = PARAM_SIZE, defaultValue = SIZE_DEFAULT) int size,
        @RequestParam(name = PARAM_PAGE, defaultValue = PAGE_DEFAULT) int page,
        @RequestParam(name = PARAM_ALL, defaultValue = IS_ALL_DEFAULT, required = false) boolean isAll,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    log.info("(list) keyword: {}, size : {}, page: {}, isAll: {}", keyword, size, page, isAll);
    return ResponseGeneral.ofSuccess(
          messageService.getMessage(GET_PROVINCE_SUCCESS, language),
          provinceService.list(keyword, size, page, isAll)
    );
  }

  @GetMapping("details/{code}")
  public ResponseGeneral<ProvinceInfoResponse> detail(
        @PathVariable String code,
        @RequestHeader(name = LANGUAGE, defaultValue = DEFAULT_LANGUAGE) String language
  ) {
    return ResponseGeneral.ofSuccess(
          messageService.getMessage(DETAIL_PROVINCE, language),
          provinceService.detail(code)
    );
  }
}
