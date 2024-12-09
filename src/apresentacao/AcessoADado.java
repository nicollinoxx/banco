package apresentacao;

import java.sql.*;
import java.util.ArrayList;
import java.math.BigDecimal;

public class AcessoADado {
    private static final String URL      = "jdbc:postgresql://localhost:5432/Contas_bancarias";
    private static final String USER     = "postgres";
    private static final String PASSWORD = "password";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void salvarConta(Conta conta) {
        String sql = "INSERT INTO contas (numero, saldo, limite) VALUES (?, ?, ?) ON CONFLICT (numero) DO UPDATE SET saldo = ?, limite = ?";

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, conta.getNumero());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setObject(3, conta instanceof ContaDebEspecial ? ((ContaDebEspecial) conta).getLimite() : null);
            stmt.setDouble(4, conta.getSaldo());
            stmt.setObject(5, conta instanceof ContaDebEspecial ? ((ContaDebEspecial) conta).getLimite() : null);
            stmt.executeUpdate();
            System.out.println("Conta salva/atualizada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Conta> buscarContas() {
        String sql = "SELECT numero, saldo, limite FROM contas";
        ArrayList<Conta> contas = new ArrayList<>();
        
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String numero = rs.getString("numero");
                double saldo = rs.getDouble("saldo");
                BigDecimal limiteBD = (BigDecimal) rs.getObject("limite");
                
                // Converte o limite para Double se não for nulo
                Double limite = null;
                if (limiteBD != null) {
                    limite = limiteBD.doubleValue();
                }
                
                // Cria a conta com base na presença de limite
                Conta conta;
                if (limite == null) {
                    conta = new ContaNormal(numero, saldo);
                } else {
                    conta = new ContaDebEspecial(numero, saldo, limite);
                }
                contas.add(conta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return contas;
    }

    public void deletarConta(String numero) {
        String sql = "DELETE FROM contas WHERE numero = ?";
        
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, numero);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Conta deletada com sucesso!");
            } else {
                System.out.println("Conta não encontrada.");
            } 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarSaldo(String numero, double novoSaldo) {
        String sql = "UPDATE contas SET saldo = ? WHERE numero = ?";
        
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, novoSaldo);
            stmt.setString(2, numero);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("Saldo atualizado com sucesso!");
            } else {
                System.out.println("Conta não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
