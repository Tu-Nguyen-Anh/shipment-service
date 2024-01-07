package com.ncsgroup.shipment.server.service.address.impl;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.address.district.DistrictInfoResponse;
import com.ncsgroup.shipment.server.dto.address.district.DistrictPageResponse;
import com.ncsgroup.shipment.server.dto.address.district.DistrictResponse;
import com.ncsgroup.shipment.server.dto.address.province.ProvinceResponse;
import com.ncsgroup.shipment.server.entity.address.District;
import com.ncsgroup.shipment.server.exception.address.AddressNotFoundException;
import com.ncsgroup.shipment.server.repository.address.DistrictRepository;
import com.ncsgroup.shipment.server.service.address.DistrictService;
import com.ncsgroup.shipment.server.service.base.BaseServiceImpl;
import com.ncsgroup.shipment.client.dto.address.SearchDistrictRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
public class DistrictServiceImpl extends BaseServiceImpl<District> implements DistrictService {
  private final DistrictRepository repository;

  public DistrictServiceImpl(DistrictRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public PageResponse<DistrictResponse> search(SearchDistrictRequest request, int size, int page, boolean isAll) {
    log.info("(search) request: {}, size:{}, page:{}, isAll: {}", request, size, page, isAll);
    String keyword = (request == null || request.getKeyword() == null) ? null : request.getKeyword().toLowerCase();
    String provinceCode = (request == null) ? null : request.getProvinceCode();
    Pageable pageable = PageRequest.of(page, size);
    Page<DistrictResponse> list = isAll ?
          repository.findAllDistrict(provinceCode,pageable) : repository.searchDistrict(keyword,provinceCode, pageable);
    return PageResponse.of(list.getContent(), (int) list.getTotalElements());
  }

  @Override
  public DistrictInfoResponse detail(String code) {
    log.info("(detail)code: {}", code);
    this.checkDistrictExist(code);
    return repository.getByCode(code);
  }

  @Override
  public void checkDistrictExist(String code) {
    log.debug("(checkDistrictExist)");

    if (!repository.existsByCode(code)) {
      log.error("(checkDistrictExist) ============= AddressExistException");
      throw new AddressNotFoundException(false, true, false);
    }
  }

}
