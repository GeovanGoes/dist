package commons.to;
import commons.model.Pessoa;

import java.io.Serializable;
import java.util.List;

public class ResponseTO implements Serializable
{
	private boolean success;
	private String message;
	private List<Pessoa> pessoas;
	public ResponseTO(boolean success, String message, List<Pessoa> pessoas)
	{
		super();
		this.success = success;
		this.message = message;
		this.pessoas = pessoas;
	}
	/**
	 * @return the success
	 */
	public boolean isSuccess()
	{
		return success;
	}
	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success)
	{
		this.success = success;
	}
	/**
	 * @return the message
	 */
	public String getMessage()
	{
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}
	/**
	 * @return the pessoas
	 */
	public List<Pessoa> getPessoas()
	{
		return pessoas;
	}
	/**
	 * @param pessoas the pessoas to set
	 */
	public void setPessoas(List<Pessoa> pessoas)
	{
		this.pessoas = pessoas;
	}
}
