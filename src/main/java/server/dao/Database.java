/**
 * 
 */
package server.dao;

import commons.model.Pessoa;

import java.util.List;

/**
 * @author geovan.goes
 *
 */
public interface Database
{

	boolean create(Pessoa pessoa);
	
	List<Pessoa> read();
	
	boolean update (Pessoa pessoa);
	
	boolean delete (long id);
}
