package DataBase.Model;

public class Cursos {

    private int id;
    private String nomeCurso;
    private int faseInicial;
    private int faseFinal;

    public Cursos(){

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }
    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public int getFaseInicial() {
        return faseInicial;
    }
    public void setFaseInicial(int faseInicial) {
        this.faseInicial = faseInicial;
    }

    public int getFaseFinal() {
        return faseFinal;
    }
    public void setFaseFinal(int faseFinal) {
        this.faseFinal = faseFinal;
    }
}
