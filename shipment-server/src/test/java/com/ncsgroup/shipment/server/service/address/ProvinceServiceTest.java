package com.ncsgroup.shipment.server.service.address;

import com.ncsgroup.shipment.server.configuration.ShipmentTestConfiguration;
import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.address.province.ProvinceInfoResponse;
import com.ncsgroup.shipment.server.dto.address.province.ProvinceResponse;
import com.ncsgroup.shipment.server.exception.address.AddressNotFoundException;
import com.ncsgroup.shipment.server.repository.address.ProvinceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@WebMvcTest(ProvinceService.class)
@ContextConfiguration(classes = ShipmentTestConfiguration.class)
class ProvinceServiceTest {
  @MockBean
  private ProvinceRepository repository;
  @Autowired
  private ProvinceService provinceService;

  private ProvinceResponse mockProvinceResponse() {
    ProvinceResponse mockResponse = new ProvinceResponse();
    mockResponse.setCode("02");
    mockResponse.setName("Hai Duong");
    mockResponse.setNameEn("Ha Duong");
    mockResponse.setFullName("Tinh Hai Duong");
    mockResponse.setFullNameEn("Hai Duong Province");
    mockResponse.setCodeName("hai_duong");
    return mockResponse;
  }

  private ProvinceInfoResponse mockProvinceInfo() {
    ProvinceInfoResponse mockProvinceResponse = new ProvinceInfoResponse();
    mockProvinceResponse.setCode("02");
    mockProvinceResponse.setProvinceName("Hai Duong");
    mockProvinceResponse.setProvinceNameEn("Hai Duong");
    mockProvinceResponse.setProvinceCodeName("hai_duong");
    return mockProvinceResponse;
  }

  @Test
  void testList_WhenAllTrue_ReturnProvincePageResponse() {
    Pageable pageable = PageRequest.of(0, 10);
    ProvinceResponse mockResponse = mockProvinceResponse();

    List<ProvinceResponse> list = new ArrayList<>();
    list.add(mockResponse);

    Page<ProvinceResponse> page = new PageImpl<>(list);
    Mockito.when(repository.findAllProvinces(pageable)).thenReturn(page);
    PageResponse<ProvinceResponse> response = provinceService.list(null, 10, 0, true);
    assertThat(list).hasSize(response.getAmount());
  }

  @Test
  void testList_WhenAllFalse_ReturnProvincePageResponse() {
    Pageable pageable = PageRequest.of(0, 10);
    ProvinceResponse mockResponse = mockProvinceResponse();

    List<ProvinceResponse> list = new ArrayList<>();
    list.add(mockResponse);

    Page<ProvinceResponse> page = new PageImpl<>(list);

    Mockito.when(repository.search(pageable, "ha_noi")).thenReturn(page);
    Mockito.when(repository.countSearch("ha_noi")).thenReturn(list.size());
    PageResponse<ProvinceResponse> response = provinceService.list("ha_noi", 10, 0, false);
    assertThat(list).hasSize(response.getAmount());
  }

  @Test
  void testDetail_WhenCodeNotFound_ReturnsProvinceNotFoundException() {
    Mockito.when(repository.existsByCode("011")).thenReturn(false);
    Assertions.assertThatThrownBy(() -> provinceService.detail("011")).isInstanceOf(AddressNotFoundException.class);
  }

  @Test
  void testDetail_WhenGetSuccess_ReturnResponseBody() {
    ProvinceInfoResponse provinceInfo = mockProvinceInfo();
    Mockito.when(repository.existsByCode("01")).thenReturn(true);
    Mockito.when(repository.getByCode("01")).thenReturn(provinceInfo);

    ProvinceInfoResponse response = provinceService.detail("01");
    Assertions.assertThat(provinceInfo.getProvinceName()).isEqualTo(response.getProvinceName());
    Assertions.assertThat(provinceInfo.getProvinceNameEn()).isEqualTo(response.getProvinceNameEn());
    Assertions.assertThat(provinceInfo.getProvinceCodeName()).isEqualTo(response.getProvinceCodeName());
    Assertions.assertThat(provinceInfo.getCode()).isEqualTo(response.getCode());
  }
}
