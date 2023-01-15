import java.util.ArrayList;

public class Turma extends Entidade{
    private ArrayList<Aluno> alunos;
    public ArrayList<Aluno> getAlunos() {
        return alunos;
    }

    private int ano;

    

    public Turma(String nome, int codigo) {
        super(nome, codigo);
        alunos = new ArrayList<Aluno>();
    }
    public Turma(String base, Escola escola) {
        super(base);
        alunos = new ArrayList<Aluno>();
        var tierOne = base.split(";");
        try{
        for (String tTwo : tierOne[2].split(",")){
            alunos.add(escola.alunos.get(Integer.parseInt(tTwo)));
        }
    } catch (Exception ex){
        System.out.println("Turma Incompleta sendo carregada");
    }
    }
    public boolean adicionarAluno(Aluno aluno,Escola escola){
        if (alunos.contains(aluno))
            return false;
        escola.turmas.get(aluno.getTurma()).removerAluno(aluno,escola);
        alunos.add(aluno);
        aluno.setTurma(this.getCodigo());

        return true;
    }
    public boolean removerAluno(Aluno aluno,Escola escola){
        if (!alunos.contains(aluno))
            return false;
        alunos.remove(aluno);
        if(this.getCodigo()!=0){
        escola.turmas.get(0).getAlunos().add(aluno);
        }
        return true;
    }
    public boolean removerAluno(int aluno,Escola escola){
        if(this.getCodigo()!=0){
        escola.turmas.get(0).getAlunos().add(alunos.get(aluno));
        }
        alunos.remove(aluno);
        return true;
    }

    public String mapearAlunos(){
        String toReturn = "";
        for (Aluno aluno : alunos) {
            toReturn = toReturn + "Código: "+aluno.getCodigo()+" Nome: " + aluno.getNome();
        }

        return toReturn;
    }

    @Override
    public String toCsv() {
        String toReturn = "";
        toReturn = toReturn + getCodigo() + ";"+ getNome() +";";
        for(Aluno a : alunos){
            try{
            toReturn+=a.getCodigo();
            toReturn+=",";
            } catch(Exception ex){
                
            }
        }
        return toReturn;
    }
    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
