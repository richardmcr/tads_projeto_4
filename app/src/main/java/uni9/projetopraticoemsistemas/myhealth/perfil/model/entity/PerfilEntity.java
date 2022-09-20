package uni9.projetopraticoemsistemas.myhealth.perfil.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;


@Entity(tableName = "perfil")
public class PerfilEntity {

    @PrimaryKey
    private Long idUsuario;

    private Long dataNascimento;

    private Long peso;

    private Float altura;

    private String genero;

    private String tipoSanguineo;

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Long dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Long getPeso() {
        return peso;
    }

    public void setPeso(Long peso) {
        this.peso = peso;
    }

    public Float getAltura() {
        return altura;
    }

    public void setAltura(Float altura) {
        this.altura = altura;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PerfilEntity that = (PerfilEntity) o;
        return Objects.equals(idUsuario, that.idUsuario) && Objects.equals(dataNascimento, that.dataNascimento) && Objects.equals(peso, that.peso) && Objects.equals(altura, that.altura) && Objects.equals(genero, that.genero) && Objects.equals(tipoSanguineo, that.tipoSanguineo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUsuario, dataNascimento, peso, altura, genero, tipoSanguineo);
    }

    @Override
    @NonNull
    public String toString() {
        return "PerfilEntity{" +
                "idUsuario=" + idUsuario +
                ", dataNascimento=" + dataNascimento +
                ", peso=" + peso +
                ", altura=" + altura +
                ", genero='" + genero + '\'' +
                ", tipoSanguineo='" + tipoSanguineo + '\'' +
                '}';
    }
}
