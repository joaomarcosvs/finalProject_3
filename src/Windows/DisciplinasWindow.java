package Windows;

import DataBase.Dao.*;
import DataBase.Model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.ArrayList;

public class DisciplinasWindow extends JFrame {

    private JTextField txtCodigoDisciplina, txtNomeDisciplina, txtDiaSemana, txtQuantProfessores, txtIdFase, txtIdProfessor;
    private JButton btnInserir, btnExcluir;
    private Connection conexao;
    private DisciplinasDAO disciplinasDAO;
    private DisciplinasProfessoresDAO dpDAO;

    public DisciplinasWindow(Connection conexao) {
        this.conexao = conexao;
        setTitle("Gerenciar Disciplinas");
        setSize(450, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            disciplinasDAO = new DisciplinasDAO(conexao);
            dpDAO = new DisciplinasProfessoresDAO(conexao);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar com os DAOs");
            return;
        }

        JLabel lblCodigo = new JLabel("Código da Disciplina:");
        lblCodigo.setBounds(10, 10, 140, 20);
        add(lblCodigo);

        txtCodigoDisciplina = new JTextField();
        txtCodigoDisciplina.setBounds(160, 10, 200, 20);
        add(txtCodigoDisciplina);

        JLabel lblNome = new JLabel("Nome da Disciplina:");
        lblNome.setBounds(10, 40, 140, 20);
        add(lblNome);

        txtNomeDisciplina = new JTextField();
        txtNomeDisciplina.setBounds(160, 40, 200, 20);
        add(txtNomeDisciplina);

        JLabel lblDiaSemana = new JLabel("Dia da Semana:");
        lblDiaSemana.setBounds(10, 70, 140, 20);
        add(lblDiaSemana);

        txtDiaSemana = new JTextField();
        txtDiaSemana.setBounds(160, 70, 200, 20);
        add(txtDiaSemana);

        JLabel lblQuantProf = new JLabel("Qtd Professores:");
        lblQuantProf.setBounds(10, 100, 140, 20);
        add(lblQuantProf);

        txtQuantProfessores = new JTextField();
        txtQuantProfessores.setBounds(160, 100, 200, 20);
        add(txtQuantProfessores);

        JLabel lblIdFase = new JLabel("ID da Fase:");
        lblIdFase.setBounds(10, 130, 140, 20);
        add(lblIdFase);

        txtIdFase = new JTextField();
        txtIdFase.setBounds(160, 130, 200, 20);
        add(txtIdFase);

        JLabel lblIdProfessor = new JLabel("ID do Professor:");
        lblIdProfessor.setBounds(10, 160, 140, 20);
        add(lblIdProfessor);

        txtIdProfessor = new JTextField();
        txtIdProfessor.setBounds(160, 160, 200, 20);
        add(txtIdProfessor);

        btnInserir = new JButton("Inserir Disciplina");
        btnInserir.setBounds(10, 200, 180, 30);
        add(btnInserir);

        btnExcluir = new JButton("Excluir Disciplina");
        btnExcluir.setBounds(220, 200, 180, 30);
        add(btnExcluir);

        btnInserir.addActionListener((ActionEvent e) -> {
            try {
                Disciplinas d = new Disciplinas();
                d.setCodigoDisciplina(Integer.parseInt(txtCodigoDisciplina.getText()));
                d.setNomeDisciplina(txtNomeDisciplina.getText());
                d.setDiaSemana(txtDiaSemana.getText());
                d.setQuantProfessores(Integer.parseInt(txtQuantProfessores.getText()));
                d.setIdFases(Integer.parseInt(txtIdFase.getText()));

                int idDisciplina = disciplinasDAO.insertAndReturnId(d);
                int idProfessor = Integer.parseInt(txtIdProfessor.getText());

                if (idDisciplina > 0 && idProfessor > 0) {
                    dpDAO.insert(idDisciplina, idProfessor);
                    JOptionPane.showMessageDialog(null, "Disciplina inserida e associada ao professor com sucesso!");
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao associar disciplina e professor.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao inserir disciplina.");
            }
        });

        btnExcluir.addActionListener((ActionEvent e) -> {
            try {
                String codigoStr = txtCodigoDisciplina.getText().trim();
                if (codigoStr.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Informe o código da disciplina para exclusão.");
                    return;
                }
                int codigo = Integer.parseInt(codigoStr);

                // Verificar se a disciplina está associada a algum professor
                ArrayList<Integer> professoresAssociados = dpDAO.selectProfessoresByDisciplina(codigo);

                if (!professoresAssociados.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Erro: Disciplina está associada a professores e não pode ser excluída.");
                    return;
                }

                // Se não tem associação, pode excluir
                disciplinasDAO.deleteById(codigo);
                JOptionPane.showMessageDialog(null, "Disciplina excluída com sucesso.");
                limparCampos();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao excluir disciplina.");
            }
        });
    }

    private void limparCampos() {
        txtCodigoDisciplina.setText("");
        txtNomeDisciplina.setText("");
        txtDiaSemana.setText("");
        txtQuantProfessores.setText("");
        txtIdFase.setText("");
        txtIdProfessor.setText("");
    }
}
