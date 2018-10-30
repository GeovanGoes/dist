package client;

import commons.enumation.OperationType;
import commons.infra.Conexao;
import commons.model.Pessoa;
import commons.to.RequisicaoTO;
import commons.to.ResponseTO;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;

public class Cliente
{

	static Conexao c;
	static Socket socket;

	public Cliente()
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

		new Cliente();
		float op1, op2;
		char oper;
		Scanner in = new Scanner(System.in);

		System.out.println("*********************************");
		System.out.println("***    CADASTRO DE PESSOAS    ***");
		System.out.println("*********************************");

		int opcaoSelecionada = 0;

		do
		{
			showOptions();

			opcaoSelecionada = in.nextInt();

			switch (opcaoSelecionada)
			{
				case 1:
					showDigiteNome(null);
					String nome = in.nextLine();
					showDigiteEmail(null);
					String email = in.nextLine();
					Pessoa pessoa = new Pessoa(nome, email, new Date(), new Date());
					RequisicaoTO requisicaoTO = new RequisicaoTO(OperationType.CREATE, pessoa);
					c.send(socket, requisicaoTO);
					ResponseTO msgRep = (ResponseTO) c.receive(socket);
					System.out.println(msgRep.getMessage());
					break;

				default:
					break;
			}

		}
		while (opcaoSelecionada != 5);

		/*
		 * 
		 * System.out.println("Digite o primeiro numero"); op1 = in.nextFloat(); System.out.println("Digite o segundo numero"); op2 = in.nextFloat();
		 * System.out.println("Escolha uma operação"); System.out.println("(+)SOMA (-)SUBTRACAO (x)MULTIPLICACAO (/)DIVISAO"); oper = in.next().charAt(0);
		 * 
		 * Requisicao msgReq = new Requisicao(op1, op2, oper); c.send(socket, msgReq); Resposta msgRep = (Resposta) c.receive(socket);
		 * 
		 * if (msgRep.getStatus() == 0) { System.out.println("Resultado = " + msgRep.getResult()); } else if (msgRep.getStatus() == 1) {
		 * System.out.println("Operacao nao Implementada"); } else { System.out.println("Divisao por Zero"); }
		 */
		try
		{
			socket.close();
		}
		catch (Exception e)
		{
			System.out.println("problemas ao fechar socket");
		}
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
		System.out.println("Operações");
		System.out.println("(1) - Cadastrar");
		System.out.println("(2) - Listar");
		System.out.println("(3) - Atualizar");
		System.out.println("(4) - Deletar");
		System.out.println("(5) - Finalizar");
	}
}
