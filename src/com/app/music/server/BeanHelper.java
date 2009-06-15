package com.app.music.server;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.app.music.client.beans.BaseBean;
import com.app.music.client.beans.PageInfoBean;
import com.app.music.server.exception.ApplicationException;
import com.app.music.server.paging.PageInfo;
import com.app.music.server.persistance.BaseObject;

public class BeanHelper {

//	public static PageInfo getPageInfo(PageInfoBean pageInfoBean,QueryCriteria criteria) throws InvalidAppException
	public static PageInfo getPageInfo(PageInfoBean pageInfoBean) throws ApplicationException	
	{
		PageInfo pageInfo = new PageInfo();
		try {
			BeanUtils.copyProperties(pageInfo, pageInfoBean);
			//pageInfo.setCriteria(criteria);
		} catch (IllegalAccessException e) {
			throw new ApplicationException(e);
		} catch (InvocationTargetException e) {
			throw new ApplicationException(e);
		}
		return pageInfo;
	}
	public static BaseObject getServerObject(BaseObject serverObject, BaseBean clientBean) throws ApplicationException	
	{
		try {
			BeanUtils.copyProperties(serverObject, clientBean);
			//pageInfo.setCriteria(criteria);
		} catch (IllegalAccessException e) {
			throw new ApplicationException(e);
		} catch (InvocationTargetException e) {
			throw new ApplicationException(e);
		}
		return serverObject;
	}
	public static BaseBean getClientBean(BaseBean clientBean, BaseObject serverObject) throws ApplicationException	
	{
		try {
			BeanUtils.copyProperties(clientBean, serverObject);
			//pageInfo.setCriteria(criteria);
		} catch (IllegalAccessException e) {
			throw new ApplicationException(e);
		} catch (InvocationTargetException e) {
			throw new ApplicationException(e);
		}
		return clientBean;
	}
	public static BaseBean[] getClientBean(BaseBean[] clientBean, BaseObject[] serverObject) throws ApplicationException	
	{
		try {
			BeanUtils.copyProperties(clientBean, serverObject);
			//pageInfo.setCriteria(criteria);
		} catch (IllegalAccessException e) {
			throw new ApplicationException(e);
		} catch (InvocationTargetException e) {
			throw new ApplicationException(e);
		}
		return clientBean;
	}
	
}
