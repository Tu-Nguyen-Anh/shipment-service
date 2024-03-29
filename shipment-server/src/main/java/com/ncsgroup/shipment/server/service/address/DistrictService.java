package com.ncsgroup.shipment.server.service.address;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.address.district.DistrictInfoResponse;
import com.ncsgroup.shipment.server.dto.address.district.DistrictResponse;
import com.ncsgroup.shipment.server.entity.address.District;
import com.ncsgroup.shipment.server.service.base.BaseService;
import com.ncsgroup.shipment.client.dto.address.SearchDistrictRequest;

public interface DistrictService extends BaseService<District> {
  PageResponse<DistrictResponse> search(SearchDistrictRequest request, int size, int page, boolean isAll);

  DistrictInfoResponse detail(String code);

  void checkDistrictExist(String code);
}
