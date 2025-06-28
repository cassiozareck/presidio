/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import com.mycompany.sistema_carcerario.model.Atendente;
import com.mycompany.sistema_carcerario.model.Prisioneiro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ferna
 */
public class AtendenteDao {
    public ArrayList<Atendente> getAtendentes(){
        ArrayList<Atendente> atendentes = new ArrayList<>();
        String sql = "SELECT nome,id FROM atendente";

        try (Connection conexao = ConexaoBanco.conectar()){
            Statement stmt = conexao.createStatement();  
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Atendente atendente = new Atendente(rs.getInt("id"),rs.getString("nome"));
                atendentes.add(atendente);   
            }
            
            return atendentes;

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao listar prisioneiros com filtro");
        }
        return null;
    }
    
    
    public int getIdAtendenteByName(String nome){
        String sql = "SELECT id FROM atendente WHERE nome = ?";   

        try (Connection conexao = ConexaoBanco.conectar()){
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, nome);
            
            try(ResultSet rs = ps.executeQuery()){     
                if (rs.next()){ 
                    int id = rs.getInt("id");
                    System.out.println("ID (Debug): " + id);
                    return id;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro ao buscar id do responsável pelo atendimento");
        }
        return -1;
    }
    
}
