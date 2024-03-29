package com.ncsgroup.shipment.server.service.address;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.address.province.ProvinceInfoResponse;
import com.ncsgroup.shipment.server.dto.address.province.ProvinceResponse;
import com.ncsgroup.shipment.server.entity.address.Province;
import com.ncsgroup.shipment.server.service.base.BaseService;

public interface ProvinceService extends BaseService<Province> {
  PageResponse<ProvinceResponse> list(String keyword, int size, int page, boolean isAll);
  ProvinceInfoResponse detail(String code);
  void checkProvinceExist(String code);
}
