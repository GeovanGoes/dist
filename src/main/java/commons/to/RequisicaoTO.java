package commons.to;
import commons.enumation.OperationType;
import commons.model.Pessoa;

import java.io.Serializable;

/**
 * 
 */

/**
 * 
 *
 */
public class RequisicaoTO implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5617632220617288358L;
	private OperationType operation;
	private Pessoa pessoa;

	/**
	 * 
	 */
	public RequisicaoTO(OperationType type, Pessoa pessoa)
	{
		this.pessoa = pessoa;
		this.operation = type;
	}

	/**
	 * @return the operation
	 */
	public OperationType getOperation()
	{
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(OperationType operation)
	{
		this.operation = operation;
	}

	/**
	 * @return the pessoa
	 */
	public Pessoa getPessoa()
	{
		return pessoa;
	}

	/**
	 * @param pessoa the pessoa to set
	 */
	public void setPessoa(Pessoa pessoa)
	{
		this.pessoa = pessoa;
	}
	
	
}
