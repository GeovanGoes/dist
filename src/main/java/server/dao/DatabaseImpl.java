/**
 * 
 */
package server.dao;

import commons.model.ListaPessoas;
import commons.model.Pessoa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author geovan.goes
 *
 */
public class DatabaseImpl implements Database
{
	
	private int databaseRotate = 1;
	

	
	private void rotateDatabase()
	{
		if (databaseRotate == 3)
			databaseRotate = 1;
		else
			databaseRotate++;
	}
	
	/* (non-Javadoc)
	 * @see server.dao.Database#create(commons.model.Pessoa)
	 */
	@Override
	public boolean create(Pessoa pessoa)
	{
		try
		{
			inserir(pessoa);
			rotateDatabase();
			inserir(pessoa);
			return true;
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * @param pessoa
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void inserir(Pessoa pessoa) throws FileNotFoundException, IOException
	{
		StringBuffer conteudo = FileUtil.obterConteudoDeUmArquivo(databaseRotate + ".json");
		ListaPessoas fromJson = JsonUtil.fromJson(conteudo.toString());
		
		if (fromJson != null && fromJson.getPessoas() != null)
		{
			fromJson.getPessoas().add(pessoa);
			String json = JsonUtil.toJson(fromJson);
			
			FileUtil.gerarArquivo(databaseRotate + StorageConstants.JSON_EXT, json);
		}
		else
		{
			List<Pessoa> list = new ArrayList<>();
			list.add(pessoa);
			ListaPessoas listaPessoas = new ListaPessoas(list);
			
			String json = JsonUtil.toJson(listaPessoas);
			
			FileUtil.gerarArquivo(databaseRotate + StorageConstants.JSON_EXT, json);
		}
	}

	/* (non-Javadoc)
	 * @see server.dao.Database#read()
	 */
	@Override
	public List<Pessoa> read()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see server.dao.Database#update(commons.model.Pessoa)
	 */
	@Override
	public boolean update(Pessoa pessoa)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see server.dao.Database#delete(long)
	 */
	@Override
	public boolean delete(long id)
	{
		// TODO Auto-generated method stub
		return false;
	}

}
