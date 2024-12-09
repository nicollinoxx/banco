package apresentacao;

public class ContaDebEspecial extends ContaEspecial{
   
   ContaDebEspecial() {
   	super();
   }
   
   ContaDebEspecial(String n, double s, double l){
   	numero = n;
	saldo  = s;
	limite = l;
   }
  
  @Override
  public int compareTo(Conta o) {
    return numero.compareTo(o.getNumero());
  }
}

