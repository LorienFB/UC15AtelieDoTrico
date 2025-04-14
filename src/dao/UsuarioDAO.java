package dao;

import beans.Usuario;
import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    private Connection conn;
    private PreparedStatement st;

    public UsuarioDAO() {
        try {
            this.conn = new Conexao().getConexao();
        } catch (Exception e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }

    public void cadastrarUsuario(Usuario usuario) {
        int status;
        try {
            // Alterei o nome da tabela para 'usuario' e as colunas conforme sua tabela
            st = conn.prepareStatement("INSERT INTO usuario (nome_usuario, login_usuario, senha_usuario, tipo_usuario) VALUES (?, ?, ?, ?)");
            st.setString(1, usuario.getNomeUsuario());
            st.setString(2, usuario.getLoginUsuario());
            st.setString(3, usuario.getSenhaUsuario());
            st.setString(4, usuario.getTipoUsuario());

            status = st.executeUpdate();
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar usuário: " + ex.getMessage());
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    public Usuario buscarUsuarioPorId(int id) {
        Usuario usuario = null;
        try {
            // Alterei o nome da tabela para 'usuario' e os campos conforme sua tabela
            String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
            PreparedStatement st = conn.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));  // Alterei o nome do campo para 'id_usuario'
                usuario.setNomeUsuario(rs.getString("nome_usuario"));  // Alterei o nome do campo para 'nome_usuario'
                usuario.setLoginUsuario(rs.getString("login_usuario"));  // Alterei o nome do campo para 'login_usuario'
                usuario.setSenhaUsuario(rs.getString("senha_usuario"));  // Alterei o nome do campo para 'senha_usuario'
                usuario.setTipoUsuario(rs.getString("tipo_usuario"));  // Alterei o nome do campo para 'tipo_usuario'
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao buscar usuário por ID: " + ex.getMessage());
        }
        return usuario;
    }

    public Usuario validarLogin(String login, String senha) {
        Usuario usuario = null;
        // Alterei o nome da tabela para 'usuario' e os campos para os corretos
        String sql = "SELECT * FROM usuario WHERE login_usuario = ? AND senha_usuario = ?";  // 'login_usuario' e 'senha_usuario'

        try ( PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, login);
            st.setString(2, senha);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));  // Alterei o nome do campo para 'id_usuario'
                usuario.setNomeUsuario(rs.getString("nome_usuario"));  // Alterei o nome do campo para 'nome_usuario'
                usuario.setLoginUsuario(rs.getString("login_usuario"));  // Alterei o nome do campo para 'login_usuario'
                usuario.setSenhaUsuario(rs.getString("senha_usuario"));  // Alterei o nome do campo para 'senha_usuario'
                usuario.setTipoUsuario(rs.getString("tipo_usuario"));  // Alterei o nome do campo para 'tipo_usuario'
            }
        } catch (SQLException e) {
            System.out.println("Erro ao validar login: " + e.getMessage());
        }
        return usuario;
    }
}
