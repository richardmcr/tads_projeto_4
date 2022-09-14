package uni9.projetopraticoemsistemas.myhealth.eventos;

import uni9.projetopraticoemsistemas.myhealth.model.Lembrete;

public abstract class Eventos<T> {

    private final T data;

    private Eventos(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public final static class LembreteRemovido extends Eventos<Lembrete> {
        public LembreteRemovido(Lembrete lembrete) {
            super(lembrete);
        }
    }

    public final static class LembreteRecuperado<T> extends Eventos<T> {
        public LembreteRecuperado() {
            super(null);
        }
    }

    public final static class NovoLembrete<T> extends Eventos<T> {
        public NovoLembrete() {
            super(null);
        }
    }

    public final static class LembreteSalvo extends Eventos<Integer> {
        public LembreteSalvo(Integer resultado) {
            super(resultado);
        }
    }

    public final static class MensagemErro extends Eventos<String> {
        public MensagemErro(String mensagem) {
            super(mensagem);
        }
    }

    public final static class LembretesCompletosRemovidos<T> extends Eventos<T> {
        public LembretesCompletosRemovidos() {
            super(null);
        }
    }

    public final static class ErroValidacao extends Eventos<String> {
        public ErroValidacao(String erro) {
            super(erro);
        }
    }
}
