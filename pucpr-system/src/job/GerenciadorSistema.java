package job;

import entities.Aluno;
import entities.Curso;
import entities.Disciplina;
import entities.Professor;
import excessoes.AlunoNaoEncontrado;
import excessoes.CursoNaoEncontrado;
import excessoes.DiscplinaNaoEncontrada;
import excessoes.ProfessorNaoEncontrado;

import java.io.*;
import java.util.ArrayList;

public class GerenciadorSistema implements Serializable{
    private ArrayList<Aluno> alunos;
    private ArrayList<Professor> professores;
    private ArrayList<Curso> cursos;
    private ArrayList<Disciplina> disciplinas;

    private int nextMatricula;
    private int nextProfessorId;
    private int nextCursoId;
    private int nextDisciplinaId;

    public GerenciadorSistema(){
        this.alunos = new ArrayList<>();
        this.professores =  new ArrayList<>();
        this.cursos = new ArrayList<>();
        this.disciplinas = new ArrayList<>();

        this.nextMatricula = 1001;
        this.nextProfessorId = 1;
        this.nextCursoId = 1;
        this.nextDisciplinaId = 1;
    }

    public boolean cadastrarAluno(Aluno aluno){
        for(Aluno a : alunos) {
            if (a.getCpf().equals(aluno.getCpf())) {
                return false;
            }
        }
        aluno.setMatricula(this.nextMatricula);
        this.nextMatricula++;
        alunos.add(aluno);
        return true;
    }

    public boolean cadastrarProfessor(Professor professor){
        for(Professor p : professores) {
            if (p.getCpf().equals(professor.getCpf())) {
                return false;
            }
        }
        professor.setId(this.nextProfessorId);
        this.nextProfessorId++;
        professores.add(professor);
        return true;
    }

    public boolean criarCurso(Curso curso){
        // A verificação de duplicidade por código do usuário foi removida.
        // O ID garantirá a unicidade.
        curso.setId(this.nextCursoId);
        curso.setCodigo(String.valueOf(this.nextCursoId)); // Define o código com base no ID
        this.nextCursoId++;
        cursos.add(curso);
        return true;
    }

    public boolean criarDisciplina(Disciplina disciplina){
        // A verificação de duplicidade por código do usuário foi removida.
        disciplina.setId(this.nextDisciplinaId);
        disciplina.setCodigo(String.valueOf(this.nextDisciplinaId)); // Define o código com base no ID
        this.nextDisciplinaId++;
        disciplinas.add(disciplina);
        return true;
    }

    public String buscarAluno(int matricula) throws AlunoNaoEncontrado {
        for(Aluno aluno : alunos){
            if(aluno.getMatricula() == matricula){ return aluno.exibirInformacoes(); }
        }
        throw new AlunoNaoEncontrado("Aluno com matricula: " + matricula + ", nao foi encontrado");
    }

    public String buscarProfessor(String cpf) throws ProfessorNaoEncontrado {
        for(Professor professor : professores){
            if(professor.getCpf().equals(cpf)){ return professor.exibirInformacoes(); }
        }
        throw new ProfessorNaoEncontrado("Professor com cpf: " + cpf + ", nao foi encontrado");
    }

    // A busca ainda funciona com o código, que agora é a string do ID.
    public String buscaCurso(String codigo) throws CursoNaoEncontrado {
        for(Curso curso : cursos){
            if(curso.getCodigo().equals(codigo)){ return curso.toString(); }
        }
        throw new CursoNaoEncontrado("Curso com ID: " + codigo + ", nao foi encontrado");
    }

    public String buscaDiscplina(String codigo) throws DiscplinaNaoEncontrada {
        for(Disciplina disciplina : disciplinas){
            if(disciplina.getCodigo().equals(codigo)){ return disciplina.toString() + " | Carga Horária: " + disciplina.getCargaHoraria() + "h";}
        }
        throw new DiscplinaNaoEncontrada("Discplina com ID: " + codigo + ", nao encontrada");
    }

    //PERSISTENCIA DE DADOOOOSSSSSSSSSSSSSSSS (somente para os tung tung tungzitos)
    public void carregarAlunosDeArquivo(String nomeArquivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    Aluno aluno = new Aluno(data[0], data[1], data[2], data[3]);
                    this.cadastrarAluno(aluno);
                }
            }
            System.out.println("Alunos carregados do arquivo: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao carregar alunos do arquivo: " + e.getMessage());
        }
    }

    //PERSISTENCIA DE OBJETOS (ARRAYS DESTA CLASSE )
    public void salvarDados(String nomeArquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(this.alunos);
            oos.writeObject(this.professores);
            oos.writeObject(this.cursos);
            oos.writeObject(this.disciplinas);
            oos.writeObject(this.nextMatricula);
            oos.writeObject(this.nextProfessorId);
            oos.writeObject(this.nextCursoId);
            oos.writeObject(this.nextDisciplinaId);
            System.out.println("Dados salvos com sucesso em: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void carregarDados(String nomeArquivo){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            this.alunos = (ArrayList<Aluno>) ois.readObject();
            this.professores = (ArrayList<Professor>) ois.readObject();
            this.cursos = (ArrayList<Curso>) ois.readObject();
            this.disciplinas = (ArrayList<Disciplina>) ois.readObject();
            this.nextMatricula = (int) ois.readObject();
            this.nextProfessorId = (int) ois.readObject();
            this.nextCursoId = (int) ois.readObject();
            this.nextDisciplinaId = (int) ois.readObject();
            System.out.println("Dados carregados com sucesso de: " + nomeArquivo);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            this.alunos = new ArrayList<>();
            this.professores = new ArrayList<>();
            this.cursos = new ArrayList<>();
            this.disciplinas = new ArrayList<>();
            this.nextMatricula = 1001;
            this.nextProfessorId = 1;
            this.nextCursoId = 1;
            this.nextDisciplinaId = 1;
        }
    }

    //mostrar arrays-------------------------------------------------------------------------------------------------------------------
    public void mostrarAlunos(){
        System.out.println("-----------ALUNOS------------");
        for(Aluno aluno : alunos){
            System.out.println(aluno.exibirInformacoes());
            System.out.println();
        }

        System.out.println("----------------------------------");
    }

    public void mostrarProfessores(){
        System.out.println("-----------Professores------------");
        for(Professor professor : professores){
            System.out.println(professor.exibirInformacoes());
            System.out.println();
        }

        System.out.println("----------------------------------");
    }

    public void mostrarCursos(){
        System.out.println("-------------Cursos-------------");
        for(Curso curso : cursos){
            System.out.println(curso.toString());
            System.out.println();
        }
    }

    public void mostrarDisciplinas(){
        System.out.println("--------------Disciplinas----------------");
        for(Disciplina disciplina : disciplinas){
            System.out.println(disciplina.toString());
            System.out.println();
        }
    }

    //metodos get dos arrays (pode ser util mais para frente)
    public ArrayList<Aluno> getAlunos() { return this.alunos;}
    public ArrayList<Professor> getProfessores() {return professores;}
    public ArrayList<Curso> getCursos() {return cursos;}
    public ArrayList<Disciplina> getDisciplinas() {return disciplinas;}
}