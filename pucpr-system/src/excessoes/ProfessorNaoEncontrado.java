package excessoes;

public class ProfessorNaoEncontrado extends RuntimeException {
    public ProfessorNaoEncontrado(String message) {
        super(message);
    }

    public ProfessorNaoEncontrado(String mensagem, Throwable causa){ super(mensagem, causa);}
}
