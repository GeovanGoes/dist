package commons.model;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 */

/**
 * @author geovan.goes
 *
 */
public class Pessoa implements Serializable
{
	
	private long id;
	private String nome;
	private String email;
	private Date cricao;
	private Date update;
	
	
	
	public Pessoa(long id, String nome, String email, Date cricao, Date update)
	{
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cricao = cricao;
		this.update = update;
	}


	public Pessoa(String nome, String email, Date cricao, Date update)
	{
		super();
		this.nome = nome;
		this.email = email;
		this.cricao = cricao;
		this.update = update;
	}


	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}


	/**
	 * @return the nome
	 */
	public String getNome()
	{
		return nome;
	}


	/**
	 * @param nome the nome to set
	 */
	public void setNome(String nome)
	{
		this.nome = nome;
	}


	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}


	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}


	/**
	 * @return the cricao
	 */
	public Date getCricao()
	{
		return cricao;
	}


	/**
	 * @param cricao the cricao to set
	 */
	public void setCricao(Date cricao)
	{
		this.cricao = cricao;
	}


	/**
	 * @return the update
	 */
	public Date getUpdate()
	{
		return update;
	}


	/**
	 * @param update the update to set
	 */
	public void setUpdate(Date update)
	{
		this.update = update;
	}
	
	
}
