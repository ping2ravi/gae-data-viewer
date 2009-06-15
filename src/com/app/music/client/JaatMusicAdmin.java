package com.app.music.client;

import com.app.music.client.beans.FunctionBean;
import com.app.music.client.beans.RoleBean;
import com.app.music.client.exceptions.ClientException;
import com.app.music.client.beans.SearchResultWrapper;
import com.app.music.client.beans.PageInfoBean;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface JaatMusicAdmin extends RemoteService {
	String greetServer(SearchResultWrapper w, String name);
	public FunctionBean createFunction(FunctionBean newUser) throws ClientException;
	public FunctionBean updateFunction(FunctionBean updateFunction) throws ClientException;
	public SearchResultWrapper searchFunction(FunctionBean searchCriteria,PageInfoBean pageInfo) throws ClientException;
	public FunctionBean[] getRoleFunctions(String roleId) throws ClientException;
	public FunctionBean[] getFunctionAvailableForRole(String roleId) throws ClientException;
	public String updateRoleFunctions(String soeId,Long[] functionsToAdd,Long[] functionsToRemove) throws ClientException;

	public RoleBean[] getUserRoles(String soeId) throws ClientException;
	public RoleBean[] getRolesAvailableForUser(String soeId) throws ClientException;
	public String updateUserRoles(String soeId,Long[] rolesToAdd,Long[] rolesToRemove) throws ClientException;
}
