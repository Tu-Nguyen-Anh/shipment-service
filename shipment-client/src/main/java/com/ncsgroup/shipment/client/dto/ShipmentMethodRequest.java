package com.ncsgroup.shipment.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static com.ncsgroup.shipment.client.constants.Constants.Validate.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentMethodRequest {
  @NotBlank(message = NAME_BLANK)
  private String name;
  @NotBlank(message = DESCRIPTION_BLANK)
  private String description;
  @NotNull(message = PRICE_BLANK)
  private Double pricePerKilometer;
}
