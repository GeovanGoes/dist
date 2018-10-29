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
}
