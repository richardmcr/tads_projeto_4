package uni9.projetopraticoemsistemas.myhealth.perfil.apis;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import uni9.projetopraticoemsistemas.myhealth.perfil.model.entity.PerfilEntity;
import uni9.projetopraticoemsistemas.myhealth.perfil.model.entity.UsuarioJoinPerfil;

@Dao
public interface PerfilDao {

    @Transaction
    @Query("SELECT * \n" +
            "FROM usuario \n" +
            "LEFT JOIN perfil ON perfil.idUsuario = usuario.id \n" +
            "WHERE usuario.id = :idUsuario \n")
    UsuarioJoinPerfil getPerfilByIdUsuario(Long idUsuario);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(PerfilEntity perfilEntity);
}
