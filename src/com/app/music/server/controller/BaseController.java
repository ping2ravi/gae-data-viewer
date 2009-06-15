package com.app.music.server.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.app.music.server.session.web.SessionObject;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public abstract class BaseController extends SimpleFormController{

	private static final String SESSION_OBJECT = "SESSION_OBJECT";
    protected final ModelAndView showForm(HttpServletRequest request,
                                    HttpServletResponse response,
                                    BindException errors)
			throws Exception{
    	UserService userService = UserServiceFactory.getUserService();
        String thisURL = request.getRequestURI();
        String loginUrl = userService.createLoginURL(thisURL);

        ModelAndView mv =  super.showForm(request, response, errors, null);
        /*if(request.getUserPrincipal() == null) {
            mv.setView(new RedirectView(loginUrl));
            return mv;
        }*/
        //start Persisatnce Manager
        SessionObject sessionObject = null;
        mv = this.showForm(request, response, errors, sessionObject, mv);
        Map model = mv.getModel();
        if(request.getUserPrincipal() == null) {
            model.put("loggedId", false);
            model.put("userName", "guest");
            model.put("userEmail", "guest");
            model.put("loginUrl", loginUrl);
        }else
        {
            model.put("loggedId", true);
            model.put("userName", userService.getCurrentUser().getNickname());
            model.put("userEmail", userService.getCurrentUser().getEmail());
            model.put("logoutUrl", userService.createLogoutURL(thisURL));
        }
        //Close/commit Persisatnce Manager
        
        return mv;
    }
    protected final ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors)
			throws Exception {
        ModelAndView mv =  super.showForm(request, response, errors, null);
        //start Persisatnce Manager
        SessionObject sessionObject = null;
        mv = this.onSubmit(request, response, command, errors, sessionObject, mv);
        //Close/commit Persisatnce Manager
        return mv;

    }
    protected SessionObject getSessionObject(HttpServletRequest request)
    {
    	SessionObject sessionObject = (SessionObject)request.getAttribute(SESSION_OBJECT);
    	if(sessionObject == null)
    		sessionObject = new SessionObject();
    	UserService userService = UserServiceFactory.getUserService();

        if(request.getUserPrincipal() != null) 
        {
        	sessionObject.setUserNickName(userService.getCurrentUser().getNickname());
        	sessionObject.setUserEmail(userService.getCurrentUser().getEmail());
        }
        else
        {
        	sessionObject.setUserNickName("guest");
        	sessionObject.setUserEmail("guest");
        }
        return sessionObject;
    }
    
    public abstract ModelAndView showForm(HttpServletRequest request,
                                    HttpServletResponse response,
                                    BindException errors,
                                    SessionObject sessionObject,
                                    ModelAndView mv)
			throws Exception;
    protected abstract ModelAndView onSubmit(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Object command,
                                    BindException errors,
                                    SessionObject sessionObject,
                                    ModelAndView mv)
			throws Exception;


}
