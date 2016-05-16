package org.springside.examples.quickstart.service.hospital;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.quickstart.entity.Hospital;

import org.springside.examples.quickstart.repository.HospitalDao;

import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.utils.Clock;

import java.util.List;
import java.util.Map;

/**
 * Created by 1 on 2016/5/14.
 */
@Component
@Transactional
public class HospitalService {
    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;

    private static Logger logger = LoggerFactory.getLogger(HospitalService.class);

    private HospitalDao hospitalDao;
    private Clock clock = Clock.DEFAULT;

    public void saveHospital(Hospital entity) {
        hospitalDao.save(entity);
    }

    public void deleteHospital(Long id) {
        hospitalDao.delete(id);
    }

    public List<Hospital> getAllHospital() {
        return (List<Hospital>) hospitalDao.findAll();
    }

    public Hospital getHospital(Long id) {

        return hospitalDao.findOne(id);
    }
    public Page<Hospital> getUserHospital(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
                                  String sortType) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
        Specification<Hospital> spec = buildSpecification(userId, searchParams);
        return hospitalDao.findAll(spec, pageRequest);
    }

    /**
     * 创建分页请求.
     */
    private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
        Sort sort = null;
        if ("auto".equals(sortType)) {
            sort = new Sort(Sort.Direction.DESC, "id");
        } else if ("name".equals(sortType)) {
            sort = new Sort(Sort.Direction.ASC, "name");
        }

        return new PageRequest(pageNumber - 1, pagzSize, sort);
    }

    /**
     * 创建动态查询条件组合.
     */
    private Specification<Hospital> buildSpecification(Long userId, Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        filters.put("user.id", new SearchFilter("user.id", SearchFilter.Operator.EQ, userId));
        Specification<Hospital> spec = DynamicSpecifications.bySearchFilter(filters.values(), Hospital.class);
        return spec;
    }

    @Autowired
    public void setHospitalDao(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }
}



