package com.ncsgroup.shipment.server.service.address;

import com.ncsgroup.shipment.server.configuration.ShipmentTestConfiguration;
import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.address.district.DistrictInfoResponse;
import com.ncsgroup.shipment.server.dto.address.district.DistrictResponse;
import com.ncsgroup.shipment.server.entity.address.District;
import com.ncsgroup.shipment.server.exception.address.AddressNotFoundException;
import com.ncsgroup.shipment.server.repository.address.DistrictRepository;
import com.ncsgroup.shipment.client.dto.address.SearchDistrictRequest;
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

@WebMvcTest(DistrictService.class)
@ContextConfiguration(classes = ShipmentTestConfiguration.class)
class DistrictServiceTest {
  @MockBean
  private DistrictRepository repository;
  @Autowired
  private DistrictService districtService;

  private District mockDistrict() {
    District mockEntity = new District();
    mockEntity.setCode("293");
    mockEntity.setName("Kim Thanh");
    mockEntity.setNameEn("Kim Thanh");
    mockEntity.setFullName("Huyen Kim Thanh");
    mockEntity.setFullNameEn("Kim Thanh District");
    mockEntity.setCodeName("kim_thanh");
    mockEntity.setProvinceCode("30");
    return mockEntity;
  }

  private SearchDistrictRequest mockSearchRequest(String keyword, String provinceCode) {
    return new SearchDistrictRequest(keyword, provinceCode);
  }

  private DistrictResponse mockDistrictResponse(District district) {
    return new DistrictResponse(
          district.getCode(),
          district.getName(),
          district.getNameEn(),
          district.getFullName(),
          district.getFullNameEn(),
          district.getCodeName()
    );
  }

  private DistrictInfoResponse mockDistrictInfo(District district) {
    return new DistrictInfoResponse(
          district.getName(),
          district.getNameEn(),
          district.getCodeName(),
          district.getCode());
  }

  @Test
  void testList_WhenAllFalseAndExistProvinceCode_ReturnDistrictPageResponse() {
    SearchDistrictRequest mockSearch = mockSearchRequest(null, "30");
    District mockEntity = mockDistrict();

    DistrictResponse mockResponse = mockDistrictResponse(mockEntity);
    List<DistrictResponse> list = new ArrayList<>();
    list.add(mockResponse);

    Page<DistrictResponse> page = new PageImpl<>(list);
    Pageable pageable = PageRequest.of(0,10);
    Mockito.when(repository.searchDistrict(null,"30",pageable)).thenReturn(page);

    PageResponse<DistrictResponse> response = districtService.search(mockSearch, 10, 0, false);
    assertThat(list).hasSize(response.getAmount());
  }

  @Test
  void testList_WhenAllTrue_ReturnDistrictPageResponse() {
    District mockEntity = mockDistrict();

    DistrictResponse mockResponse = mockDistrictResponse(mockEntity);
    List<DistrictResponse> list = new ArrayList<>();
    list.add(mockResponse);

    Page<DistrictResponse> page = new PageImpl<>(list);
    Pageable pageable = PageRequest.of(0,10);
    Mockito.when(repository.findAllDistrict(null,pageable)).thenReturn(page);

    PageResponse<DistrictResponse> response = districtService.search(null, 10, 0, true);
    assertThat(list).hasSize(response.getAmount());
  }

  @Test
  void testList_WhenAllFalse_ReturnDistrictPageResponse() {
    SearchDistrictRequest mockSearch = mockSearchRequest("kim_thanh", "40");
    District mockEntity = mockDistrict();

    DistrictResponse mockResponse = mockDistrictResponse(mockEntity);
    List<DistrictResponse> list = new ArrayList<>();
    list.add(mockResponse);

    Page<DistrictResponse> page = new PageImpl<>(list);
    Pageable pageable = PageRequest.of(0,10);
    Mockito.when(repository.searchDistrict("kim_thanh","40",pageable)).thenReturn(page);

    PageResponse<DistrictResponse> response = districtService.search(mockSearch, 10, 0, false);
    assertThat(list).hasSize(response.getAmount());
  }

  @Test
  void testDetail_WhenCodeNotFound_ReturnsDistrictNotFoundException() {
    Mockito.when(repository.existsByCode("123")).thenReturn(false);
    Assertions.assertThatThrownBy(() -> districtService.detail("123")).isInstanceOf(AddressNotFoundException.class);
  }

  @Test
  void testDetail_WhenGetSuccess_ReturnResponseBody() {
    District mockEntity = mockDistrict();
    DistrictInfoResponse districtInfo = mockDistrictInfo(mockEntity);

    Mockito.when(repository.existsByCode("01")).thenReturn(true);
    Mockito.when(repository.getByCode("01")).thenReturn(districtInfo);

    DistrictInfoResponse response = districtService.detail("01");
    Assertions.assertThat(mockEntity.getName()).isEqualTo(response.getDistrictName());
    Assertions.assertThat(mockEntity.getNameEn()).isEqualTo(response.getDistrictNameEn());
    Assertions.assertThat(mockEntity.getCodeName()).isEqualTo(response.getDistrictCodeName());
    Assertions.assertThat(mockEntity.getCode()).isEqualTo(response.getCode());
  }
}
