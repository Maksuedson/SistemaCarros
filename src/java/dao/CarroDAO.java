package dao;

import br.max.Carro;
import br.max.FabricaConexao;
import exception.ErroSistema;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Max
 */
public class CarroDAO {

    public void salvar(Carro carro) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps;
            if (carro.getId() == null) {
                ps = conexao.prepareStatement("insert into carro (modelo, fabricante, cor, ano) values (?,?,?,?)");
            } else {
                ps = conexao.prepareStatement("update carro set modelo=?, fabricante=?, cor=?, ano=? where id=? ");
                ps.setInt(5, carro.getId());
            }
            ps.setString(1, carro.getModelo());
            ps.setString(2, carro.getFabricante());
            ps.setString(3, carro.getCor());
            ps.setDate(4, new java.sql.Date(carro.getAno().getTime()));
            ps.execute();
            FabricaConexao.fecharConexao();
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao tentar salvar ou alterar", ex);
        }
    }

    public void deletar(Integer idCarro) throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("delete from carro where id = ?");
            ps.setInt(1, idCarro);
            ps.execute();
        } catch (Exception ex) {
            throw new ErroSistema("Erro ao deletar um carro", ex);
        }
    }

    public List<Carro> buscar() throws ErroSistema {
        try {
            Connection conexao = FabricaConexao.getConexao();
            PreparedStatement ps = conexao.prepareStatement("select * from carro");
            ResultSet rs = ps.executeQuery();
            List<Carro> carros = new ArrayList<>();
            while (rs.next()) {
                Carro carro = new Carro();
                carro.setId(rs.getInt("id"));
                carro.setModelo(rs.getString("modelo"));
                carro.setFabricante(rs.getString("fabricante"));
                carro.setCor(rs.getString("cor"));
                carro.setAno(rs.getDate("ano"));
                carros.add(carro);
            }
            return carros;
        } catch (SQLException ex) {
            throw new ErroSistema("Erro ao buscar os carros", ex);
        }
    }

}
