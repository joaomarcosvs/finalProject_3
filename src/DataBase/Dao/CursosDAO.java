package DataBase.Dao;

import java.sql.*;
import java.util.ArrayList;

import DataBase.Model.Cursos;

public class CursosDAO {
    private final String selectAll = "SELECT * FROM universidade.tb_cursos";
    private final String selectById = "SELECT * FROM universidade.tb_cursos WHERE id = ?";
    private final String insert = "INSERT INTO universidade.tb_cursos (nomeCurso, faseInicial, faseFinal) VALUES (?, ?, ?)";
    private final String update = "UPDATE universidade.tb_cursos SET nomeCurso = ?, faseInicial = ?, faseFinal = ? WHERE id = ?";
    private final String deleteById = "DELETE FROM universidade.tb_cursos WHERE id = ?";

    private final Connection conexao;

    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstSelectById;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDeleteById;

    public CursosDAO(Connection conexao) throws SQLException {
        this.conexao = conexao;
        pstSelectAll = conexao.prepareStatement(selectAll);
        pstSelectById = conexao.prepareStatement(selectById);
        pstUpdate = conexao.prepareStatement(update);
        pstDeleteById = conexao.prepareStatement(deleteById);
    }

    public ArrayList<Cursos> selectAll() throws SQLException {
        ArrayList<Cursos> lista = new ArrayList<>();
        ResultSet resultado = pstSelectAll.executeQuery();

        while (resultado.next()) {
            Cursos curso = new Cursos();
            curso.setId(resultado.getInt("id"));
            curso.setNomeCurso(resultado.getString("nomeCurso"));
            curso.setFaseInicial(resultado.getInt("faseInicial"));
            curso.setFaseFinal(resultado.getInt("faseFinal"));
            lista.add(curso);
        }

        return lista;
    }

    public Cursos selectById(int id) throws SQLException {
        pstSelectById.setInt(1, id);
        ResultSet resultado = pstSelectById.executeQuery();

        if (resultado.next()) {
            Cursos curso = new Cursos();
            curso.setId(resultado.getInt("id"));
            curso.setNomeCurso(resultado.getString("nomeCurso"));
            curso.setFaseInicial(resultado.getInt("faseInicial"));
            curso.setFaseFinal(resultado.getInt("faseFinal"));
            return curso;
        }

        return null;
    }

    public int insert(Cursos curso) throws SQLException {
        String sql = "INSERT INTO universidade.tb_cursos (nomeCurso, faseInicial, faseFinal) VALUES (?, ?, ?)";
        PreparedStatement pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, curso.getNomeCurso());
        pst.setInt(2, curso.getFaseInicial());
        pst.setInt(3, curso.getFaseFinal());

        pst.executeUpdate();

        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return -1;
    }

    public void update(Cursos curso) throws SQLException {
        pstUpdate.setString(1, curso.getNomeCurso());
        pstUpdate.setInt(2, curso.getFaseInicial());
        pstUpdate.setInt(3, curso.getFaseFinal());
        pstUpdate.setInt(4, curso.getId());
        pstUpdate.executeUpdate();
    }

    public void deleteById(int id) throws SQLException {
        pstDeleteById.setInt(1, id);
        pstDeleteById.executeUpdate();
    }
}