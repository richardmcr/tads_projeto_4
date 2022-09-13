package uni9.projetopraticoemsistemas.myhealth.apis;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import uni9.projetopraticoemsistemas.myhealth.model.entity.MedicamentoEntity;

@Dao
public interface MedicamentoDao {

    @Query("SELECT * FROM medicamento WHERE idProduto = :idProduto ")
    MedicamentoEntity findMedicamentoById(Long idProduto);

    @Query("SELECT * FROM medicamento WHERE nomeProduto like '%'||:nome||'%' ")
    List<MedicamentoEntity> findMedicamentoByNome(String nome);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MedicamentoEntity medicamentoEntity);

    @Update
    void update(MedicamentoEntity medicamentoEntity);
}
