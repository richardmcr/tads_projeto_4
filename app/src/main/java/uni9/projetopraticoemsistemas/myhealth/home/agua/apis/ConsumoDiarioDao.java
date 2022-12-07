package uni9.projetopraticoemsistemas.myhealth.home.agua.apis;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import uni9.projetopraticoemsistemas.myhealth.home.agua.model.entity.ConsumoDiarioEntity;

@Dao
public interface ConsumoDiarioDao {

    @Query("SELECT * FROM consumo_diario WHERE idUsuario = :idUsuario AND data = :data")
    ConsumoDiarioEntity findConsumoDiarioByIdUsuarioAndData(Long idUsuario, String data);

    @Query("SELECT * FROM consumo_diario WHERE idUsuario = :idUsuario ORDER BY data DESC")
    List<ConsumoDiarioEntity> findAllConsumoDiarioByIdUsuario(Long idUsuario);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ConsumoDiarioEntity consumoDiarioEntity);
}
