package uni9.projetopraticoemsistemas.myhealth.home.agua.apis;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import uni9.projetopraticoemsistemas.myhealth.home.agua.model.entity.LembreteAguaEntity;

@Dao
public interface LembreteAguaDao {

    @Query("SELECT * FROM lembrete_agua WHERE idUsuario = :idUsuario")
    LembreteAguaEntity getLembreteAgua(Long idUsuario);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(LembreteAguaEntity lembreteAguaEntity);
}
