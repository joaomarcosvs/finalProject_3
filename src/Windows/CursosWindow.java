package Windows;

import DataBase.Dao.CursosDAO;
import DataBase.Model.Cursos;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;

public class CursosWindow extends JFrame {

    private JTextField txtNomeCurso, txtFaseInicial, txtFaseFinal;
    private JTextField txtIdExcluir;
    private JButton btnInserir, btnExcluir;
    private Connection conexao;
    private CursosDAO cursosDAO;

    public CursosWindow(Connection conexao) {
        this.conexao = conexao;
        setTitle("Gerenciar Cursos");
        setSize(400, 250);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            cursosDAO = new CursosDAO(conexao);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o DAO de Cursos");
            return;
        }

        JLabel lblNome = new JLabel("Nome do Curso:");
        lblNome.setBounds(10, 10, 120, 20);
        add(lblNome);

        txtNomeCurso = new JTextField();
        txtNomeCurso.setBounds(130, 10, 220, 20);
        add(txtNomeCurso);

        JLabel lblFaseIni = new JLabel("Fase Inicial:");
        lblFaseIni.setBounds(10, 40, 120, 20);
        add(lblFaseIni);

        txtFaseInicial = new JTextField();
        txtFaseInicial.setBounds(130, 40, 50, 20);
        add(txtFaseInicial);

        JLabel lblFaseFim = new JLabel("Fase Final:");
        lblFaseFim.setBounds(10, 70, 120, 20);
        add(lblFaseFim);

        txtFaseFinal = new JTextField();
        txtFaseFinal.setBounds(130, 70, 50, 20);
        add(txtFaseFinal);

        btnInserir = new JButton("Inserir Curso");
        btnInserir.setBounds(10, 100, 140, 25);
        add(btnInserir);

        btnInserir.addActionListener((ActionEvent e) -> {
            try {
                Cursos c = new Cursos();
                c.setNomeCurso(txtNomeCurso.getText());
                c.setFaseInicial(Integer.parseInt(txtFaseInicial.getText()));
                c.setFaseFinal(Integer.parseInt(txtFaseFinal.getText()));

                cursosDAO.insert(c);
                JOptionPane.showMessageDialog(null, "Curso inserido com sucesso!");
                limparCampos();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao inserir curso");
            }
        });

        JLabel lblExcluir = new JLabel("ID para excluir:");
        lblExcluir.setBounds(10, 140, 120, 20);
        add(lblExcluir);

        txtIdExcluir = new JTextField();
        txtIdExcluir.setBounds(130, 140, 50, 20);
        add(txtIdExcluir);

        btnExcluir = new JButton("Excluir Curso");
        btnExcluir.setBounds(10, 170, 140, 25);
        add(btnExcluir);

        btnExcluir.addActionListener((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(txtIdExcluir.getText());
                cursosDAO.deleteById(id);
                JOptionPane.showMessageDialog(null, "Curso excluído com sucesso!");
                txtIdExcluir.setText("");
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Não é possível excluir este curso.\nExistem fases vinculadas a ele.",
                        "Erro de Integridade",
                        JOptionPane.WARNING_MESSAGE
                );
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao excluir curso");
            }
        });
    }

    private void limparCampos() {
        txtNomeCurso.setText("");
        txtFaseInicial.setText("");
        txtFaseFinal.setText("");
    }
}
