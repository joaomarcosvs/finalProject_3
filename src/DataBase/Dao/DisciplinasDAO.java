package DataBase.Dao;

import java.sql.*;
import java.util.ArrayList;

import DataBase.Model.Disciplinas;

public class DisciplinasDAO {

    private final String selectAll = "SELECT * FROM universidade.tb_disciplinas";
    private final String selectById = "SELECT * FROM universidade.tb_disciplinas WHERE id = ?";
    private final String insert = "INSERT INTO universidade.tb_disciplinas (codigoDisciplina, nomeDisciplina, diaSemana, quantProfessores, idFases) VALUES (?, ?, ?, ?, ?)";
    private final String update = "UPDATE universidade.tb_disciplinas SET codigoDisciplina = ?, nomeDisciplina = ?, diaSemana = ?, quantProfessores = ?, idFases = ? WHERE id = ?";
    private final String deleteById = "DELETE FROM universidade.tb_disciplinas WHERE id = ?";

    private final Connection conexao;
    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstSelectById;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstUpdate;
    private final PreparedStatement pstDeleteById;

    public DisciplinasDAO(Connection conexao) throws SQLException {
        this.conexao = conexao;
        pstSelectAll = conexao.prepareStatement(selectAll);
        pstSelectById = conexao.prepareStatement(selectById);
        pstInsert = conexao.prepareStatement(insert);
        pstUpdate = conexao.prepareStatement(update);
        pstDeleteById = conexao.prepareStatement(deleteById);
    }

    public ArrayList<Disciplinas> selectAll() throws SQLException {
        ArrayList<Disciplinas> lista = new ArrayList<>();
        ResultSet rs = pstSelectAll.executeQuery();

        while (rs.next()) {
            Disciplinas disciplinas = new Disciplinas();
            disciplinas.setId(rs.getInt("id"));
            disciplinas.setCodigoDisciplina(rs.getInt("codigoDisciplina"));
            disciplinas.setNomeDisciplina(rs.getString("nomeDisciplina"));
            disciplinas.setDiaSemana(rs.getString("diaSemana"));
            disciplinas.setQuantProfessores(rs.getInt("quantProfessores"));
            disciplinas.setIdFases(rs.getInt("idFases"));
            lista.add(disciplinas);
        }

        return lista;
    }

    public Disciplinas selectById(int id) throws SQLException {
        pstSelectById.setInt(1, id);
        ResultSet rs = pstSelectById.executeQuery();

        if (rs.next()) {
            Disciplinas d = new Disciplinas();
            d.setId(rs.getInt("id"));
            d.setCodigoDisciplina(rs.getInt("codigoDisciplina"));
            d.setNomeDisciplina(rs.getString("nomeDisciplina"));
            d.setDiaSemana(rs.getString("diaSemana"));
            d.setQuantProfessores(rs.getInt("quantProfessores"));
            d.setIdFases(rs.getInt("idFases"));
            return d;
        }

        return null;
    }
    public int insert(Disciplinas d) throws SQLException {
        String sql = "INSERT INTO tb_disciplinas (codigoDisciplina, nomeDisciplina, diaSemana, quantProfessores, idFases) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setInt(1, d.getCodigoDisciplina());
        pst.setString(2, d.getNomeDisciplina());
        pst.setString(3, d.getDiaSemana());
        pst.setInt(4, d.getQuantProfessores());
        pst.setInt(5, d.getIdFases());

        pst.executeUpdate();

        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;
    }

    public int insertAndReturnId(Disciplinas d) throws SQLException {
        String sql = "INSERT INTO universidade.tb_disciplinas (codigoDisciplina, nomeDisciplina, diaSemana, quantProfessores, idFases) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pst = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, d.getCodigoDisciplina());
            pst.setString(2, d.getNomeDisciplina());
            pst.setString(3, d.getDiaSemana());
            pst.setInt(4, d.getQuantProfessores());
            pst.setInt(5, d.getIdFases());

            int affectedRows = pst.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Inserção falhou, nenhuma linha afetada.");
            }

            try (ResultSet rs = pst.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o ID gerado.");
                }
            }
        }
    }


    public void update(Disciplinas d) throws SQLException {
        pstUpdate.setInt(1, d.getCodigoDisciplina());
        pstUpdate.setString(2, d.getNomeDisciplina());
        pstUpdate.setString(3, d.getDiaSemana());
        pstUpdate.setInt(4, d.getQuantProfessores());
        pstUpdate.setInt(5, d.getIdFases());
        pstUpdate.setInt(6, d.getId());
        pstUpdate.executeUpdate();
    }

    public void deleteById(int id) throws SQLException {
        pstDeleteById.setInt(1, id);
        pstDeleteById.executeUpdate();
    }
}