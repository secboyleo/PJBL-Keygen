package excessoes;

public class AlunoNaoEncontrado extends Exception {
    public AlunoNaoEncontrado(String message) {
        super(message);
    }
    public AlunoNaoEncontrado(String mensagem, Throwable causa){super(mensagem, causa);}


}
