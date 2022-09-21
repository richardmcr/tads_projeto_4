package uni9.projetopraticoemsistemas.myhealth.login.apis;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import uni9.projetopraticoemsistemas.myhealth.login.model.entity.UsuarioEntity;

@Dao
public interface UsuarioDao {

    @Query("SELECT * FROM usuario WHERE id = :id ")
    UsuarioEntity getUsuarioById(Long id);

    @Query("SELECT * FROM usuario WHERE email = :email ")
    UsuarioEntity findUsuarioByEmail(String email);

    @Query("INSERT INTO usuario (nome, email, senha) VALUES (:nome, :email, :senha) ")
    long insert(String nome, String email, String senha);

    @Update
    int update(UsuarioEntity usuarioEntity);
}
