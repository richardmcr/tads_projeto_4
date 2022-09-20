package uni9.projetopraticoemsistemas.myhealth.login.repositories;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import uni9.projetopraticoemsistemas.myhealth.MyHealthDatabase;
import uni9.projetopraticoemsistemas.myhealth.login.apis.UsuarioDao;
import uni9.projetopraticoemsistemas.myhealth.login.mappers.UsuarioMapper;
import uni9.projetopraticoemsistemas.myhealth.login.model.Usuario;
import uni9.projetopraticoemsistemas.myhealth.login.model.entity.UsuarioEntity;

public class UsuarioRepository {

    private final UsuarioMapper usuarioMapper;

    private final SharedPreferences usuarioSharedPreferences;
    private final SharedPreferences.Editor editor;

    private final UsuarioDao usuarioDao;

    private final MutableLiveData<Usuario> usuarioLiveData;
    private final MutableLiveData<Long> novoUsuarioLiveData;

    @Inject
    public UsuarioRepository(@ApplicationContext Context context,
                             UsuarioDao usuarioDao,
                             UsuarioMapper usuarioMapper) {
        this.usuarioDao = usuarioDao;
        this.usuarioMapper = usuarioMapper;
        this.usuarioLiveData = new MutableLiveData<>();
        this.novoUsuarioLiveData = new MutableLiveData<>();
        this.usuarioSharedPreferences = context.getSharedPreferences("MyHealthUsuario", 0);
        this.editor = this.usuarioSharedPreferences.edit();
    }

    /* Logar */
    public void obterUsuario(String email) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            UsuarioEntity usuarioEntity = usuarioDao.findUsuarioByEmail(email);
            Usuario usuario = usuarioMapper.usuarioEntityToUsuario(usuarioEntity);
            if(Objects.isNull(usuario)){
                usuario = new Usuario();
                usuario.setId(-1L);
            }
            usuarioLiveData.postValue(usuario);
        });
    }

    public void atualizarUsuarioLogado(Usuario usuario) {
        editor.putLong("usuario", usuario.getId());
        editor.commit();
    }
    /* Fim Logar */

    public void obterUsuario(Long id) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            UsuarioEntity usuarioEntity = usuarioDao.getUsuarioById(id);
            usuarioLiveData.postValue(usuarioMapper.usuarioEntityToUsuario(usuarioEntity));
        });
    }

    public void sair() {
        editor.putLong("usuario", 0L);
        editor.commit();
        usuarioLiveData.postValue(null);
    }

    /* Cadastrar novo usuário */
    public void existeUsuario(String email) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            UsuarioEntity usuarioEntity = usuarioDao.findUsuarioByEmail(email);
            novoUsuarioLiveData.postValue(Objects.isNull(usuarioEntity) ? 0L : -1L);
        });
    }

    public void inserirUsuario(Usuario usuario) {
        MyHealthDatabase.databaseWriteExecutor.execute(() -> {
            long id = usuarioDao.insert(usuario.getNome(), usuario.getEmail(), usuario.getSenha());
            novoUsuarioLiveData.postValue(id);
        });
    }
    /* Fim Cadastrar novo usuário */

    public Long getUsuarioLogado() {
        return usuarioSharedPreferences.getLong("usuario", 0L);
    }

    public MutableLiveData<Usuario> getUsuarioLiveData() {
        return usuarioLiveData;
    }

    public MutableLiveData<Long> getNovoUsuarioLiveData() {
        return novoUsuarioLiveData;
    }
}
