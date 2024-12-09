package apresentacao;

abstract public class Conta  implements Comparable<Conta>{
  protected String numero; 
  protected double saldo;
  protected double limite;
  
  protected String getNumero() {
    return numero;
  }
  
  protected void setNumero(String value) {
    numero = value;
  }
  
  protected double getSaldo() {
    return saldo;
  }
  
  protected void setSaldo(double value) {
    saldo = value;
  }
  
  protected double getLimite() {
	return limite;
  }
  
  protected void setLimite(double l) {
	limite = l;
  }
	  
  abstract void creditar(double valor);
  abstract void debitar(double valor);
}
