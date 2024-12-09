package apresentacao;

 
public class ContaNormal extends Conta {
	
  ContaNormal() {
	super();
  }
	   
  ContaNormal(String n, double s){
	numero = n;
	saldo  = s;
  }
	       
  public void creditar(double valor) {
    saldo = saldo + valor;
  }
  
  public void debitar(double valor) { 
    saldo = saldo - valor;
  }
  
  @Override
  public int compareTo(Conta o) {
    return numero.compareTo(o.getNumero());
  }
}

