package com.ncsgroup.shipment.server.service;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.shipmentmethod.ShipmentMethodResponse;
import com.ncsgroup.shipment.server.entity.ShipmentMethod;
import com.ncsgroup.shipment.server.service.base.BaseService;
import com.ncsgroup.shipment.client.dto.ShipmentMethodRequest;

public interface ShipmentMethodService extends BaseService<ShipmentMethod> {
  ShipmentMethodResponse create(ShipmentMethodRequest request);

  ShipmentMethodResponse update(String id, ShipmentMethodRequest request);

  PageResponse<ShipmentMethodResponse> list(String keyword, int size, int page, boolean isAll);

  void delete(String id);

  ShipmentMethodResponse detail(String id);
}
