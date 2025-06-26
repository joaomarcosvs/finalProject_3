package Windows;

import DataBase.ConnectionFactory;
import DataBase.Dao.UsuarioDAO;
import DataBase.Model.Usuario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginWindow extends JFrame {

        private JPanel painelCabecalho;
        private JLabel lblCabecalho, lblUsuario, lblSenha;
        private JTextField txfUsuario, txfSenha;
        private JButton btnEntrar;

        public LoginWindow() throws SQLException {

            setTitle("Universidade");
            setSize(400,270);
            setLayout(null);
            setResizable(false);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            criarComponentes();
            setVisible(true);
        }

        public void criarComponentes(){
            painelCabecalho = new JPanel();
            painelCabecalho.setBounds(0, 0, 400, 50);
            painelCabecalho.setLayout(null);
            painelCabecalho.setBackground(new Color(60, 120, 180));
            getContentPane().add(painelCabecalho);

            lblCabecalho = new JLabel("Login");
            lblCabecalho.setBounds(170, 10, 270, 25);
            lblCabecalho.setForeground(Color.WHITE);
            lblCabecalho.setFont(new Font("SansSerif", Font.BOLD, 18));
            painelCabecalho.add(lblCabecalho);

            lblUsuario = new JLabel("Usuario:");
            lblUsuario.setBounds(20, 70, 250, 25);
            lblUsuario.setFont(new Font("SansSerif", Font.BOLD, 15));
            lblUsuario.setForeground(Color.RED);
            getContentPane().add(lblUsuario);

            txfUsuario = new JTextField();
            txfUsuario.setBounds(140, 70, 125, 25);
            getContentPane().add(txfUsuario);

            lblSenha = new JLabel("Senha:");
            lblSenha.setBounds(20, 105, 250, 25);
            lblSenha.setFont(new Font("SansSerif", Font.BOLD, 15));
            lblSenha.setForeground(Color.BLUE);
            getContentPane().add(lblSenha);

            txfSenha = new JTextField();
            txfSenha.setBounds(140, 105, 125, 25);
            getContentPane().add(txfSenha);

            btnEntrar = new JButton("Entrar");
            btnEntrar.setBounds(140, 150, 125, 30);
            getContentPane().add(btnEntrar);

            btnEntrar.addActionListener(e -> realizarLogin());

        }

    public void realizarLogin() {
        String usuario = txfUsuario.getText().trim();
        String senha = txfSenha.getText().trim();

        try {
            Connection conexao = ConnectionFactory.getConnection("localhost", "3306", "universidade", "root", "root");

            if (conexao != null) {
                UsuarioDAO dao = new UsuarioDAO(conexao);
                Usuario u = dao.selectWhere(usuario, senha);

                if (u != null) {
//                    JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
                    // Aqui você pode abrir uma nova janela ou prosseguir para o sistema
                    dispose(); // Fecha a janela de login

                    new ImportadorWindow(conexao).setVisible(true);


                } else {
                    JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Erro na conexão com o banco de dados");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new LoginWindow();
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erro ao iniciar o sistema: " + e.getMessage());
            }
        });
    }


}

