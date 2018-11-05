/**
 * 
 */
package server.dao;

import com.google.gson.Gson;
import commons.model.ListaPessoas;


/**
 * 
 *
 */
public class JsonUtil
{
	/***
	 * 
	 * @param pessoas
	 * @return
	 */
	public static String toJson(ListaPessoas pessoas)
	{
		return new Gson().toJson(pessoas);
	}
	
	/***
	 * 
	 * @param json
	 * @return
	 */
	public static ListaPessoas fromJson(String json)
	{
		return new Gson().fromJson(json, ListaPessoas.class);
	}
}
