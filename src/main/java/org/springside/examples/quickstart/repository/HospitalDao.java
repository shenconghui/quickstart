package org.springside.examples.quickstart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springside.examples.quickstart.entity.Hospital;

import java.util.List;

/**
 * Created by 1 on 2016/5/14.
 */
public interface HospitalDao extends PagingAndSortingRepository<Hospital, Long>,JpaSpecificationExecutor<Hospital> {

    Page<Hospital> findByUserId(Long id, Pageable pageRequest);

    @Modifying
    @Query("delete from Hospital hospital where hospital.user.id=?1")
    void deleteByUserId(Long id);
}
