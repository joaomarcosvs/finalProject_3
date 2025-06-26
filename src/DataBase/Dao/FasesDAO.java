package DataBase.Dao;

import DataBase.Model.Fases;

import java.sql.*;
import java.util.ArrayList;

public class FasesDAO {

    private final String selectAll = "SELECT * FROM universidade.tb_fases";
    private final String selectById = "SELECT * FROM universidade.tb_fases WHERE id = ?";
    private final String insert = "INSERT INTO universidade.tb_fases (numeroFase, idCursos) VALUES (?, ?)";
    private final String update = "UPDATE universidade.tb_fases SET numeroFase = ?, idCursos = ? WHERE id = ?";
    private final String deleteById = "DELETE FROM universidade.tb_fases WHERE id = ?";

    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstSelectById;
    private final Connection conexao;
    private PreparedStatement pstInsert;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDeleteById;

    public FasesDAO(Connection conexao) throws SQLException {
        this.conexao = conexao;
        pstSelectAll = conexao.prepareStatement(selectAll);
        pstSelectById = conexao.prepareStatement(selectById);
        pstInsert = conexao.prepareStatement(insert, PreparedStatement.RETURN_GENERATED_KEYS);
        pstUpdate = conexao.prepareStatement(update);
        pstDeleteById = conexao.prepareStatement(deleteById);
    }

    public ArrayList<Fases> selectAll() throws SQLException {
        ArrayList<Fases> lista = new ArrayList<>();
        ResultSet resultado = pstSelectAll.executeQuery();

        while (resultado.next()) {
            Fases fase = new Fases();
            fase.setId(resultado.getInt("id"));
            fase.setNumeroFase(resultado.getInt("numeroFase"));
            fase.setIdCursos(resultado.getInt("idCursos"));
            lista.add(fase);
        }

        return lista;
    }

    public Fases selectById(int id) throws SQLException {
        pstSelectById.setInt(1, id);
        ResultSet resultado = pstSelectById.executeQuery();

        if (resultado.next()) {
            Fases curso = new Fases();
            curso.setId(resultado.getInt("id"));
            curso.setNumeroFase(resultado.getInt("numeroFase"));
            curso.setIdCursos(resultado.getInt("idCursos"));
            return curso;
        }

        return null;
    }
    
    public int insert(Fases fase) throws SQLException {
        String sql = "INSERT INTO tb_fases (numeroFase, idCursos) VALUES (?, ?)";
        PreparedStatement pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setInt(1, fase.getNumeroFase());
        pst.setInt(2, fase.getIdCursos());

        pst.executeUpdate();

        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public void update(Fases fase) throws SQLException {
        pstUpdate.setInt(1, fase.getNumeroFase());
        pstUpdate.setInt(2, fase.getIdCursos());
        pstUpdate.setInt(4, fase.getId());
        pstUpdate.executeUpdate();
    }

    public void deleteById(int id) throws SQLException {
        pstDeleteById.setInt(1, id);
        pstDeleteById.executeUpdate();
    }
}
