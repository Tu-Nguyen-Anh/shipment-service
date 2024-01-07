package com.ncsgroup.shipment.server.repository.address;

import com.ncsgroup.shipment.server.dto.address.district.DistrictInfoResponse;
import com.ncsgroup.shipment.server.dto.address.district.DistrictResponse;
import com.ncsgroup.shipment.server.entity.address.District;
import com.ncsgroup.shipment.server.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DistrictRepository extends BaseRepository<District> {
  boolean existsByCode(String code);
  @Query("""
        SELECT new com.ncsgroup.shipment.server.dto.address.district.DistrictInfoResponse
        (d.name, d.nameEn, d.codeName, d.code)
        FROM District d
        WHERE d.code = :code
        """)
  DistrictInfoResponse getByCode(@Param("code") String code);


  @Query("""
        SELECT new com.ncsgroup.shipment.server.dto.address.district.DistrictResponse (
            d.code, d.name, d.nameEn, d.fullName, d.fullNameEn, d.codeName
        )
        FROM District d
        WHERE (:provinceCode IS NULL OR d.provinceCode = :provinceCode)
        ORDER BY d.name
        """)
Page<DistrictResponse> findAllDistrict(@Param("provinceCode") String provinceCode, Pageable pageable);

  @Query("""
        SELECT new com.ncsgroup.shipment.server.dto.address.district.DistrictResponse (
            d.code, d.name, d.nameEn, d.fullName, d.fullNameEn, d.codeName
        )
        FROM District d
        WHERE (:keyword IS NULL OR (
            lower(d.name) LIKE lower(concat('%', :keyword, '%')) OR
            lower(d.nameEn) LIKE lower(concat('%', :keyword, '%')) OR
            lower(d.fullName) LIKE lower(concat('%', :keyword, '%')) OR
            lower(d.fullNameEn) LIKE lower(concat('%', :keyword, '%')) OR
            lower(d.codeName) LIKE lower(concat('%', :keyword, '%'))
        ))
        AND (:provinceCode IS NULL OR d.provinceCode = :provinceCode)
        ORDER BY d.name
        """)
  Page<DistrictResponse> searchDistrict(
        @Param("keyword") String keyword,
        @Param("provinceCode") String provinceCode,
        Pageable pageable
  );

}
