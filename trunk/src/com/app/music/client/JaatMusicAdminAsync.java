package com.app.music.client;

import com.app.music.client.beans.FunctionBean;
import com.app.music.client.beans.RoleBean;
import com.app.music.client.beans.PageInfoBean;
import com.app.music.client.beans.SearchResultWrapper;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface JaatMusicAdminAsync {
	void greetServer(SearchResultWrapper w,String input, AsyncCallback<String> callback);
	
	
	public void createFunction(FunctionBean newUser,AsyncCallback<FunctionBean> callback);
	public void updateFunction(FunctionBean updateFunction,AsyncCallback<FunctionBean> callback);
	public void searchFunction(FunctionBean searchCriteria,PageInfoBean pageInfo,AsyncCallback<SearchResultWrapper> callback);
	public void getRoleFunctions(String roleId,AsyncCallback<FunctionBean[]> callback);
	public void getFunctionAvailableForRole(String roleId,AsyncCallback<FunctionBean[]> callback);
	public void updateRoleFunctions(String soeId,Long[] functionsToAdd,Long[] functionsToRemove,AsyncCallback<String> callback);

	public void getUserRoles(String soeId,AsyncCallback<RoleBean[]> callback);
	public void getRolesAvailableForUser(String soeId,AsyncCallback<RoleBean[]> callback);
	public void updateUserRoles(String soeId,Long[] rolesToAdd,Long[] rolesToRemove,AsyncCallback<String> callback);
}
