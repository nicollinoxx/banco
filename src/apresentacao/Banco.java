package apresentacao;

import java.util.ArrayList;
import java.util.Iterator;

public class Banco {
    private ArrayList<Conta> contas;

    public Banco() {
        contas = new ArrayList<>();
    }

    public void criarConta(Conta c) {
        contas.add(c);
    }
    
    public ArrayList<Conta> getContas() {
        return contas;
    }

    public void removerConta(String numero) {
        Iterator<Conta> iterator = contas.iterator();
        while (iterator.hasNext()) {
            Conta c = iterator.next();
            if (c.getNumero().equals(numero)) {
                iterator.remove();
                System.out.printf("Conta %s removida com sucesso.%n", numero);
                return;
            }
        }
        System.out.printf("Conta %s não encontrada.%n", numero);
    }

    public void creditarConta(String numero, double valor) {
        for (Conta c : contas) {
            if (c.getNumero().equals(numero)) {
                c.creditar(valor);
                System.out.printf("Conta %s creditada com R$ %.2f.%n", numero, valor);
                return;
            }
        }
        System.out.printf("Conta %s não encontrada.%n", numero);
    }

    public void debitarConta(String numero, double valor) {
        for (Conta c : contas) {
            if (c.getNumero().equals(numero)) {
                if (c.getSaldo() >= valor) {
                	c.debitar(valor);
                    System.out.printf("Conta %s debitada com R$ %.2f.%n", numero, valor);
                } else {
                    System.out.printf("Saldo insuficiente na conta %s.%n", numero);
                }
                return;
            }
        }
        System.out.printf("Conta %s não encontrada.%n", numero);
    }

    public void transferirConta(String origem, String destino, double valor) {
        Conta contaOrigem = null, contaDestino = null;

        for (Conta c : contas) {
            if (c.getNumero().equals(origem))  contaOrigem  = c;
            if (c.getNumero().equals(destino)) contaDestino = c;
        }

        if (contaOrigem != null && contaDestino != null) {
            if (contaOrigem.getSaldo() >= valor) {
                contaDestino.creditar(valor);
                System.out.printf("Transferência de R$ %.2f de %s para %s realizada com sucesso.%n", valor, origem, destino);
            } else {
                System.out.printf("Saldo insuficiente na conta %s.%n", origem);
            }
        } else {
            System.out.println("Conta(s) não encontrada(s).");
        }
    }

    public void listarContas() {
        for (Conta c : contas) {
            System.out.printf("Conta %s: Saldo R$ %.2f%n", c.getNumero(), c.getSaldo());
        }
    }

    public static void main(String[] args) {
        AcessoADado acessoADado = new AcessoADado();

        ArrayList<Conta> contas = acessoADado.buscarContas();
        if (!contas.isEmpty()) {
        	for (Conta conta : contas) {
                System.out.printf("Conta %s: Saldo R$ %.2f%n", conta.getNumero(), conta.getSaldo());
            }
        }
    }
}
