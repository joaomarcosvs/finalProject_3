package Windows;

import DataBase.Dao.FasesDAO;
import DataBase.Model.Fases;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLIntegrityConstraintViolationException;

public class FasesWindow extends JFrame {
    private JTextField txtNumeroFase, txtIdCurso, txtIdExcluir;
    private JButton btnInserir, btnExcluir;
    private Connection conexao;
    private FasesDAO fasesDAO;

    public FasesWindow(Connection conexao) {
        this.conexao = conexao;
        setTitle("Gerenciar Fases");
        setSize(400, 250);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);

        try {
            fasesDAO = new FasesDAO(conexao);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao conectar com o DAO de Fases");
            return;
        }

        JLabel lblNumeroFase = new JLabel("Número da Fase:");
        lblNumeroFase.setBounds(10, 10, 120, 20);
        add(lblNumeroFase);

        txtNumeroFase = new JTextField();
        txtNumeroFase.setBounds(130, 10, 200, 20);
        add(txtNumeroFase);

        JLabel lblIdCurso = new JLabel("ID do Curso:");
        lblIdCurso.setBounds(10, 40, 120, 20);
        add(lblIdCurso);

        txtIdCurso = new JTextField();
        txtIdCurso.setBounds(130, 40, 200, 20);
        add(txtIdCurso);

        btnInserir = new JButton("Inserir Fase");
        btnInserir.setBounds(10, 70, 140, 25);
        add(btnInserir);

        btnInserir.addActionListener((ActionEvent e) -> {
            try {
                Fases f = new Fases();
                f.setNumeroFase(Integer.parseInt(txtNumeroFase.getText()));
                f.setIdCursos(Integer.parseInt(txtIdCurso.getText()));
                fasesDAO.insert(f);
                JOptionPane.showMessageDialog(null, "Fase inserida com sucesso!");
                txtNumeroFase.setText("");
                txtIdCurso.setText("");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao inserir fase");
            }
        });

        JLabel lblExcluir = new JLabel("ID da Fase para excluir:");
        lblExcluir.setBounds(10, 110, 180, 20);
        add(lblExcluir);

        txtIdExcluir = new JTextField();
        txtIdExcluir.setBounds(180, 110, 50, 20);
        add(txtIdExcluir);

        btnExcluir = new JButton("Excluir Fase");
        btnExcluir.setBounds(10, 140, 140, 25);
        add(btnExcluir);

        btnExcluir.addActionListener((ActionEvent e) -> {
            try {
                int id = Integer.parseInt(txtIdExcluir.getText());
                fasesDAO.deleteById(id);
                JOptionPane.showMessageDialog(null, "Fase excluída com sucesso!");
                txtIdExcluir.setText("");
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(
                        null,
                        "Não é possível excluir esta fase.\nExistem fases vinculadas a ela.",
                        "Erro de Integridade",
                        JOptionPane.WARNING_MESSAGE
                );
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao excluir fase");
            }
        });
    }
}
