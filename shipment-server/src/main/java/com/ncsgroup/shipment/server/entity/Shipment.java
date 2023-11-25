package com.ncsgroup.shipment.server.entity;

import com.ncsgroup.shipment.server.entity.base.BaseEntityWithUpdater;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "shipments")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Shipment extends BaseEntityWithUpdater {
  @Column(name = "code")
  private String code;
  @Column(name = "order_id")
  private String orderId;
  @Column(name = "from_address_id")
  private String fromAddressId;
  @Column(name = "to_address_id")
  private String toAddressId;
  @Column(name = "price")
  private Double price;
  @Column(name = "shipment_method_id")
  private String shipmentMethodId;
  @Column(name = "is_deleted")
  private boolean isDeleted;

  public Shipment(
        String orderId,
        String fromAddressId,
        String toAddressId,
        Double price,
        String shipmentMethodId
  ) {
    this.orderId = orderId;
    this.fromAddressId = fromAddressId;
    this.toAddressId = toAddressId;
    this.price = price;
    this.shipmentMethodId = shipmentMethodId;
  }

}
