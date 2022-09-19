package uni9.projetopraticoemsistemas.myhealth.eventos;

import androidx.annotation.NonNull;

import uni9.projetopraticoemsistemas.myhealth.lembretes.model.Lembrete;
import uni9.projetopraticoemsistemas.myhealth.login.model.Usuario;

public abstract class Eventos<T> {

    private final T data;

    private Eventos(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    @NonNull
    public String toString() {
        return "Eventos{" +
                "evento='" + this.getClass().getSimpleName() + "', " +
                "data=" + data +
                '}';
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

    public final static class UsuarioSalvo<T> extends Eventos<T> {
        public UsuarioSalvo() {
            super(null);
        }
    }

    public final static class UsuarioLogado extends Eventos<Usuario> {
        public UsuarioLogado(Usuario usuario) {
            super(usuario);
        }
    }

    public static class Logout<T> extends Eventos<T> {
        public Logout() {
            super(null);
        }
    }
}
