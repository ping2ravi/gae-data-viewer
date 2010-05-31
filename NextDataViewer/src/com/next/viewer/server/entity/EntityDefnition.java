package com.next.viewer.server.entity;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.next.viewer.server.entity.helper.Criteria;


@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class EntityDefnition implements Criteria{
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    @Persistent
    private String name;
    @Persistent
    private String keyField;
	public String getKeyField() {
		return keyField;
	}
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getWhereClause() {
		Map<String, String> crit = new HashMap<String, String>();
		if(name!= null && !name.trim().equals(""))
			crit.put("name", name);
		if(id!= null )
			crit.put("id", String.valueOf(id));
		return getJDOWhereClause(crit);
	}
	protected String getJDOWhereClause(Map<String, String> crit)
	{
		if(crit ==null)
			return null;
		String returnClause = null;
		for(String key:crit.keySet())
		{
			if(returnClause == null )
				returnClause = key+"=='"+crit.get(key)+"' ";
			else
				returnClause = returnClause+ " and " +key+"=='"+crit.get(key)+"' ";
		}
		return returnClause;
	}

}
