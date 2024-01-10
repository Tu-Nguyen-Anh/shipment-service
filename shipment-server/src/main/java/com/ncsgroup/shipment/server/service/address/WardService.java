package com.ncsgroup.shipment.server.service.address;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.address.ward.WardInfoResponse;
import com.ncsgroup.shipment.server.dto.address.ward.WardPageResponse;
import com.ncsgroup.shipment.client.dto.address.SearchWardRequest;
import com.ncsgroup.shipment.server.dto.address.ward.WardResponse;

public interface WardService {
  PageResponse<WardResponse> search(SearchWardRequest request, int size, int page, boolean isAll);

  WardInfoResponse detail(String code);

  void checkWardExist(String code);
}
