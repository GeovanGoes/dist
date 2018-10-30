/**
 * 
 */
package commons.model;

import java.io.Serializable;
import java.util.List;


public class ListaPessoas implements Serializable
{
	private List<Pessoa> pessoas;

	public ListaPessoas(List<Pessoa> pessoas)
	{
		super();
		this.pessoas = pessoas;
	}

	/**
	 * @return the pessoas
	 */
	public List<Pessoa> getPessoas()
	{
		return pessoas;
	}
}
