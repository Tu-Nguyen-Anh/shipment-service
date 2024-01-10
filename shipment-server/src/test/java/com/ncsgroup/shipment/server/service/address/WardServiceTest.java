package com.ncsgroup.shipment.server.service.address;

import com.ncsgroup.shipment.server.configuration.ShipmentTestConfiguration;
import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.address.ward.WardInfoResponse;
import com.ncsgroup.shipment.server.dto.address.ward.WardResponse;
import com.ncsgroup.shipment.server.exception.address.AddressNotFoundException;
import com.ncsgroup.shipment.server.repository.address.WardRepository;
import com.ncsgroup.shipment.client.dto.address.SearchWardRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@WebMvcTest(WardService.class)
@ContextConfiguration(classes = ShipmentTestConfiguration.class)
class WardServiceTest {
  @MockBean
  private WardRepository repository;
  @Autowired
  private WardService wardService;

  private SearchWardRequest mockSearchRequest(String keyword, String districtCode) {
    return new SearchWardRequest(keyword, districtCode);
  }

  private WardResponse wardResponse() {
    WardResponse mockWardResponse = new WardResponse();
    mockWardResponse.setCode("10801");
    mockWardResponse.setName("Tam Ky");
    mockWardResponse.setNameEn("Tam Ky");
    mockWardResponse.setFullName("xa Tam Ky");
    mockWardResponse.setFullNameEn("Tam Ky ward");
    mockWardResponse.setCodeName("tam_ky");
    return mockWardResponse;
  }

  private WardInfoResponse mockWardInfo() {
    WardInfoResponse mockWarInfoResponse = new WardInfoResponse();
    mockWarInfoResponse.setCode("10801");
    mockWarInfoResponse.setWardName("Tam Ky");
    mockWarInfoResponse.setWardNameEn("Tam Ky");
    mockWarInfoResponse.setWardCodeName("tam_ky");
    return mockWarInfoResponse;
  }

  @Test
  void testList_WhenAllFalseAndExistDistrictCode_ReturnDistrictPageResponse() {
    SearchWardRequest mockSearch = mockSearchRequest(null, "294");
    WardResponse mockResponse = wardResponse();

    List<WardResponse> list = new ArrayList<>();
    list.add(mockResponse);
    Page<WardResponse> page = new PageImpl<>(list);

    Pageable pageable = PageRequest.of(0, 10);

    Mockito.when(repository.findAllWard("294", pageable)).thenReturn(page);

    PageResponse<WardResponse> response = wardService.search(mockSearch, 10, 0, true);
    assertThat(list).hasSize(response.getAmount());
  }

  @Test
  void testList_WhenAllTrue_ReturnWardPageResponse() {
    WardResponse mockResponse = wardResponse();
    List<WardResponse> list = new ArrayList<>();
    list.add(mockResponse);

    Page<WardResponse> page = new PageImpl<>(list);

    Pageable pageable = PageRequest.of(0, 10);

    Mockito.when(repository.findAllWard(null, pageable)).thenReturn(page);

    PageResponse<WardResponse> response = wardService.search(null, 10, 0, true);
    assertThat(list).hasSize(response.getAmount());
  }

  @Test
  void testList_WhenAllFalseAndSearchByRequest_ReturnWardPageResponse() {
    Pageable pageable = PageRequest.of(0, 10);
    SearchWardRequest mockSearch = mockSearchRequest("tam_ky", "293");
    WardResponse mockResponse = wardResponse();

    List<WardResponse> list = new ArrayList<>();
    list.add(mockResponse);
    Page<WardResponse> page = new PageImpl<>(list);

    Mockito.when(repository.searchWard("tam_ky", "293", pageable)).thenReturn(page);

    PageResponse<WardResponse> response = wardService.search(mockSearch, 10, 0, false);
    assertThat(list).hasSize(response.getAmount());
  }

  @Test
  void testDetail_WhenCodeNotFound_ReturnsWardNotFoundException() {
    Mockito.when(repository.existsByCode("123")).thenReturn(false);
    Assertions.assertThatThrownBy(() -> wardService.detail("123")).isInstanceOf(AddressNotFoundException.class);
  }

  @Test
  void testDetail_WhenGetSuccess_ReturnResponseBody() {
    WardInfoResponse wardInfo = mockWardInfo();

    Mockito.when(repository.existsByCode("10081")).thenReturn(true);
    Mockito.when(repository.getByCode("10081")).thenReturn(wardInfo);

    WardInfoResponse response = wardService.detail("10081");
    Assertions.assertThat(wardInfo.getWardName()).isEqualTo(response.getWardName());
    Assertions.assertThat(wardInfo.getWardNameEn()).isEqualTo(response.getWardNameEn());
    Assertions.assertThat(wardInfo.getWardCodeName()).isEqualTo(response.getWardCodeName());
    Assertions.assertThat(wardInfo.getCode()).isEqualTo(response.getCode());
  }

}
