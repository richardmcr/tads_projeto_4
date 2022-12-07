package uni9.projetopraticoemsistemas.myhealth.home.agua.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ConsumoDiario {

    private Long idUsuario;
    private String data;
    private Long meta;
    private Long consumo;

    public Long getRestante() {
        return meta - consumo;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getMeta() {
        return meta;
    }

    public void setMeta(Long meta) {
        this.meta = meta;
    }

    public Long getConsumo() {
        return consumo;
    }

    public void setConsumo(Long consumo) {
        this.consumo = consumo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsumoDiario that = (ConsumoDiario) o;
        return Objects.equals(idUsuario, that.idUsuario) && Objects.equals(data, that.data) && Objects.equals(meta, that.meta) && Objects.equals(consumo, that.consumo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, data, meta, consumo);
    }

    @Override
    @NonNull
    public String toString() {
        return "ConsumoDiario{" +
                "idUsuario=" + idUsuario +
                ", data='" + data + '\'' +
                ", meta='" + meta + '\'' +
                ", consumo=" + consumo +
                '}';
    }
}
