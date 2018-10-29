
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    static ServerSocket serversocket;
    static Socket client_socket;
    static Conexao c;
    
    public Servidor() {
        try {
            serversocket = new ServerSocket(9600);
            System.out.println("Calculadora distribuida no ar!!!");
            System.out.println("Aguardando cliente fazer requisiçao ...");
        } catch (IOException e) {
            System.out.println("Nao criei o server socket...");
        }
    }

    public static void main(String args[]) {
        RequisicaoTO msgReq;
        Resposta msgRep;
        int visits = 0;
        new Servidor();

        while (true) {
            if (connect()) {
                msgReq = (RequisicaoTO) c.receive(client_socket);

                System.out.println(msgReq.getOperation());
                /*char op = msgReq.getOperacao();
                System.out.println("Operacao " + op);
                msgRep = new Resposta();

                switch (op) {
                    case '+':
                        msgRep.setStatus(0);
                        msgRep.setResult(msgReq.getOp1() + msgReq.getOp2());
                        break;
                    case '-':
                        msgRep.setStatus(0);
                        msgRep.setResult(msgReq.getOp1() - msgReq.getOp2());
                        break;
                    case 'x':
                        msgRep.setStatus(0);
                        msgRep.setResult(msgReq.getOp1() * msgReq.getOp2());
                        break;
                    case '/':
                        if (msgReq.getOp2() == 0.0F) {
                            msgRep.setStatus(2);
                        } else {
                            msgRep.setStatus(0);
                            msgRep.setResult(msgReq.getOp1() / msgReq.getOp2());
                        }
                        break;
                    default:
                        msgRep.setStatus(1);
                        break;
                }*/
                c.send(client_socket, "Aeee carai");
            } else {
                try {
                    serversocket.close();
                    break;
                } catch (IOException e) {
                    System.out.println("Nao desconectei...");
                }
            }
        }
    }

    static boolean connect() {
        boolean ret;
        try {
            client_socket = serversocket.accept();
            return true;
        } catch (IOException e) {
            System.out.println("Erro de connect..." + e.getMessage());
            return false;
        }
    }
}
