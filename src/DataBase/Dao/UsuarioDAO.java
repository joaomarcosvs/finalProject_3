package DataBase.Dao;

import DataBase.Model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//CRUD -> SELECT, INSERT, UPDATE e DELETE
public class UsuarioDAO {

    private String selectAll = "SELECT * FROM universidade.tb_usuarios";
    private String selectWhere = "SELECT * FROM universidade.tb_usuarios WHERE usuario = ? AND senha = ?";
    private String insert = "INSERT INTO universidade.tb_usuarios (usuario, senha) VALUES (?, ?)";
    private String update = "UPDATE universidade.tb_usuarios SET senha = ? WHERE usuario = ?";
    private String deleteWhere = "DELETE FROM universidade.tb_usuarios WHERE usuario = ?";

    private PreparedStatement pstSelectAll;
    private PreparedStatement pstSelectWhere;
    private PreparedStatement pstInsert;
    private PreparedStatement pstUpdate;
    private PreparedStatement pstDeleteWhere;

    public UsuarioDAO(Connection conexao) throws SQLException {
        pstSelectAll = conexao.prepareStatement(selectAll);
        pstSelectWhere = conexao.prepareStatement(selectWhere);
        pstInsert = conexao.prepareStatement(insert);
        pstUpdate = conexao.prepareStatement(update);
        pstDeleteWhere = conexao.prepareStatement(deleteWhere);

    }

    public ArrayList<Usuario> selectAll() throws SQLException {

        ArrayList<Usuario> listaLocal = new ArrayList<Usuario>();

        ResultSet resultado = pstSelectAll.executeQuery();

        while (resultado.next()){
            Usuario usuario = new Usuario();
            usuario.setId(resultado.getInt("id"));
            usuario.setUsuario(resultado.getString("usuario"));
            usuario.setSenha(resultado.getString("senha"));

            listaLocal.add(usuario);

        }

        return listaLocal;
    }

    public Usuario selectWhere(String usuario, String senha) throws SQLException {

        pstSelectWhere.setString(1, usuario);
        pstSelectWhere.setString(2, senha);
        ResultSet resultado = pstSelectWhere.executeQuery();

        if(resultado.next()){
            Usuario u = new Usuario();
            u.setId(resultado.getInt("id"));
            u.setUsuario(resultado.getString("usuario"));
            u.setSenha(resultado.getString("senha"));
            return u;
        }

        return null;
    }

    public boolean insert(String usuario, String senha) throws SQLException {

        pstInsert.setString(1, usuario);
        pstInsert.setString(2, senha);
        //pstInsert.execute();
        return pstInsert.execute();
    }

    public void update(String usuario, String senha) throws SQLException {

        pstUpdate.setString(1, senha);
        pstUpdate.setString(2, usuario);
        pstUpdate.execute();
    }

    public void deleteWhere(String usuario) throws SQLException {

        pstDeleteWhere.setString(1, usuario);
        pstDeleteWhere.execute();
    }

}
