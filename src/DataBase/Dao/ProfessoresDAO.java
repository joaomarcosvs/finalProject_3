package DataBase.Dao;

import java.sql.*;
import java.util.ArrayList;

import DataBase.Model.Professores;

public class ProfessoresDAO {
    private final String selectAll = "SELECT * FROM universidade.tb_professores";
    private final String selectById = "SELECT * FROM universidade.tb_professores WHERE id = ?";
    private final String insert = "INSERT INTO universidade.tb_professores (nomeProfessor, titulo) VALUES (?, ?)";
    private final String update = "UPDATE universidade.tb_professores SET nomeProfessor = ?, titulo = ? WHERE id = ?";
    private final String deleteById = "DELETE FROM universidade.tb_professores WHERE id = ?";

    private final Connection conexao;

    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstSelectById;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDeleteById;

    public ProfessoresDAO(Connection conexao) throws SQLException {
        this.conexao = conexao;

        pstSelectAll = conexao.prepareStatement(selectAll);
        pstSelectById = conexao.prepareStatement(selectById);
        pstInsert = conexao.prepareStatement(insert);
        pstUpdate = conexao.prepareStatement(update);
        pstDeleteById = conexao.prepareStatement(deleteById);
    }

    public ArrayList<Professores> selectAll() throws SQLException {
        ArrayList<Professores> lista = new ArrayList<>();
        ResultSet rs = pstSelectAll.executeQuery();

        while (rs.next()) {
            Professores professor = new Professores();
            professor.setId(rs.getInt("id"));
            professor.setNomeProfessor(rs.getString("nomeProfessor"));
            professor.setTitulo(rs.getString("titulo"));
            lista.add(professor);
        }

        return lista;
    }

    public int insertAndReturnId(Professores p) throws SQLException {
        String sql = "INSERT INTO universidade.tb_professores (nomeProfessor, titulo) VALUES (?, ?)";
        PreparedStatement pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, p.getNomeProfessor());
        pst.setString(2, p.getTitulo());
        pst.executeUpdate();

        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) return rs.getInt(1);
        return -1;
    }

    public Professores selectById(int id) throws SQLException {
        pstSelectById.setInt(1, id);
        ResultSet rs = pstSelectById.executeQuery();

        if (rs.next()) {
            Professores p = new Professores();
            p.setId(rs.getInt("id"));
            p.setNomeProfessor(rs.getString("nomeProfessor"));
            p.setTitulo(rs.getString("titulo"));
            return p;
        }

        return null;
    }

    public boolean insert(Professores p) throws SQLException {
        pstInsert.setString(1, p.getNomeProfessor());
        pstInsert.setString(2, p.getTitulo());
        return pstInsert.executeUpdate() > 0;
    }

    public void update(Professores p) throws SQLException {
        pstUpdate.setString(1, p.getNomeProfessor());
        pstUpdate.setString(2, p.getTitulo());
        pstUpdate.setInt(3, p.getId());
        pstUpdate.executeUpdate();
    }

    public void deleteById(int id) throws SQLException {
        pstDeleteById.setInt(1, id);
        pstDeleteById.executeUpdate();
    }

    public int selectIdByNome(String nome) throws SQLException {
        String sql = "SELECT id FROM universidade.tb_professores WHERE nomeProfessor = ?";
        PreparedStatement pst = conexao.prepareStatement(sql);
        pst.setString(1, nome);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) return rs.getInt("id");
        return -1;
    }

}
