package uni9.projetopraticoemsistemas.myhealth.model.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

public class LembreteJoinMedicamento {

    @Embedded
    private LembreteEntity lembreteEntity;
    @Relation(
            parentColumn = "idMedicamento",
            entityColumn = "idProduto"
    )
    private MedicamentoEntity medicamentoEntity;

    public LembreteEntity getLembreteEntity() {
        return lembreteEntity;
    }

    public void setLembreteEntity(LembreteEntity lembreteEntity) {
        this.lembreteEntity = lembreteEntity;
    }

    public MedicamentoEntity getMedicamentoEntity() {
        return medicamentoEntity;
    }

    public void setMedicamentoEntity(MedicamentoEntity medicamentoEntity) {
        this.medicamentoEntity = medicamentoEntity;
    }
}
