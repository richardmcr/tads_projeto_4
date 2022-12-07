package uni9.projetopraticoemsistemas.myhealth.home.agua.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class LembreteAgua {

    private Long metaDiaria;
    private String horaInicio;
    private String horaFim;
    private Boolean alertas;

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
        LembreteAgua that = (LembreteAgua) o;
        return Objects.equals(metaDiaria, that.metaDiaria) && Objects.equals(horaInicio, that.horaInicio) && Objects.equals(horaFim, that.horaFim) && Objects.equals(alertas, that.alertas);
    }

    @Override
    public int hashCode() {
        return Objects.hash(metaDiaria, horaInicio, horaFim, alertas);
    }

    @Override
    @NonNull
    public String toString() {
        return "LembreteAgua{" +
                "metaDiaria=" + metaDiaria +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaFim='" + horaFim + '\'' +
                ", alertas=" + alertas +
                '}';
    }
}
