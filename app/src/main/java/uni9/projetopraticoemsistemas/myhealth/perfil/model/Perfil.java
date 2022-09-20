package uni9.projetopraticoemsistemas.myhealth.perfil.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Perfil {

    private String nome;
    private String dataNascimento;
    private Long peso;
    private Float altura;
    private String genero;
    private String tipoSanguineo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
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
        Perfil perfil = (Perfil) o;
        return Objects.equals(nome, perfil.nome) && Objects.equals(dataNascimento, perfil.dataNascimento) && Objects.equals(peso, perfil.peso) && Objects.equals(altura, perfil.altura) && Objects.equals(genero, perfil.genero) && Objects.equals(tipoSanguineo, perfil.tipoSanguineo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dataNascimento, peso, altura, genero, tipoSanguineo);
    }

    @Override
    @NonNull
    public String toString() {
        return "Perfil{" +
                "nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", peso=" + peso +
                ", altura=" + altura +
                ", genero='" + genero + '\'' +
                ", tipoSanguineo='" + tipoSanguineo + '\'' +
                '}';
    }
}
