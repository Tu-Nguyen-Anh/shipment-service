package com.ncsgroup.shipment.server.facade;

import com.ncsgroup.shipment.server.configuration.ShipmentTestConfiguration;
import com.ncsgroup.shipment.server.dto.address.AddressResponse;
import com.ncsgroup.shipment.server.exception.address.AddressNotFoundException;
import com.ncsgroup.shipment.server.service.address.AddressService;
import com.ncsgroup.shipment.server.service.address.DistrictService;
import com.ncsgroup.shipment.server.service.address.ProvinceService;
import com.ncsgroup.shipment.server.service.address.WardService;
import com.ncsgroup.shipment.client.dto.address.AddressRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

@WebMvcTest(AddressFacadeService.class)
@ContextConfiguration(classes = ShipmentTestConfiguration.class)
class AddressFacadeTest {
  @MockBean
  private AddressService addressService;
  @MockBean
  private ProvinceService provinceService;
  @MockBean
  private DistrictService districtService;
  @MockBean
  private WardService wardService;
  @Autowired
  private AddressFacadeService addressFacadeService;

  private AddressRequest mockAddressRequest() {
    AddressRequest addressRequest = new AddressRequest();
    addressRequest.setProvinceCode("30");
    addressRequest.setDistrictCode("293");
    addressRequest.setWardCode("10081");
    addressRequest.setDetail("Tam Ky Kim Thanh Hai Duong");
    return addressRequest;
  }

  private AddressResponse mockFacadeResponse() {
    AddressResponse addressResponse = new AddressResponse();
    addressResponse.setId("idMock");
    addressResponse.setProvinces("Hai Duong");
    addressResponse.setDistricts("Kim Thanh");
    addressResponse.setWards("Tam Ky");
    addressResponse.setDetail("Tam Ky Kim Thanh Hai Duong");
    return addressResponse;
  }

  @Test
  void testCreateAddress_WhenProvinceCodeNull_Return_AddressNotFound() throws Exception {
    AddressRequest request = mockAddressRequest();
    Mockito.doThrow(new AddressNotFoundException(true, false, false)).when(provinceService).checkProvinceExist(request.getProvinceCode());

    Assertions.assertThatThrownBy(() -> addressFacadeService.createAddress(request))
          .isInstanceOf(AddressNotFoundException.class)
          .hasFieldOrPropertyWithValue("code", "com.ncsgroup.shipment.server.exception.address.AddressNotFoundException.Province");
  }

  @Test
  void testCreateAddress_WhenDistrictCodeNull_Return_AddressNotFound() throws Exception {
    AddressRequest request = mockAddressRequest();
    Mockito.doThrow(new AddressNotFoundException(false, true, false)).when(districtService).checkDistrictExist(request.getDistrictCode());

    Assertions.assertThatThrownBy(() -> addressFacadeService.createAddress(request))
          .isInstanceOf(AddressNotFoundException.class)
          .hasFieldOrPropertyWithValue("code", "com.ncsgroup.shipment.server.exception.address.AddressNotFoundException.District");
  }

  @Test
  void testCreateAddress_WhenWardCodeNull_Return_AddressNotFound() throws Exception {
    AddressRequest request = mockAddressRequest();
    Mockito.doThrow(new AddressNotFoundException(false, false, true)).when(wardService).checkWardExist(request.getWardCode());

    Assertions.assertThatThrownBy(() -> addressFacadeService.createAddress(request))
          .isInstanceOf(AddressNotFoundException.class)
          .hasFieldOrPropertyWithValue("code", "com.ncsgroup.shipment.server.exception.address.AddressNotFoundException.Ward");
  }

  @Test
  void test_CreateAddress_WhenSuccess_Return_Response() {
    AddressRequest mockRequest = mockAddressRequest();
    AddressResponse mockFacadeResponse = mockFacadeResponse();

    Mockito.when(addressService.create(mockRequest)).thenReturn(mockFacadeResponse);

    AddressResponse response = addressFacadeService.createAddress(mockRequest);
    Assertions.assertThat(response.getProvinces()).isEqualTo(mockFacadeResponse.getProvinces());
    Assertions.assertThat(response.getDistricts()).isEqualTo(mockFacadeResponse.getDistricts());
    Assertions.assertThat(response.getWards()).isEqualTo(mockFacadeResponse.getWards());
    Assertions.assertThat(response.getDetail()).isEqualTo(mockFacadeResponse.getDetail());
  }

  @Test
  void testUpdate_WhenProvinceNotFound_Return_AddressNotFound() throws Exception {
    AddressRequest request = mockAddressRequest();
    Mockito.doThrow(new AddressNotFoundException(true, false, false)).when(provinceService).checkProvinceExist(request.getProvinceCode());

    Assertions.assertThatThrownBy(() -> addressFacadeService.updateAddress(request, "ok"))
          .isInstanceOf(AddressNotFoundException.class)
          .hasFieldOrPropertyWithValue("code", "com.ncsgroup.shipment.server.exception.address.AddressNotFoundException.Province");
  }

  @Test
  void testUpdateAddress_WhenDistrictCodeNotFound_Return_AddressNotFound() throws Exception {
    AddressRequest request = mockAddressRequest();
    Mockito.doThrow(new AddressNotFoundException(false, true, false)).when(districtService).checkDistrictExist(request.getDistrictCode());

    Assertions.assertThatThrownBy(() -> addressFacadeService.updateAddress(request, "test"))
          .isInstanceOf(AddressNotFoundException.class)
          .hasFieldOrPropertyWithValue("code", "com.ncsgroup.shipment.server.exception.address.AddressNotFoundException.District");
  }

  @Test
  void testUpdateAddress_WhenWardCodeNotFound_Return_AddressNotFound() throws Exception {
    AddressRequest request = mockAddressRequest();
    Mockito.doThrow(new AddressNotFoundException(false, false, true)).when(wardService).checkWardExist(request.getWardCode());

    Assertions.assertThatThrownBy(() -> addressFacadeService.updateAddress(request, "test"))
          .isInstanceOf(AddressNotFoundException.class)
          .hasFieldOrPropertyWithValue("code", "com.ncsgroup.shipment.server.exception.address.AddressNotFoundException.Ward");
  }

  @Test
  void testUpdate_WhenSuccess_Return_Response() {
    AddressRequest mockRequest = mockAddressRequest();
    AddressResponse mockFacadeResponse = mockFacadeResponse();
    mockFacadeResponse.setId("test");
    Mockito.when(addressService.update(mockRequest, "test")).thenReturn(mockFacadeResponse);

    AddressResponse response = addressFacadeService.updateAddress(mockRequest, "test");
    Assertions.assertThat(response.getId()).isEqualTo("test");
    Assertions.assertThat(response.getProvinces()).isEqualTo(mockFacadeResponse.getProvinces());
    Assertions.assertThat(response.getDistricts()).isEqualTo(mockFacadeResponse.getDistricts());
    Assertions.assertThat(response.getWards()).isEqualTo(mockFacadeResponse.getWards());
    Assertions.assertThat(response.getDetail()).isEqualTo(mockFacadeResponse.getDetail());
  }
}
