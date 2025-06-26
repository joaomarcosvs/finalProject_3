package DataBase.Model;

public class Disciplinas {

    private int id;
    private int codigoDisciplina;
    private String nomeDisciplina;
    private String diaSemana;
    private int quantProfessores;
    private int idFases;

    public Disciplinas() {

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getCodigoDisciplina() {
        return codigoDisciplina;
    }
    public void setCodigoDisciplina(int codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }
    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getDiaSemana() {
        return diaSemana;
    }
    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public int getQuantProfessores() {
        return quantProfessores;
    }
    public void setQuantProfessores(int quantProfessores) {
        this.quantProfessores = quantProfessores;
    }

    public int getIdFases() {
        return idFases;
    }
    public void setIdFases(int idFases) {
        this.idFases = idFases;
    }
}
