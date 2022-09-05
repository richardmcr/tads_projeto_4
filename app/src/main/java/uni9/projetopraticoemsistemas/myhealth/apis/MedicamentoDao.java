package uni9.projetopraticoemsistemas.myhealth.apis;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

import uni9.projetopraticoemsistemas.myhealth.model.entity.MedicamentoEntity;

@Dao
public interface MedicamentoDao {

    @Insert
    void Insert(MedicamentoEntity medicamentoEntity);

    @Update
    void Update(MedicamentoEntity medicamentoEntity);
}
