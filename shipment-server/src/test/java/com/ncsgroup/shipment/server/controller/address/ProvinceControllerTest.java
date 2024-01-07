package com.ncsgroup.shipment.server.controller.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncsgroup.shipment.server.dto.PageResponse;
import com.ncsgroup.shipment.server.dto.address.province.ProvinceInfoResponse;
import com.ncsgroup.shipment.server.dto.address.province.ProvinceResponse;
import com.ncsgroup.shipment.server.exception.address.AddressNotFoundException;
import com.ncsgroup.shipment.server.service.MessageService;
import com.ncsgroup.shipment.server.service.address.ProvinceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static com.ncsgroup.shipment.server.constanst.Constants.MessageCode.DETAIL_PROVINCE;
import static com.ncsgroup.shipment.server.constanst.Constants.MessageCode.GET_PROVINCE_SUCCESS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProvinceController.class)
class ProvinceControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private ProvinceService provinceService;
  @MockBean
  private MessageService messageService;
  @Autowired
  private ProvinceController provinceController;
  @Autowired
  ObjectMapper objectMapper;

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
  void testList_WhenSearchByKeyWordAllFalse_Return200Body() throws Exception {
    ProvinceResponse mockProvinceResponse = mockProvinceResponse();

    List<ProvinceResponse> list = new ArrayList<>();
    list.add(mockProvinceResponse);

    PageResponse<ProvinceResponse> mockPageResponse = new PageResponse<>();
    mockPageResponse.setContent(list);
    mockPageResponse.setAmount(list.size());

    Mockito.when(messageService.getMessage(GET_PROVINCE_SUCCESS, "en")).thenReturn("Get detail success");
    Mockito.when(provinceService.list("02", 10, 0, false)).thenReturn(mockPageResponse);

    MvcResult mvcResult = mockMvc.perform(get("/api/v1/provinces")
                .param("keyword", "02")
                .param("size", String.valueOf(10))
                .param("page", String.valueOf(0))
                .param("all", String.valueOf(false)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.message").value("Get detail success"))
          .andDo(print())
          .andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();
    Assertions.assertEquals(responseBody,
          objectMapper.writeValueAsString(provinceController.list("02", 10, 0, false, "en")));
  }

  @Test
  void testList_WhenListIsAll_Returns200AndBody() throws Exception {
    ProvinceResponse mockProvinceResponse = mockProvinceResponse();

    List<ProvinceResponse> list = new ArrayList<>();
    list.add(mockProvinceResponse);

    PageResponse<ProvinceResponse> mockPageResponse = new PageResponse<>();
    mockPageResponse.setContent(list);
    mockPageResponse.setAmount(list.size());

    Mockito.when(messageService.getMessage(GET_PROVINCE_SUCCESS, "en")).thenReturn("Get detail success");
    Mockito.when(provinceService.list("", 10, 0, true)).thenReturn(mockPageResponse);

    MvcResult mvcResult = mockMvc.perform(get("/api/v1/provinces")
                .param("keyword", "")
                .param("size", String.valueOf(10))
                .param("page", String.valueOf(0))
                .param("all", String.valueOf(true)))
          .andExpect(status().isOk())
          .andDo(print())
          .andExpect(jsonPath("$.message").value("Get detail success"))
          .andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();
    Assertions.assertEquals(responseBody,
          objectMapper.writeValueAsString(provinceController.list("", 10, 0, true, "en")));
  }

  @Test
  void testDetail_WhenCodeNotFound_ReturnProvinceNotFoundException() throws Exception {
    Mockito.when(provinceService.detail("ok")).thenThrow(new AddressNotFoundException(true, false, false));
    mockMvc.perform(
                get("/api/v1/provinces/details/{code}", "ok")
                      .contentType("application/json"))
          .andExpect(status().isNotFound())
          .andExpect(jsonPath("$.data.code")
                .value("com.ncsgroup.shipment.server.exception.address.AddressNotFoundException.Province"));
  }

  @Test
  void testDetails_WhenCodeExists_ReturnProvinceDetails() throws Exception {
    ProvinceInfoResponse mockInfoResponse = mockProvinceInfo();

    Mockito.when(provinceService.detail("01")).thenReturn(mockInfoResponse);
    Mockito.when(messageService.getMessage(DETAIL_PROVINCE, "en")).thenReturn("Get detail success");

    MvcResult mvcResult = mockMvc.perform(
                get("/api/v1/provinces/details/{code}", "01")
                      .contentType("application/json"))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.message").value("Get detail success"))
          .andReturn();

    String responseBody = mvcResult.getResponse().getContentAsString();
    Assertions.assertEquals(responseBody,
          objectMapper.writeValueAsString(provinceController.detail("01", "en")));
  }

}
