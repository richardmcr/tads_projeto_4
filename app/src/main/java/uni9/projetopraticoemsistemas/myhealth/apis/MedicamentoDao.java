package uni9.projetopraticoemsistemas.myhealth.apis;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import uni9.projetopraticoemsistemas.myhealth.model.entity.MedicamentoEntity;

@Dao
public interface MedicamentoDao {

    @Query("SELECT * FROM medicamento WHERE idProduto = :idProduto ")
    MedicamentoEntity FindMedicamentoById(Long idProduto);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void Insert(MedicamentoEntity medicamentoEntity);

    @Update
    void Update(MedicamentoEntity medicamentoEntity);
}
