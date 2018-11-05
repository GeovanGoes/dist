/**
 * 
 */
package server.dao;

import commons.model.ListaPessoas;
import commons.model.Pessoa;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.dao.Database#create(commons.model.Pessoa)
	 */
	@Override
	public boolean create(Pessoa pessoa)
	{
		try
		{
			pessoa.setId(System.currentTimeMillis());
			inserir(pessoa);
			rotateDatabase();
			inserir(pessoa);
			return true;
		}
		catch (IOException e)
		{
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.dao.Database#read()
	 */
	@Override
	public List<Pessoa> read()
	{
		return obterListaUnicaDaBase();
	}

	/***
	 * 
	 * @param id
	 * @return
	 */
	public Pessoa readById(long id)
	{
		for (Pessoa pessoa : read())
			if (pessoa.getId() == id)
				return pessoa;
		return null;
	}

	/***
	 * 
	 * @return
	 */
	private List<Pessoa> obterListaUnicaDaBase()
	{
		Set<Pessoa> uniqueHash = new HashSet<Pessoa>();

		uniqueHash.addAll(obterDadosDaBase(StorageConstants.BASE_UM));
		uniqueHash.addAll(obterDadosDaBase(StorageConstants.BASE_DOIS));
		uniqueHash.addAll(obterDadosDaBase(StorageConstants.BASE_TRES));

		List<Pessoa> lista = new ArrayList<Pessoa>();
		lista.addAll(uniqueHash);

		return lista;
	}

	/***
	 * 
	 * @param nomeBase
	 * @return
	 */
	private List<Pessoa> obterDadosDaBase(String nomeBase)
	{
		List<Pessoa> lista = new ArrayList<Pessoa>();
		StringBuffer contentBase;
		try
		{
			contentBase = FileUtil.obterConteudoDeUmArquivo(nomeBase);
			ListaPessoas resultList = JsonUtil.fromJson(contentBase.toString());

			if (resultList != null && resultList.getPessoas() != null)
				lista = resultList.getPessoas();
		}
		catch (IOException e)
		{
			System.out.println("a base " + nomeBase + " nao foi encontrada.");
			e.printStackTrace();
		}
		return lista;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.dao.Database#update(commons.model.Pessoa)
	 */
	@Override
	public boolean update(Pessoa pessoa)
	{
		updatePessoaOnAllBases(pessoa);
		return true;
	}

	/***
	 * 
	 * @param pessoa
	 */
	private void updatePessoaOnAllBases(Pessoa pessoa)
	{
		updatePessoaOnBase(pessoa, StorageConstants.BASE_UM);
		updatePessoaOnBase(pessoa, StorageConstants.BASE_DOIS);
		updatePessoaOnBase(pessoa, StorageConstants.BASE_TRES);
	}

	/***
	 * 
	 * @param pessoa
	 * @param nomeBase
	 */
	private void updatePessoaOnBase(Pessoa pessoa, String nomeBase)
	{
		List<Pessoa> listaPessoa = obterDadosDaBase(nomeBase);

		listaPessoa.forEach(pessoaFrom ->
		{
			if (pessoa.getId() == pessoaFrom.getId())
			{
				pessoaFrom.setNome(pessoa.getNome());
				pessoaFrom.setEmail(pessoa.getEmail());
				pessoaFrom.setUpdate(new Date());
			}
		});

		FileUtil.gerarArquivo(nomeBase, JsonUtil.toJson(new ListaPessoas(listaPessoa)));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see server.dao.Database#delete(long)
	 */
	@Override
	public boolean delete(long id)
	{
		deletePessoaFromAllBases(id);
		return true;
	}

	/***
	 * 
	 * @param id
	 */
	private void deletePessoaFromAllBases(long id)
	{
		deletePessoaFromBase(id, StorageConstants.BASE_UM);
		deletePessoaFromBase(id, StorageConstants.BASE_DOIS);
		deletePessoaFromBase(id, StorageConstants.BASE_TRES);
	}

	/***
	 * 
	 * @param id
	 * @param nomeBase
	 */
	private void deletePessoaFromBase(long id, String nomeBase)
	{
		List<Pessoa> dadosDaBase = obterDadosDaBase(nomeBase);
		dadosDaBase.removeIf(pessoa -> pessoa.getId() == id);
		FileUtil.gerarArquivo(nomeBase, JsonUtil.toJson(new ListaPessoas(dadosDaBase)));
	}

}
