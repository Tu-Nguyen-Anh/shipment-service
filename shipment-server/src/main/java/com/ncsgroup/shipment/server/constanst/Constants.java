package com.ncsgroup.shipment.server.constanst;

public class Constants {
  private Constants() {
  }

  public static class CommonConstants {
    private CommonConstants() {
    }

    public static final String ENCODING_UTF_8 = "UTF-8";
    public static final String LANGUAGE = "Accept-Language";
    public static final String DEFAULT_LANGUAGE = "en";
    public static final String MESSAGE_SOURCE = "classpath:i18n/messages";
    public static final String DEFAULT_CODE = "";
    public static final String DEFAULT_MESSAGE = "";
    public static final String PARAM_KEYWORD = "keyword";
    public static final String PARAM_SIZE = "size";
    public static final String PARAM_PAGE = "page";
    public static final String PARAM_ALL = "all";





  }

  public static class AuditorConstant {
    private AuditorConstant() {
    }

    public static final String ANONYMOUS = "anonymousUser";
    public static final String SYSTEM = "SYSTEM";
  }

  public static class StatusException {
    private StatusException() {
    }

    public static final Integer NOT_FOUND = 404;
    public static final Integer CONFLICT = 409;
    public static final Integer BAD_REQUEST = 400;
  }

  public static class AuthConstant {
    private AuthConstant() {
    }

    public static final String TYPE_TOKEN = "Bear ";
    public static final String AUTHORIZATION = "Authorization";
  }

  public static class MessageCode {
    private MessageCode() {
    }

    public static final String CREATE_SHIPMENT_METHOD_SUCCESS = "com.ncsgroup.shipment.server.controller.ShipmentMethodController.create";
    public static final String UPDATE_SHIPMENT_METHOD_SUCCESS = "com.ncsgroup.shipment.server.controller.ShipmentMethodController.update";
    public static final String GET_SHIPMENT_METHOD_SUCCESS = "com.ncsgroup.shipment.server.controller.ShipmentMethodController.list";
    public static final String DELETE_SUCCESS = "com.ncsgroup.shipment.server.controller.ShipmentMethodController.delete";
    public static final String GET_PROVINCE_SUCCESS = "com.ncsgroup.shipment.server.controller.address.ProvinceController.list";
    public static final String GET_DISTRICT_SUCCESS = "com.ncsgroup.shipment.server.controller.address.DistrictController.list";
    public static final String GET_WARD_SUCCESS = "com.ncsgroup.shipment.server.controller.address.WardController.list";
    public static final String DETAIL_PROVINCE = "com.ncsgroup.shipment.server.controller.address.ProvinceController.detail";
    public static final String DETAIL_DISTRICT = "com.ncsgroup.shipment.server.controller.address.DistrictController.detail";
    public static final String DETAIL_WARD = "com.ncsgroup.shipment.server.controller.address.WardController.detail";
    public static final String CREATE_ADDRESS_SUCCESS = "com.ncsgroup.shipment.server.controller.address.AddressController.create";
    public static final String DETAIL_ADDRESS = "com.ncsgroup.shipment.server.controller.address.AddressController.detail";
    public static final String LIST_ADDRESS = "com.ncsgroup.shipment.server.controller.address.AddressController.list";
    public static final String UPDATE_ADDRESS = "com.ncsgroup.shipment.server.controller.address.AddressController.update";
    public static final String DELETE_ADDRESS = "com.ncsgroup.shipment.server.controller.address.AddressController.delete";
    public static final String CREATE_SHIPMENT_SUCCESS = "com.ncsgroup.shipment.server.controller.shipment.create";
    public static final String DETAIL_SHIPMENT_METHOD = "com.ncsgroup.shipment.server.controller.ShipmentMethodController.detail";
    public static final String UPDATE_SHIPMENT_SUCCESS = "com.ncsgroup.shipment.server.controller.shipment.update";
    public static final String DELETE_SHIPMENT_SUCCESS = "com.ncsgroup.shipment.server.controller.shipment.delete";
    public static final String DETAIL_SHIPMENT_SUCCESS = "com.ncsgroup.shipment.server.controller.shipment.detail";
    public static final String LIST_SHIPMENT_SUCCESS = "com.ncsgroup.shipment.server.controller.shipment.list";

  }
  public static class VariableConstants {
    private VariableConstants(){}
    public static final String SIZE_DEFAULT = "10";
    public static final String PAGE_DEFAULT = "0";
    public static final String IS_ALL_DEFAULT = "0";
  }


}
