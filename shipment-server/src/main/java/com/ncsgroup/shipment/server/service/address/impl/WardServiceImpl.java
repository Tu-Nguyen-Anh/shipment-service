package com.ncsgroup.shipment.server.service.address.impl;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.address.ward.WardInfoResponse;
import com.ncsgroup.shipment.server.dto.address.ward.WardPageResponse;
import com.ncsgroup.shipment.server.dto.address.ward.WardResponse;
import com.ncsgroup.shipment.server.entity.address.Ward;
import com.ncsgroup.shipment.server.exception.address.AddressNotFoundException;
import com.ncsgroup.shipment.server.repository.address.WardRepository;
import com.ncsgroup.shipment.server.service.address.WardService;
import com.ncsgroup.shipment.server.service.base.BaseServiceImpl;
import com.ncsgroup.shipment.client.dto.address.SearchWardRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
public class WardServiceImpl extends BaseServiceImpl<Ward> implements WardService {
  private final WardRepository repository;

  public WardServiceImpl(WardRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  public PageResponse<WardResponse> search(SearchWardRequest request, int size, int page, boolean isAll) {
    log.info("(search) request: {}, size:{}, page:{}, isAll: {}", request, size, page, isAll);

    String keyword = (request == null || request.getKeyword() == null) ? null : request.getKeyword().toLowerCase();
    String districtCode = (request == null) ? null : request.getDistrictCode();
    Pageable pageable = PageRequest.of(page, size);
    Page<WardResponse> list = isAll ? repository.findAllWard(districtCode, pageable) :
          repository.searchWard(keyword, districtCode, pageable);

    return PageResponse.of(list.getContent(), (int) list.getTotalElements());
  }

  @Override
  public WardInfoResponse detail(String code) {
    log.info("(detail)code: {}", code);
    this.checkWardExist(code);
    return repository.getByCode(code);
  }

  @Override
  public void checkWardExist(String code) {
    log.debug("(checkWardExist)");

    if (!repository.existsByCode(code)) {
      log.error("(checkExist) ============= AddressExistException");
      throw new AddressNotFoundException(false, false, true);
    }
  }
}
