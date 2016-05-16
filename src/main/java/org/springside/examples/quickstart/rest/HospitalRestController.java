package org.springside.examples.quickstart.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springside.examples.quickstart.entity.Hospital;

import org.springside.examples.quickstart.service.hospital.HospitalService;

import org.springside.modules.beanvalidator.BeanValidators;
import org.springside.modules.web.MediaTypes;

import javax.validation.Validator;
import java.net.URI;
import java.util.List;

/**
 * Created by 1 on 2016/5/15.
 */

@RestController
@RequestMapping(value = "/api/v1/hospital")
public class HospitalRestController {

    private static Logger logger = LoggerFactory.getLogger(HospitalRestController.class);

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private Validator validator;

    @RequestMapping(method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public List<Hospital> list() {
        return hospitalService.getAllHospital();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaTypes.JSON_UTF_8)
    public Hospital get(@PathVariable("id") Long id) {
        Hospital hospital = hospitalService.getHospital(id);
        if (hospital == null) {
            String message = "医院不存在(id:" + id + ")";
            logger.warn(message);
            throw new RestException(HttpStatus.NOT_FOUND, message);
        }
        return hospital;
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaTypes.JSON)
    public ResponseEntity<?> create(@RequestBody Hospital hospital, UriComponentsBuilder uriBuilder) {
        // 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
        BeanValidators.validateWithException(validator, hospital);

        // 保存医院信息
        hospitalService.saveHospital(hospital);

        // 按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
        Long id = hospital.getId();
        URI uri = uriBuilder.path("/api/v1/hospital/" + id).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaTypes.JSON)
    // 按Restful风格约定，返回204状态码, 无内容. 也可以返回200状态码.
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Hospital hospital) {
        // 调用JSR303 Bean Validator进行校验, 异常将由RestExceptionHandler统一处理.
        BeanValidators.validateWithException(validator, hospital);

        // 保存任务
        hospitalService.saveHospital(hospital);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        hospitalService.deleteHospital(id);
    }
}
