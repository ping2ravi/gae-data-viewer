package com.app.music.server.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;

import com.app.music.server.session.web.SessionObject;

public class IndexPageController extends BaseController{

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors,
			SessionObject sessionObject, ModelAndView mv) throws Exception {
		Map model = mv.getModel();
		model.put("Message", "Hell Message from onSubmit");
		model.put("view", "INDEXPAGE");
		
		return mv;
	}

	@Override
	public ModelAndView showForm(HttpServletRequest request,
			HttpServletResponse response, BindException errors,
			SessionObject sessionObject, ModelAndView mv) throws Exception {
		Map model = mv.getModel();
		model.put("Message", "Hell Message from showForm");
		model.put("view", "INDEXPAGE");
		return mv;
	}

}
