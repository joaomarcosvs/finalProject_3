package DataBase.Model;

public class Professores {

    private int id;
    private String nomeProfessor;
    private String titulo;

    public Professores() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNomeProfessor() {
        return nomeProfessor;
    }
    public void setNomeProfessor(String nome) {
        this.nomeProfessor = nome;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
}
