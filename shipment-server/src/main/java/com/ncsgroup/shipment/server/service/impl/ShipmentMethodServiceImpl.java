package com.ncsgroup.shipment.server.service.impl;

import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.shipmentmethod.ShipmentMethodResponse;
import com.ncsgroup.shipment.server.entity.ShipmentMethod;
import com.ncsgroup.shipment.server.exception.shipmentmethod.ShipmentMethodAlreadyExistException;
import com.ncsgroup.shipment.server.exception.shipmentmethod.ShipmentMethodNotFoundException;
import com.ncsgroup.shipment.server.repository.ShipmentMethodRepository;
import com.ncsgroup.shipment.server.service.ShipmentMethodService;
import com.ncsgroup.shipment.server.service.base.BaseServiceImpl;
import com.ncsgroup.shipment.client.dto.ShipmentMethodRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Slf4j
public class ShipmentMethodServiceImpl extends BaseServiceImpl<ShipmentMethod> implements ShipmentMethodService {
  private final ShipmentMethodRepository repository;

  public ShipmentMethodServiceImpl(ShipmentMethodRepository repository) {
    super(repository);
    this.repository = repository;
  }

  @Override
  @Transactional
  public ShipmentMethodResponse create(ShipmentMethodRequest request) {
    log.info("(create) request: {}", request);
    this.checkShipmentMethodAlreadyExists(request.getName());
    ShipmentMethod shipmentMethod = ShipmentMethod.from(
          request.getName(),
          request.getDescription(),
          request.getPricePerKilometer()
    );
    create(shipmentMethod);
    return convertToResponse(shipmentMethod);
  }

  @Override
  @Transactional
  public ShipmentMethodResponse update(String id, ShipmentMethodRequest request) {
    log.info("(update) request: {}", request);
    ShipmentMethod shipmentMethod = findById(id);
    this.checkNameShipmentMethodAlreadyExists(shipmentMethod, request);
    setValueUpdate(shipmentMethod, request);
    shipmentMethod = update(shipmentMethod);
    return convertToResponse(shipmentMethod);
  }

  @Override
  public PageResponse<ShipmentMethodResponse> list(String keyword, int size, int page, boolean isAll) {
    log.info("(list) keyword: {}, size : {}, page: {}, isAll: {}", keyword, size, page, isAll);

    Pageable pageable = PageRequest.of(page, size);
    Page<ShipmentMethodResponse> pageResponse = isAll ?
          repository.findAllShipmentMethod(pageable) : repository.search(keyword, pageable);

    return PageResponse.of(pageResponse.getContent(), (int) pageResponse.getTotalElements());
  }

  @Override
  @Transactional
  public void delete(String id) {
    log.info("(delete) request: {}", id);
    this.checkAlreadyById(id);
    repository.deleteById(id);
  }

  @Override
  public ShipmentMethodResponse detail(String id) {
    log.info("(detail) request: {}", id);
    ShipmentMethod shipmentMethod = findById(id);
    return new ShipmentMethodResponse(
          shipmentMethod.getId(),
          shipmentMethod.getName(),
          shipmentMethod.getDescription(),
          shipmentMethod.getPricePerKilometer()
    );
  }

  private ShipmentMethod findById(String id) {
    log.debug("(findById) id: {}", id);
    ShipmentMethod shipmentMethod = repository.findById(id).orElseThrow(ShipmentMethodNotFoundException::new);
    if (shipmentMethod.isDeleted())
      throw new ShipmentMethodNotFoundException();
    return shipmentMethod;
  }

  private void checkShipmentMethodAlreadyExists(String name) {
    log.debug("checkShipmentMethodAlreadyExists :{}", name);
    if (repository.existsByName(name)) {
      log.error("Shipment Method AlreadyExists:{}", name);
      throw new ShipmentMethodAlreadyExistException();
    }
  }

  private void checkNameShipmentMethodAlreadyExists(ShipmentMethod shipmentMethod, ShipmentMethodRequest request) {
    log.debug("check name of shipment method AlreadyExists when update");
    if (!shipmentMethod.getName().equals(request.getName()))
      this.checkShipmentMethodAlreadyExists(request.getName());
  }

  private void setValueUpdate(ShipmentMethod shipmentmethod, ShipmentMethodRequest request) {
    shipmentmethod.setName(request.getName());
    shipmentmethod.setDescription(request.getDescription());
    shipmentmethod.setPricePerKilometer(request.getPricePerKilometer());
  }

  private ShipmentMethodResponse convertToResponse(ShipmentMethod shipmentMethod) {
    return new ShipmentMethodResponse(
          shipmentMethod.getId(),
          shipmentMethod.getName(),
          shipmentMethod.getDescription(),
          shipmentMethod.getPricePerKilometer());
  }

  private void checkAlreadyById(String id) {
    log.debug("Checking existing shipment by id {}", id);
    if (!repository.existsById(id)) {
      throw new ShipmentMethodNotFoundException();
    }
  }
}
