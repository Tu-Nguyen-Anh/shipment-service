package com.ncsgroup.shipment.server.service.address.impl;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.address.province.ProvinceInfoResponse;
import com.ncsgroup.shipment.server.dto.address.province.ProvinceResponse;
import com.ncsgroup.shipment.server.entity.address.Province;
import com.ncsgroup.shipment.server.exception.address.AddressNotFoundException;
import com.ncsgroup.shipment.server.repository.address.ProvinceRepository;
import com.ncsgroup.shipment.server.service.address.ProvinceService;
import com.ncsgroup.shipment.server.service.base.BaseServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Slf4j
public class ProvinceServiceImpl extends BaseServiceImpl<Province> implements ProvinceService {
  private final ProvinceRepository repository;

  public ProvinceServiceImpl(ProvinceRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public PageResponse<ProvinceResponse> list(String keyword, int size, int page, boolean isAll) {
    log.info("(list) keyword: {}, size : {}, page: {}, isAll: {}", keyword, size, page, isAll);
    Pageable pageable = PageRequest.of(page, size);
    Page<ProvinceResponse> list = isAll ?
          repository.findAllProvinces(pageable) : repository.search(pageable, keyword);
    return PageResponse.of(list.getContent(), (int) list.getTotalElements());
  }

  @Override
  public ProvinceInfoResponse detail(String code) {
    log.info("detail by code {}", code);
    this.checkProvinceExist(code);
    return repository.getByCode(code);
  }

  @Override
  public void checkProvinceExist(String code) {
    log.debug("check province by code {}", code);
    if (!repository.existsByCode(code)) {
      log.error("(checkProvinceExist) ========> (AddressNotFoundException)");
      throw new AddressNotFoundException(true, false, false);
    }
  }

}
