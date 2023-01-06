import java.util.ArrayList;
import java.util.Map;

public class GeradorDeRelatorio{
    public static String relatorioMediasTurmas(Escola escola){
        String toReturn = "";
        for (Turma turma : escola.turmas.values()) {
            toReturn = toReturn + turma.getNome() + ": ";
            float mediaAlunos = 0;
            for(Aluno a: turma.getAlunos()){
                float sum = 0;
                for(Float[] notas : a.getNotas().values()){
                    sum = sum + notas[0]+ notas[1]+ notas[2]+ notas[3];
                    sum = sum /4;
                }
                sum = sum /a.getNotas().size();
                mediaAlunos+=sum;
            }
            mediaAlunos = mediaAlunos/turma.getAlunos().size();
            toReturn = toReturn + mediaAlunos +"\n";
        }
        return toReturn;
    }
    public static String relatorioMediasDisciplinas(Escola escola){
        String toReturn = "";
        for (Disciplina disciplina : escola.disciplinas.values()) {
            toReturn = toReturn + disciplina.getNome() + ": ";
            float media = 0;
            for(int a = 0; a<=3; a++){
                media = 0;
                a++;
                toReturn = toReturn + a + " Unidade - ";
                a--;
                for (Float[] notas : disciplina.getNotas().values()) {
                    media += notas[a];
                }
                media = media / disciplina.getNotas().size();
                toReturn += media;
                toReturn += " ; ";
            }
            toReturn+="\n";
        }
        return toReturn;
    }
    public static String relatorioAprovados(Escola escola){
        String toReturn = "APROVADOS\n";
        boolean passou = true;
        for (Turma turma : escola.turmas.values()) {
            toReturn = toReturn + turma.getNome() + ": ";
            for (Aluno aluno :turma.getAlunos()){
               var notas = aluno.getNotas();
               for (Float[] n : aluno.getNotas().values()){
                    if (n[0]+n[1]+n[2]+n[3]<28){
                        passou = false;
                        break;
                    }
               }
               if (passou)
                toReturn = toReturn+aluno.getNome()+" ; ";
            }
            toReturn+="\n";
        }
        return toReturn;
    }
    public static String relatorioReprovados(Escola escola){
        String toReturn = "REPROVADOS \n";
        for (Disciplina disciplina : escola.disciplinas.values()) {
            toReturn = toReturn + disciplina.getNome() + ": ";
            for (Map.Entry<Aluno, Float[]> pair:disciplina.getNotas().entrySet()){
                var n = pair.getValue();
                if(n[0]+n[1]+n[2]+n[3]<28){
                    toReturn = toReturn + pair.getKey().getNome() + " ; ";
                }
            }
            toReturn+="\n";
        }
        return toReturn;
    }
    public static String relatorioDocentes(Escola escola){
        String toReturn = "PROFESSORES E SUAS DISCIPLINAS \n";
        for (Docente doc : escola.docentes.values()){
            toReturn = toReturn + doc.getNome() + ": ";
            for(Disciplina d : doc.getDisciplinas()){
                toReturn = toReturn + d.getNome() + " ; ";
            }
            toReturn+="\n";
        }
        
        return toReturn;
    }
}
