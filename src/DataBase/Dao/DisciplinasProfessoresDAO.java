package DataBase.Dao;

import java.sql.*;
import java.util.ArrayList;

import DataBase.Model.Disciplinas;
import DataBase.Model.DisciplinasProfessores;

public class DisciplinasProfessoresDAO {
    private final String selectAll = "SELECT * FROM universidade.disciplinas_professores";
    private final String insert = "INSERT INTO universidade.disciplinas_professores (idDisciplinas, idProfessores) VALUES (?, ?)";
    private final String deleteById = "DELETE FROM universidade.disciplinas_professores WHERE id = ?";

    private final Connection conexao;
    private final PreparedStatement pstSelectAll;
    private final PreparedStatement pstInsert;
    private final PreparedStatement pstDeleteById;

    public DisciplinasProfessoresDAO(Connection conexao) throws SQLException {
        this.conexao = conexao;
        pstSelectAll = conexao.prepareStatement(selectAll);
        pstInsert = conexao.prepareStatement(insert);
        pstDeleteById = conexao.prepareStatement(deleteById);
    }

    public ArrayList<DisciplinasProfessores> selectAll() throws SQLException {
        ArrayList<DisciplinasProfessores> lista = new ArrayList<>();
        ResultSet rs = pstSelectAll.executeQuery();

        while (rs.next()) {
            DisciplinasProfessores dp = new DisciplinasProfessores();
            dp.setId(rs.getInt("id"));
            dp.setIdDisciplinas(rs.getInt("idDisciplinas"));
            dp.setIdProfessores(rs.getInt("idProfessores"));
            lista.add(dp);
        }

        return lista;
    }

    public ArrayList<Disciplinas> selectDisciplinasByProfessor(int idProfessor) throws SQLException {
        ArrayList<Disciplinas> lista = new ArrayList<>();
        String sql = "SELECT d.* FROM disciplinas d " +
                "INNER JOIN disciplinas_professores dp ON d.id = dp.idDisciplinas " +
                "WHERE dp.idProfessores = ?";

        try (PreparedStatement pst = conexao.prepareStatement(sql)) {
            pst.setInt(1, idProfessor);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Disciplinas d = new Disciplinas();
                    d.setId(rs.getInt("id"));
                    d.setCodigoDisciplina(rs.getInt("codigoDisciplina")); // ajuste o nome do campo se necessário
                    d.setNomeDisciplina(rs.getString("nomeDisciplina"));
                    d.setDiaSemana(rs.getString("diaSemana"));
                    d.setQuantProfessores(rs.getInt("quantProfessores"));
                    d.setIdFases(rs.getInt("idFases"));
                    // preencha outros campos de Disciplinas conforme a sua modelagem
                    lista.add(d);
                }
            }
        }

        return lista;
    }

    public ArrayList<Integer> selectProfessoresByDisciplina(int idDisciplina) throws SQLException {
        ArrayList<Integer> lista = new ArrayList<>();
        String sql = "SELECT idProfessores FROM disciplinas_professores WHERE idDisciplinas = ?";
        PreparedStatement pst = conexao.prepareStatement(sql);
        pst.setInt(1, idDisciplina);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            lista.add(rs.getInt("idProfessores"));
        }
        rs.close();
        pst.close();
        return lista;
    }


    public void insert(int idDisciplina, int idProfessor) throws SQLException {
        String sql = "INSERT INTO disciplinas_professores (idDisciplinas, idProfessores) VALUES (?, ?)";
        PreparedStatement stmt = conexao.prepareStatement(sql);
        stmt.setInt(1, idDisciplina);
        stmt.setInt(2, idProfessor);
        stmt.executeUpdate();
    }

    public void deleteById(int id) throws SQLException {
        pstDeleteById.setInt(1, id);
        pstDeleteById.executeUpdate();
    }
}

