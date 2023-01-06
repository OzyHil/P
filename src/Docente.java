import java.util.ArrayList;

public class Docente extends Entidade{
    private ArrayList<Disciplina> disciplinas;
    private String matricula,dataNascimento;
    
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public String getMatricula() {
        return matricula;
    }

    public Docente(String nome, int codigo,String mat, String dataNas) {
        super(nome, codigo);
        disciplinas = new ArrayList<Disciplina>();
        matricula = mat;
        dataNascimento = dataNas;
        if(mat == null){
            mat = "#9999";
        }

        if (dataNascimento == null){
            dataNascimento = "01/01/1111";
        }
    }

    public Docente(String base) {
        super(base);
        try{
        disciplinas = new ArrayList<Disciplina>();
        String[] tierOne = base.split(";");
        matricula = tierOne[2];
        dataNascimento = tierOne [3];
        } catch (Exception ex){
            
        }
    }

    public ArrayList<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    @Override
    public String toCsv() {
        String toReturn = "";
        toReturn = toReturn + getCodigo() + ";"+ getNome()+";"+matricula+";"+dataNascimento;
        return toReturn;
    }

    public void addDisciplina(Disciplina toAdd){
        disciplinas.add(toAdd);
    }
    
    public void removeDisciplina(Disciplina toRemove){
        disciplinas.remove(toRemove);
    }
}
