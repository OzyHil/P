
import java.util.ArrayList;
import java.util.Map;

public class GeradorDeRelatorio {

    public static String relatorioMediasTurmas(Escola escola, Turma t) {
        String toReturn = "MÉDIA DE NOTAS DA TURMA " + "[" + t.getCodigo() + "]: " + t.getNome() + "\n\n";

        float mediaAlunos = 0;
        int qtd = 0;
        for (Aluno a : t.getAlunos()) {
            for (Map.Entry<Integer, Float[]> par : a.getNotas().entrySet()) {
                Float[] n = par.getValue();
                mediaAlunos += (n[0] + n[1] + n[2] + n[3]) / 4;
                qtd++;
            }
        }
        if (!t.getAlunos().isEmpty() && qtd != 0) {
            mediaAlunos = mediaAlunos / (qtd * t.getAlunos().size());
        } else {
            mediaAlunos = 0f;
        }
        toReturn = toReturn + mediaAlunos + ";\n";
        return toReturn;
    }

    public static String relatorioMediasTodasTurmas(Escola escola) {
        String toReturn = "MÉDIA DE NOTAS EM CADA TURMA \n\n";
        float mediaAlunos = 0;
        int qtd = 0;
        for (Turma turma : escola.turmas.values()) {
            toReturn = toReturn + "[" + turma.getCodigo() + "]-" + turma.getNome() + ": ";

            if (turma.getAlunos().isEmpty()) {
                toReturn = toReturn + "0.0;\n";
            } else {
                for (Aluno a : turma.getAlunos()) {
                    for (Map.Entry<Integer, Float[]> par : a.getNotas().entrySet()) {
                        Float[] n = par.getValue();
                        mediaAlunos += (n[0] + n[1] + n[2] + n[3]) / 4;
                        qtd++;
                    }
                }
                if (!turma.getAlunos().isEmpty() && qtd != 0) {
                    mediaAlunos = mediaAlunos / (qtd * turma.getAlunos().size());
                } else {
                    mediaAlunos = 0f;
                }
              toReturn = toReturn + mediaAlunos + ";\n";  
            }
        }       
        return toReturn;
    }

    public static String relatorioAprovados(Escola escola, Turma t) {
        String toReturn = "APROVADOS NA TURMA " + "[" + t.getCodigo() + "]: " + t.getNome() + "\n\n";
        for (Aluno aluno : t.getAlunos()) {
            boolean passou = true;
            for (Float[] n : aluno.getNotas().values()) {
                if (n[0] + n[1] + n[2] + n[3] < 28) {
                    passou = false;
                    break;
                }
            }
            if (passou) {
                toReturn = toReturn + "[" + aluno.getCodigo() + "]" + aluno.getNome() + ";\n";
            }
        }
        return toReturn;
    }

    public static String relatorioTodasAprovados(Escola escola) {
        String toReturn = "APROVADOS POR TURMA\n\n";
        boolean passou = true;
        for (Turma turma : escola.turmas.values()) {
            toReturn = toReturn + "[" + turma.getCodigo() + "]-" + turma.getNome() + ": ";
            for (Aluno aluno : turma.getAlunos()) {
                var notas = aluno.getNotas();
                for (Float[] n : aluno.getNotas().values()) {
                    if (n[0] + n[1] + n[2] + n[3] < 28) {
                        passou = false;
                        break;
                    }
                }
                if (passou) {
                    toReturn = toReturn + "[" + aluno.getCodigo() + "]" + aluno.getNome() + "; ";
                }
            }
            toReturn += "\n\n";
        }
        return toReturn;
    }

    public static String relatorioMediasDisciplinas(Escola escola, Disciplina d) {
        String toReturn = "MÉDIA DE NOTAS DA DISCIPLINA " + "[" + d.getCodigo() + "]: " + d.getNome() + "\n\n";
        float media = 0;
        for (int a = 0; a <= 3; a++) {
            media = 0;
            a++;
            toReturn = toReturn + a + "U- ";
            a--;
            for (Float[] notas : d.getNotas().values()) {
                media += notas[a];
            }
            media = media / d.getNotas().size();
            toReturn += media;
            toReturn += "; ";
        }
        return toReturn;
    }

    public static String relatorioMediasTodasDisciplinas(Escola escola) {
        String toReturn = "MÉDIA POR DISCIPLINA\n\n";
        for (Disciplina disciplina : escola.disciplinas.values()) {
            toReturn = toReturn + "[" + disciplina.getCodigo() + "]-" + disciplina.getNome() + ": ";
            float media = 0;
            float sum = 0;
            for (int a = 0; a <= 3; a++) {
                media = 0;
                a++;
                toReturn = toReturn + a + "U- ";
                a--;

                if (!disciplina.getNotas().isEmpty()) {
                    for (Float[] notas : disciplina.getNotas().values()) {
                        media += notas[a];
                    }
                    media = media / disciplina.getNotas().size();
                    toReturn += media;
                    sum += media;
                    toReturn += "; ";
                    if (a == 3) {
                        toReturn += "Md- ";
                        toReturn += (sum / 4);
                        toReturn += "; ";
                    }
                } else {
                    toReturn += media;
                    toReturn += "; ";
                    if (a == 3) {
                        toReturn += "Md- ";
                        toReturn += media;
                        toReturn += "; ";
                    }
                }
            }
            toReturn += "\n";
        }
        return toReturn;
    }

    public static String relatorioReprovadosDisciplinas(Escola escola, Disciplina d) {
        String toReturn = "REPROVADOS NA DISCIPLINA " + "[" + d.getCodigo() + "]: " + d.getNome() + "\n\n";

        for (Map.Entry<Aluno, Float[]> pair : d.getNotas().entrySet()) {
            var n = pair.getValue();
            if (n[0] + n[1] + n[2] + n[3] < 28) {
                toReturn = toReturn + "[" + pair.getKey().getCodigo() + "]" + pair.getKey().getNome() + "; \n";
            }
        }
        return toReturn;
    }

    public static String relatorioReprovadosGeralDisciplinas(Escola escola) {
        String toReturn = "REPROVADOS POR DISCIPLINA\n\n";
        for (Disciplina disciplina : escola.disciplinas.values()) {
            toReturn = toReturn + "[" + disciplina.getCodigo() + "]-" + disciplina.getNome() + ": ";
            for (Map.Entry<Aluno, Float[]> pair : disciplina.getNotas().entrySet()) {
                var n = pair.getValue();
                if (n[0] + n[1] + n[2] + n[3] < 28) {
                    toReturn = toReturn + "[" + pair.getKey().getCodigo() + "]" + pair.getKey().getNome() + "; ";
                }
            }
            toReturn += "\n";
        }
        return toReturn;
    }

    public static String relatorioDocentes(Escola escola) {
        String toReturn = "PROFESSORES E SUAS DISCIPLINAS \n\n";
        for (Docente doc : escola.docentes.values()) {
            toReturn = toReturn + "[" + doc.getMatricula() + "]-" + doc.getNome() + ": ";
            for (Disciplina d : doc.getDisciplinas()) {
                toReturn = toReturn + "[" + d.getCodigo() + "]" + d.getNome() + "; ";
            }
            toReturn += "\n";
        }

        return toReturn;
    }

    public static String relatorioBoletim(Escola escola, Aluno a) {
        float media = 0;
        String toReturn = "BOLETIM DO ALUNO " + "[" + a.getCodigo() + "]: " + a.getNome().toUpperCase() + "\n\n";

        for (Map.Entry<Integer, Float[]> pair : a.getNotas().entrySet()) {
            Float[] notas = pair.getValue();
            Disciplina d = escola.disciplinas.get(pair.getKey());
            media = (notas[0] + notas[1] + notas[2] + notas[3]) / 4;
            toReturn += "\t" + d.getNome() + ": " + notas[0] + " ; " + notas[1] + " ; " + notas[2] + " ; " + notas[3] + " ; " + media + "\n";
        }
        return toReturn;
    }

    public static String relatorioBoletimTodosAlunos(Escola escola) {
        float media = 0;
        String toReturn = "BOLETIM POR ALUNO\n\n";
        for (Aluno a : escola.alunos.values()) {
            toReturn += "[" + a.getMatricula() + "]" + "-" + a.getNome() + "\n";
            for (Map.Entry<Integer, Float[]> pair : a.getNotas().entrySet()) {
                Float[] notas = pair.getValue();
                Disciplina d = escola.disciplinas.get(pair.getKey());
                media = (notas[0] + notas[1] + notas[2] + notas[3]) / 4;
                toReturn += "\t" + d.getNome() + ": " + notas[0] + " ; " + notas[1] + " ; " + notas[2] + " ; " + notas[3] + " ; " + media + "\n";
            }
            toReturn += "\n";
        }
        return toReturn;
    }
}
