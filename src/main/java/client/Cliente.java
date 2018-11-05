package client;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import commons.enumation.OperationType;
import commons.infra.Conexao;
import commons.model.Pessoa;
import commons.to.RequisicaoTO;
import commons.to.ResponseTO;


public class Cliente
{

	static Conexao conexao;
	static Socket socket;

	public Cliente()
	{
		criarConexao();
	}

	private static void criarConexao()
	{
		try
		{
			socket = new Socket("localhost", 9600);
		}
		catch (IOException e)
		{
			System.out.println("Nao consegui resolver o host...");
		}
	}

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);

		System.out.println("*********************************");
		System.out.println("***    CADASTRO DE PESSOAS    ***");
		System.out.println("*********************************");

		int opcaoSelecionada = 0;
		
		do
		{
			showOptions();

			opcaoSelecionada = scanner.nextInt();

			criarConexao();
			
			switch (opcaoSelecionada)
			{
				case 1:
					processoCreate(scanner);
					break;
				case 2:
					processoRead();
					break;
				case 3:
					processoUpdate();
					break;
				case 4: 
					processoDelete();
					break;
				default:
					break;
					
			}
		}
		while (opcaoSelecionada != 5);
		
		try
		{
			socket.close();
		}
		catch (Exception e)
		{
			System.out.println("problemas ao fechar socket");
		}
	}

	/***
	 * 
	 */
	@SuppressWarnings("static-access")
	private static void processoDelete()
	{
		processoRead();
		System.out.println("Digite o id do usuario que deseja deletar.");
		long id = lerLong();
		
		ResponseTO responseReadById = readByIdProcess(id);
		
		if (responseReadById.isSuccess())
		{
			criarConexao();		
			conexao.send(socket, new RequisicaoTO(OperationType.DELETE, new Pessoa(id, null, null, null, null)));
			ResponseTO response = (ResponseTO) conexao.receive(socket);
			System.out.println(response.getMessage());
		}
		else
			System.out.println(responseReadById.getMessage());
	}

	/***
	 * 
	 */
	@SuppressWarnings("static-access")
	private static void processoUpdate()
	{
		processoRead();
		System.out.println("Digite o id do usuario que deseja alterar.");
		long id = lerLong();
		ResponseTO response = readByIdProcess(id);
		
		if (response.isSuccess())
		{
			Pessoa pessoa = response.getPessoa();
			exibirPessoa(pessoa);
			showDigiteNome(pessoa.getNome());
			pessoa.setNome(lerString()); 
			showDigiteEmail(pessoa.getEmail());
			pessoa.setEmail(lerString());
			criarConexao();
			conexao.send(socket, new RequisicaoTO(OperationType.UPDATE, pessoa));
			ResponseTO responseUpdate = (ResponseTO) conexao.receive(socket);
			System.out.println(responseUpdate.getMessage());
		}
		else
			System.out.println(response.getMessage());
	}

	@SuppressWarnings("static-access")
	private static ResponseTO readByIdProcess(long id)
	{
		criarConexao();
		conexao.send(socket, new RequisicaoTO(OperationType.READ_BY_ID, new Pessoa(id, null, null, null, null)));
		ResponseTO response = (ResponseTO) conexao.receive(socket);
		return response;
	}

	/***
	 * 
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String lerString()
	{
		return new Scanner(System.in).nextLine();
	}
	
	/***
	 * 
	 * @return
	 */
	@SuppressWarnings("resource")
	public static long lerLong()
	{
		return new Scanner(System.in).nextLong();
	}
	
	/***
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	private static List<Pessoa> processoRead()
	{
		conexao.send(socket, new RequisicaoTO(OperationType.READ, null));
		ResponseTO resonse = (ResponseTO) conexao.receive(socket);
		exibirPessoas(resonse);
		return resonse.getPessoas();
	}

	@SuppressWarnings("static-access")
	private static void processoCreate(Scanner in)
	{
		showDigiteNome(null);
		String nome = lerString();
		showDigiteEmail(null);
		String email = lerString();
		Pessoa pessoa = new Pessoa(nome, email, new Date(), new Date());
		RequisicaoTO requisicaoTO = new RequisicaoTO(OperationType.CREATE, pessoa);
		conexao.send(socket, requisicaoTO);
		ResponseTO msgRep = (ResponseTO) conexao.receive(socket);
		System.out.println(msgRep.getMessage());
	}

	/***
	 * 
	 * @param resonse
	 */
	private static void exibirPessoas(ResponseTO resonse)
	{
		for (Pessoa pessoa : resonse.getPessoas())
		{
			exibirPessoa(pessoa);
			System.out.println("----------------------------------------------------------------------------------");
		}
			
	}
	
	/***
	 * 
	 * @param pessoa
	 */
	private static void exibirPessoa(Pessoa pessoa)
	{
		System.out.println("Id: " + pessoa.getId());
		System.out.println("Nome: " + pessoa.getNome());
		System.out.println("Email: " + pessoa.getEmail());
		System.out.println("Data de cricao: " + pessoa.getCricao());
		System.out.println("Data ultima atualizacao: " + pessoa.getUpdate());
	}
	
	/**
	 * @param object
	 */
	private static void showDigiteEmail(String actual)
	{
		showBaseMsg("Digite o email: ", actual);
	}

	/***
	 * 
	 * @param actual
	 */
	private static void showDigiteNome(String actual)
	{
		showBaseMsg("Digite o nome: ", actual);
	}

	/**
	 * 
	 * @param baseMsg
	 * @param optional
	 */
	private static void showBaseMsg(String baseMsg, String optional)
	{
		System.out.println(baseMsg + (optional != null ? "(" + optional + ")" : ""));
	}

	/**
	 * 
	 */
	private static void showOptions()
	{
		System.out.println("Selecione uma opcao");
		System.out.println("(1) - Cadastrar");
		System.out.println("(2) - Listar");
		System.out.println("(3) - Atualizar");
		System.out.println("(4) - Deletar");
	}
}
