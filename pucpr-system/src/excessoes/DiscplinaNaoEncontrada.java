package excessoes;

public class DiscplinaNaoEncontrada extends RuntimeException {
    public DiscplinaNaoEncontrada(String message) {
        super(message);
    }
    public DiscplinaNaoEncontrada(String mensagem, Throwable causa){super(mensagem, causa);}
}
