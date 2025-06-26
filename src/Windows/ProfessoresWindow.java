package Windows;

import DataBase.Dao.*;
import DataBase.Model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.util.ArrayList;

public class ProfessoresWindow extends JFrame {

    private JTextField txtNomeProfessor, txtTitulo;
    private JTextField txtIdDisciplina;
    private JButton btnInserir, btnExcluir;
    private Connection conexao;
    private ProfessoresDAO professoresDAO;
    private DisciplinasProfessoresDAO dpDAO;

    public ProfessoresWindow(Connection conexao) {
        this.conexao = conexao;
        setTitle("Gerenciar Professores");
        setSize(400, 250);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            professoresDAO = new ProfessoresDAO(conexao);
            dpDAO = new DisciplinasProfessoresDAO(conexao);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar com os DAOs");
            return;
        }

        JLabel lblNome = new JLabel("Nome do Professor:");
        lblNome.setBounds(10, 10, 140, 20);
        add(lblNome);

        txtNomeProfessor = new JTextField();
        txtNomeProfessor.setBounds(160, 10, 200, 20);
        add(txtNomeProfessor);

        JLabel lblTitulo = new JLabel("Título Acadêmico:");
        lblTitulo.setBounds(10, 40, 140, 20);
        add(lblTitulo);

        txtTitulo = new JTextField();
        txtTitulo.setBounds(160, 40, 200, 20);
        add(txtTitulo);

        JLabel lblIdDisciplina = new JLabel("ID da Disciplina:");
        lblIdDisciplina.setBounds(10, 70, 140, 20);
        add(lblIdDisciplina);

        txtIdDisciplina = new JTextField();
        txtIdDisciplina.setBounds(160, 70, 50, 20);
        add(txtIdDisciplina);

        btnInserir = new JButton("Inserir Professor");
        btnInserir.setBounds(10, 110, 160, 25);
        add(btnInserir);

        btnInserir.addActionListener((ActionEvent e) -> {
            try {
                Professores p = new Professores();
                p.setNomeProfessor(txtNomeProfessor.getText());
                p.setTitulo(txtTitulo.getText());

                int idDisciplina = Integer.parseInt(txtIdDisciplina.getText());

                int idProfessor = professoresDAO.insertAndReturnId(p);

                if (idDisciplina > 0 && idProfessor > 0) {
                    dpDAO.insert(idDisciplina, idProfessor);
                    JOptionPane.showMessageDialog(null, "Professor inserido e vinculado à disciplina com sucesso!");
                } else {
                    JOptionPane.showMessageDialog(null, "Erro ao associar professor e disciplina");
                }

                limparCampos();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao inserir professor");
            }
        });

        btnExcluir = new JButton("Excluir Professor");
        btnExcluir.setBounds(180, 110, 160, 25);
        add(btnExcluir);

        btnExcluir.addActionListener((ActionEvent e) -> {
            try {
                String nomeProfessor = txtNomeProfessor.getText().trim();
                if (nomeProfessor.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Informe o nome do professor para exclusão.");
                    return;
                }

                // Buscar o id do professor pelo nome
                int idProfessor = professoresDAO.selectIdByNome(nomeProfessor);

                if (idProfessor <= 0) {
                    JOptionPane.showMessageDialog(null, "Professor não encontrado.");
                    return;
                }

                // Verificar se está associado na tabela intermediária
                ArrayList<Disciplinas> disciplinasAssociadas = dpDAO.selectDisciplinasByProfessor(idProfessor);

                if (!disciplinasAssociadas.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Erro: Professor está associado a uma ou mais disciplinas e não pode ser excluído.");
                    return;
                }

                // Se não tem associações, pode excluir
                professoresDAO.deleteById(idProfessor);
                JOptionPane.showMessageDialog(null, "Professor excluído com sucesso.");
                limparCampos();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao excluir professor.");
            }
        });
    }

    private void limparCampos() {
        txtNomeProfessor.setText("");
        txtTitulo.setText("");
        txtIdDisciplina.setText("");
    }
}
