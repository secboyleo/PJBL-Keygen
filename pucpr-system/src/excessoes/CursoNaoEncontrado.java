package excessoes;

public class CursoNaoEncontrado extends Exception {
    public CursoNaoEncontrado(String message) {
        super(message);
    }
    public CursoNaoEncontrado(String mensagem, Throwable causa){super(mensagem, causa);}
}
