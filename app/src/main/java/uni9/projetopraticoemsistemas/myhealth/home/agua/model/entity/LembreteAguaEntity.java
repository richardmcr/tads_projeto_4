package uni9.projetopraticoemsistemas.myhealth.home.agua.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity(tableName = "lembrete_agua")
public class LembreteAguaEntity {

    @PrimaryKey
    private Long idUsuario;
    private Long metaDiaria;
    private String horaInicio;
    private String horaFim;
    private Boolean alertas;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getMetaDiaria() {
        return metaDiaria;
    }

    public void setMetaDiaria(Long metaDiaria) {
        this.metaDiaria = metaDiaria;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(String horaFim) {
        this.horaFim = horaFim;
    }

    public Boolean getAlertas() {
        return alertas;
    }

    public void setAlertas(Boolean alertas) {
        this.alertas = alertas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LembreteAguaEntity that = (LembreteAguaEntity) o;
        return Objects.equals(idUsuario, that.idUsuario) && Objects.equals(metaDiaria, that.metaDiaria) && Objects.equals(horaInicio, that.horaInicio) && Objects.equals(horaFim, that.horaFim) && Objects.equals(alertas, that.alertas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, metaDiaria, horaInicio, horaFim, alertas);
    }

    @NonNull
    @Override
    public String toString() {
        return "LembreteAguaEntity{" +
                "idUsuario=" + idUsuario +
                ", metaDiaria=" + metaDiaria +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFim='" + horaFim + '\'' +
                ", alertas=" + alertas +
                '}';
    }
}
