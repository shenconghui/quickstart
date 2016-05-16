/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package org.springside.examples.quickstart.web.hospital;

import com.google.common.collect.Maps;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.examples.quickstart.entity.Hospital;

import org.springside.examples.quickstart.entity.User;
import org.springside.examples.quickstart.service.account.ShiroDbRealm.ShiroUser;
import org.springside.examples.quickstart.service.hospital.HospitalService;

import org.springside.modules.web.Servlets;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * Hospital管理的Controller, 使用Restful风格的Urls:
 * 
 * List page : GET /hospital/
 * Create page : GET /hospital/create
 * Create action : POST /hospital/create
 * Update page : GET /hospital/update/{id}
 * Update action : POST /hospital/update
 * Delete action : GET /hospital/delete/{id}
 * 
 * @author shenconghui
 */
@Controller
@RequestMapping(value = "/hospital")
public class HospitalController {

	private static final String PAGE_SIZE = "5";

	private static Map<String, String> sortTypes = Maps.newLinkedHashMap();
	static {
		sortTypes.put("auto", "自动");
		sortTypes.put("name", "医院名称");
	}

	@Autowired
	private HospitalService hospitalService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(@RequestParam(value = "page", defaultValue = "1") int pageNumber,
			@RequestParam(value = "page.size", defaultValue = PAGE_SIZE) int pageSize,
			@RequestParam(value = "sortType", defaultValue = "auto") String sortType, Model model,
			ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		Long userId = getCurrentUserId();

		Page<Hospital> hospitals = hospitalService.getUserHospital(userId, searchParams, pageNumber, pageSize, sortType);

		model.addAttribute("hospitals", hospitals);
		model.addAttribute("sortType", sortType);
		model.addAttribute("sortTypes", sortTypes);
		// 将搜索条件编码成字符串，用于排序，分页的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "hospital/hospitalList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("hospital", new Hospital());
		model.addAttribute("action", "create");
		return "hospital/hospitalForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Hospital newHospital, RedirectAttributes redirectAttributes) {
		User user = new User(getCurrentUserId());
		newHospital.setUser(user);

		hospitalService.saveHospital(newHospital);
		redirectAttributes.addFlashAttribute("message", "新增医院信息成功");
		return "redirect:/hospital/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("hospital", hospitalService.getHospital(id));
		model.addAttribute("action", "update");
		return "hospital/hospitalForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("hospital") Hospital hospital, RedirectAttributes redirectAttributes) {
		hospitalService.saveHospital(hospital);
		redirectAttributes.addFlashAttribute("message", "更新医院信息成功");
		return "redirect:/hospital/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		hospitalService.deleteHospital(id);
		redirectAttributes.addFlashAttribute("message", "删除医院信息成功");
		return "redirect:/hospital/";
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出Task对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getHospital(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("hospital", hospitalService.getHospital(id));
		}
	}

	/**
	 * 取出Shiro中的当前用户Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
}
