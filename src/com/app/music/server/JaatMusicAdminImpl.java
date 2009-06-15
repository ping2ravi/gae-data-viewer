package com.app.music.server;

import com.app.music.client.JaatMusicAdmin;
import com.app.music.client.beans.FunctionBean;
import com.app.music.client.beans.PageInfoBean;
import com.app.music.client.beans.RoleBean;
import com.app.music.client.beans.SearchResultWrapper;
import com.app.music.client.exceptions.ClientException;
import com.app.music.server.exception.ApplicationException;
import com.app.music.server.paging.PageInfo;
import com.app.music.server.paging.PageResult;
import com.app.music.server.persistance.Function;
import com.app.music.server.persistance.helper.FunctionPersistance;
import com.app.music.server.persistance.helper.HelperFactory;
import com.app.music.server.session.UserServerSession;
import com.app.music.server.util.PersistanceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class JaatMusicAdminImpl extends RemoteServiceServlet implements
		JaatMusicAdmin {

	public String greetServer(SearchResultWrapper w,String input) {
		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");
		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	protected UserServerSession getUserServerSession()
	{
		UserServerSession userServerSession = new UserServerSession();
		userServerSession.setPersistenceManager(PersistanceFactory.getPersisatnceManager());
		return userServerSession;
	}
	protected void commitUserServerSession(UserServerSession userServerSession)
	{
		userServerSession.getPersistenceManager().currentTransaction().commit();
		userServerSession.getPersistenceManager().close();
	}
	protected void rollbackUserServerSession(UserServerSession userServerSession)
	{
		userServerSession.getPersistenceManager().currentTransaction().rollback();
		userServerSession.getPersistenceManager().close();
	}
	@Override
	public FunctionBean[] getFunctionAvailableForRole(String roleId)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FunctionBean[] getRoleFunctions(String roleId)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleBean[] getRolesAvailableForUser(String soeId)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleBean[] getUserRoles(String soeId) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateRoleFunctions(String soeId, Long[] functionsToAdd,
			Long[] functionsToRemove) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateUserRoles(String soeId, Long[] rolesToAdd,
			Long[] rolesToRemove) throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FunctionBean createFunction(FunctionBean newFunction)
			throws ClientException {
		
		UserServerSession userServerSession = getUserServerSession();
		try{
		FunctionPersistance functionPersistance = HelperFactory.getFunctionPersistance();
		Function func = new Function();
		func.setName(newFunction.getName());
		func.setDescription(newFunction.getDescription());
		functionPersistance.createFunction(userServerSession, func);
		System.out.println("Functuion saved is " + func.getId());
		newFunction.setId(func.getId());
		}catch(ApplicationException ex)
		{
			throw new ClientException(ex);
		}catch(Exception ex)
		{
			throw new ClientException("Internal Error occured");
		}
		return newFunction;
	}

	@Override
	public SearchResultWrapper searchFunction(FunctionBean searchCriteria,
			PageInfoBean pageInfoBean) throws ClientException {
		UserServerSession userServerSession = getUserServerSession();
		SearchResultWrapper resultWrapper = null;
		try{
			PageInfo pageInfo = BeanHelper.getPageInfo(pageInfoBean);
			FunctionPersistance functionPersistance = HelperFactory.getFunctionPersistance();
			PageResult pageResult = functionPersistance.findFunctionsEntities(userServerSession, pageInfo);
			if(pageResult != null )
			{
				resultWrapper = new SearchResultWrapper();
				//resultWrapper.setData(pageResult.getResultData());
				resultWrapper.setTotalSize(pageResult.getTotalRecords());
			}
		}catch(ApplicationException ex)
		{
			throw new ClientException(ex);
		}catch(Exception ex)
		{
			throw new ClientException("Internal Error occured");
		}
		return null;
	}

	@Override
	public FunctionBean updateFunction(FunctionBean updateFunction)
			throws ClientException {
		// TODO Auto-generated method stub
		return null;
	}
}
