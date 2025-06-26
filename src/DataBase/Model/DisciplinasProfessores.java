package DataBase.Model;

public class DisciplinasProfessores{

    private int id;
    private int idDisciplinas;
    private int idProfessores;

    public DisciplinasProfessores(){

    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getIdDisciplinas() {
        return idDisciplinas;
    }
    public void setIdDisciplinas(int idDisciplinas) {
        this.idDisciplinas = idDisciplinas;
    }

    public int getIdProfessores() {
        return idProfessores;
    }
    public void setIdProfessores(int idProfessores) {
        this.idProfessores = idProfessores;
    }
}
