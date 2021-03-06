package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import server.dao.Database;
import server.dao.DatabaseImpl;
import server.dao.FileUtil;
import server.dao.StorageConstants;

import commons.enumation.OperationType;
import commons.infra.Conexao;
import commons.model.Pessoa;
import commons.to.RequisicaoTO;
import commons.to.ResponseTO;

public class Servidor
{

	static ServerSocket serversocket;
	static Socket client_socket;
	static Conexao c;

	public Servidor()
	{
		try
		{
			checkIfBaseFilesExists();
			serversocket = new ServerSocket(9600);
			System.out.println("Servidor rodando...");
		}
		catch (IOException e)
		{
			System.out.println("Erro ao criar o server...");
		}
	}

	/***
	 * 
	 */
	private static void checkIfBaseFilesExists()
	{
		checkIfBaseFilesExists(StorageConstants.BASE_UM);
		checkIfBaseFilesExists(StorageConstants.BASE_DOIS);
		checkIfBaseFilesExists(StorageConstants.BASE_TRES);
	}

	/**
	 * 
	 */
	private static void checkIfBaseFilesExists(String fileName)
	{
		try
		{
			FileUtil.obterConteudoDeUmArquivo(fileName);
		}
		catch (Exception e)
		{
			FileUtil.gerarArquivo(fileName, "");
		}
	}

	@SuppressWarnings({ "static-access" })
	public static void main(String args[])
	{
		RequisicaoTO requisicao;
		new Servidor();

		Database database = new DatabaseImpl();

		while (true)
		{
			if (connect())
			{
				requisicao = (RequisicaoTO) c.receive(client_socket);

				if (requisicao.getOperation().equals(OperationType.CREATE))
				{
					boolean create = database.create(requisicao.getPessoa());
					if (create)
						c.send(client_socket, new ResponseTO(true, "Pessoa inserida com sucesso."));
					else
						c.send(client_socket, new ResponseTO(false, "Nao foi possivel salvar os dados."));
				}
				else if (requisicao.getOperation().equals(OperationType.READ))
				{
					List<Pessoa> read = database.read();
					if (read != null)
						c.send(client_socket, new ResponseTO(true, "Pessoas listadas com sucesso.", read));
					else
						c.send(client_socket, new ResponseTO(false, "Nao foi possivel listar as pessoas."));
				}
				else if (requisicao.getOperation().equals(OperationType.READ_BY_ID))
				{
					Pessoa read = database.readById(requisicao.getPessoa().getId());

					if (read != null)
						c.send(client_socket, new ResponseTO(true, "Pessoa obtida com sucesso.", read));
					else
						c.send(client_socket, new ResponseTO(false, "Nao foi possivel obter a pessoa."));
				}
				else if (requisicao.getOperation().equals(OperationType.UPDATE))
				{
					boolean update = database.update(requisicao.getPessoa());
					if (update)
						c.send(client_socket, new ResponseTO(true, "Pessoa atualizada com sucesso."));
					else
						c.send(client_socket, new ResponseTO(false, "Nao foi possivel atualizar os dados."));
				}
				else if (requisicao.getOperation().equals(OperationType.DELETE))
				{
					boolean delete = database.delete(requisicao.getPessoa().getId());
					if (delete)
						c.send(client_socket, new ResponseTO(true, "Pessoa deletada com sucesso."));
					else
						c.send(client_socket, new ResponseTO(false, "Nao foi possivel deletar os dados."));
				}
				else
				{
					c.send(client_socket, new ResponseTO(false, "404 - Nao foi processar sua requisicao."));
				}

			}
			else
			{
				try
				{
					serversocket.close();
					break;
				}
				catch (IOException e)
				{
					System.out.println("Nao desconectei...");
				}
			}
		}
	}

	/***
	 * 
	 * @return
	 */
	static boolean connect()
	{
		try
		{
			client_socket = serversocket.accept();
			return true;
		}
		catch (IOException e)
		{
			System.out.println("Erro de connect..." + e.getMessage());
			return false;
		}
	}
}
