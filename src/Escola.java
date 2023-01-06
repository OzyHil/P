import java.nio.file.InvalidPathException;
import java.nio.file.NoSuchFileException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.crypto.Data;

public class Escola{
    public LinkedHashMap<Integer,Docente> docentes = new LinkedHashMap<Integer,Docente>();
    public LinkedHashMap<Integer,Disciplina> disciplinas = new LinkedHashMap<Integer,Disciplina>();
    public LinkedHashMap<Integer,Aluno> alunos = new LinkedHashMap<Integer,Aluno>();
    public LinkedHashMap<Integer,Turma> turmas = new LinkedHashMap<Integer,Turma>();
    public int maiorAluno,maiorDocente,maiorDisciplina,maiorTurma;
    private int numeroRelatorios;
    public void setNumeroRelatorios(int numeroRelatorios) {
        this.numeroRelatorios = numeroRelatorios;
    }

    public int getNumeroRelatorios() {
        return numeroRelatorios;
    }

    private GerenteDeArquivo ga = new GerenteDeArquivo();

    //Metódos
    //TODO fazer os métodos de adição e adição darem throws qnd acessar um item q nao existem
    public Aluno adicionarAluno(String nome,String mat, String datNas){
        Aluno aluno = new Aluno(nome,maiorAluno,mat,datNas);
        alunos.put(maiorAluno, aluno);
        turmas.get(0).adicionarAluno(aluno, this);
        maiorAluno++;
        return aluno;
    }

    public void removerAluno(int codigo) throws NoSuchKeyException{
        if (!alunos.containsKey(codigo))
            throw new NoSuchKeyException();
        else
            for(int i: alunos.get(codigo).getNotas().keySet()){
                disciplinas.get(i).removerAluno(alunos.get(codigo));
                turmas.get(alunos.get(codigo).getTurma()).removerAluno(alunos.get(codigo),this);
            }
            alunos.get(codigo).setTurma(0);
            alunos.remove(codigo);
    }

    public Docente adicionarDocente(String nome,String mat, String datNas){
        Docente docente = new Docente(nome, maiorDocente,mat,datNas);
        docentes.put(maiorDocente, docente);
        maiorDocente++;
        return docente;
    }

    public void removerDocente(int codigo) throws NoSuchKeyException{
        if (!docentes.containsKey(codigo))
            throw new NoSuchKeyException();
        else
            for(Disciplina d: docentes.get(codigo).getDisciplinas()){
                d.removerDocente(docentes.get(codigo));
            }
            docentes.remove(codigo);
    }

    public Disciplina adicionarDisciplina(String nome){
        Disciplina disc =  new Disciplina(nome, maiorDisciplina);
        disciplinas.put(maiorDisciplina, disc);
        maiorDisciplina++;
        return disc;
    }

    public void removerDisciplina(int codigo) throws NoSuchKeyException{
        if (!disciplinas.containsKey(codigo))
            throw new NoSuchKeyException();
        else{
            for(Docente d: disciplinas.get(codigo).getDocentes()){
                d.removeDisciplina(disciplinas.get(codigo));
            }
            for (Aluno a: disciplinas.get(codigo).getNotas().keySet()){
                a.getNotas().remove(disciplinas.get(codigo).getCodigo());
            }
            disciplinas.remove(codigo);
        }
    }

    public Turma adicionarTurma(String nome){
        Turma turma = new Turma(nome,maiorTurma);
        turmas.put(maiorTurma, turma);
        maiorTurma++;
        return turma;
    }

    public void removerTurma(int codigo) throws NoSuchKeyException{
        if (!turmas.containsKey(codigo))
            throw new NoSuchKeyException();
        else{
            for (Aluno a: turmas.get(codigo).getAlunos()){
                a.setTurma(0);
            }
            turmas.remove(codigo);
        }
    }

    public void carregar() throws InvalidPathException, NoSuchFileException{
        Aluno a;
        try{
        for (String base : ga.carregarAlunos()) {
            a = new Aluno(base);
            alunos.put(a.getCodigo(), a); 
        }
        } catch (NullPointerException ex){
        }
        Docente doc;
        try{
        for (String base : ga.carregarDocentes()) {
            doc = new Docente(base);
            docentes.put(doc.getCodigo(), doc); 
        }
        } catch (NullPointerException ex){
        }
        Turma t;
        try{
        for (String base : ga.carregarTurmas()) {
            t = new Turma(base,this);
            turmas.put(t.getCodigo(), t); 
        }
        } catch (NullPointerException ex){
        }
        Disciplina dic;
        try{
        for (String base : ga.carregarDisciplinas()) {
            dic = new Disciplina(base,this);
            disciplinas.put(dic.getCodigo(), dic); 
        }
        } catch(NullPointerException ex){
        }
        try{
        String[] meta = ga.carregarMeta().split(";");
        numeroRelatorios = Integer.parseInt(meta[0]);
        maiorAluno = Integer.parseInt(meta[1]);
        maiorDocente = Integer.parseInt(meta[2]);
        maiorTurma = Integer.parseInt(meta[3]);
        maiorDisciplina = Integer.parseInt(meta[4]);
        System.out.println("");
        } catch (Exception ex){
        numeroRelatorios = 100;
        maiorAluno = 100;
        maiorDocente = 100;
        maiorTurma = 100;
        maiorDisciplina = 100;
        }
    }

    public void salvar(){
       ga.escrever(this);
    }
    
}   
