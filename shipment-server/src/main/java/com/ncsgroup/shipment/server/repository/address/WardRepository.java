package com.ncsgroup.shipment.server.repository.address;

import com.ncsgroup.shipment.server.dto.address.ward.WardInfoResponse;
import com.ncsgroup.shipment.server.dto.address.ward.WardResponse;
import com.ncsgroup.shipment.server.entity.address.Ward;
import com.ncsgroup.shipment.server.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;


public interface WardRepository extends BaseRepository<Ward> {
  boolean existsByCode(String code);

  @Query("""
        SELECT new com.ncsgroup.shipment.server.dto.address.ward.WardInfoResponse
            (w.name, w.nameEn, w.codeName, w.code)
        FROM Ward w
        WHERE w.code = :code
             """)
  WardInfoResponse getByCode(String code);


  @Query("""
            SELECT new com.ncsgroup.shipment.server.dto.address.ward.WardResponse
                (w.code, w.name, w.nameEn, w.fullName, w.fullNameEn, w.codeName)
            FROM Ward w
            WHERE (:districtCode is null or w.districtCode = :districtCode)
            ORDER BY w.name
        """)
  Page<WardResponse> findAllWard(String districtCode, Pageable pageable);

  @Query("""
            SELECT new com.ncsgroup.shipment.server.dto.address.ward.WardResponse
                (w.code, w.name, w.nameEn, w.fullName, w.fullNameEn, w.codeName)
            FROM Ward w
            WHERE (:keyword is null or (
                lower(w.name) LIKE lower(concat('%', :keyword, '%')) OR
                lower(w.nameEn) LIKE lower(concat('%', :keyword, '%')) OR
                lower(w.fullName) LIKE lower(concat('%', :keyword, '%')) OR
                lower(w.fullNameEn) LIKE lower(concat('%', :keyword, '%')) OR
                lower(w.codeName) LIKE lower(concat('%', :keyword, '%'))
            )) 
            AND (:districtCode is null or w.districtCode = :districtCode)
            ORDER BY w.name
        """)
  Page<WardResponse> searchWard(String keyword, String districtCode, Pageable pageable);
}
