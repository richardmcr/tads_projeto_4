package uni9.projetopraticoemsistemas.myhealth.lembretes.model.entity;

import static androidx.room.ForeignKey.CASCADE;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.login.model.entity.UsuarioEntity;


@Entity(tableName = "lembrete",
        foreignKeys = {
                @ForeignKey(entity = MedicamentoEntity.class,
                        parentColumns = "idProduto",
                        childColumns = "idMedicamento",
                        onDelete = CASCADE),
                @ForeignKey(entity = UsuarioEntity.class,
                        parentColumns = "id",
                        childColumns = "idUsuario",
                        onDelete = CASCADE)})
public class LembreteEntity {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    @ColumnInfo(index = true)
    private Long idUsuario;

    @ColumnInfo(index = true)
    private Long idMedicamento;

    private String detalhes;

    private Long dataInicio;

    private Long duracao;

    private Long intervalo;

    private Boolean alertas;

    @ColumnInfo(defaultValue = "CURRENT_TIMESTAMP")
    private Long dataCriacao;

    private Long proximaDose;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(Long idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public Long getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Long dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Long getDuracao() {
        return duracao;
    }

    public void setDuracao(Long duracao) {
        this.duracao = duracao;
    }

    public Long getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(Long intervalo) {
        this.intervalo = intervalo;
    }

    public Boolean getAlertas() {
        return alertas;
    }

    public void setAlertas(Boolean alertas) {
        this.alertas = alertas;
    }

    public Long getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Long dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Long getProximaDose() {
        return proximaDose;
    }

    public void setProximaDose(Long proximaDose) {
        this.proximaDose = proximaDose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LembreteEntity that = (LembreteEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(idUsuario, that.idUsuario) && Objects.equals(idMedicamento, that.idMedicamento) && Objects.equals(detalhes, that.detalhes) && Objects.equals(dataInicio, that.dataInicio) && Objects.equals(duracao, that.duracao) && Objects.equals(intervalo, that.intervalo) && Objects.equals(alertas, that.alertas) && Objects.equals(dataCriacao, that.dataCriacao) && Objects.equals(proximaDose, that.proximaDose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUsuario, idMedicamento, detalhes, dataInicio, duracao, intervalo, alertas, dataCriacao, proximaDose);
    }

    @NonNull
    @Override
    public String toString() {
        return "LembreteEntity{" +
                "id=" + id +
                ", idUsuario=" + idUsuario +
                ", idMedicamento=" + idMedicamento +
                ", detalhes='" + detalhes + '\'' +
                ", dataInicio=" + dataInicio +
                ", duracao=" + duracao +
                ", intervalo=" + intervalo +
                ", alertas=" + alertas +
                ", dataCriacao=" + dataCriacao +
                ", proximaDose=" + proximaDose +
                '}';
    }
}
