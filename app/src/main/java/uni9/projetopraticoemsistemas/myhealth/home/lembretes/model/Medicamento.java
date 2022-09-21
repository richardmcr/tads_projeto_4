package uni9.projetopraticoemsistemas.myhealth.home.lembretes.model;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Medicamento {

    private Long id;
    private String nome;
    private String razaoSocial;
    private String cnpj;
    private String processo;
    private String idBulaPacienteProtegido;
    private String nomeComercial;
    private String classesTerapeuticas;
    private String medicamentoReferencia;
    private String codigoBulaPaciente;
    private String principioAtivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getProcesso() {
        return processo;
    }

    public void setProcesso(String processo) {
        this.processo = processo;
    }

    public String getIdBulaPacienteProtegido() {
        return idBulaPacienteProtegido;
    }

    public void setIdBulaPacienteProtegido(String idBulaPacienteProtegido) {
        this.idBulaPacienteProtegido = idBulaPacienteProtegido;
    }

    public String getNomeComercial() {
        return nomeComercial;
    }

    public void setNomeComercial(String nomeComercial) {
        this.nomeComercial = nomeComercial;
    }

    public String getClassesTerapeuticas() {
        return classesTerapeuticas;
    }

    public void setClassesTerapeuticas(String classesTerapeuticas) {
        this.classesTerapeuticas = classesTerapeuticas;
    }

    public String getMedicamentoReferencia() {
        return medicamentoReferencia;
    }

    public void setMedicamentoReferencia(String medicamentoReferencia) {
        this.medicamentoReferencia = medicamentoReferencia;
    }

    public String getCodigoBulaPaciente() {
        return codigoBulaPaciente;
    }

    public void setCodigoBulaPaciente(String codigoBulaPaciente) {
        this.codigoBulaPaciente = codigoBulaPaciente;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicamento that = (Medicamento) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(razaoSocial, that.razaoSocial) && Objects.equals(cnpj, that.cnpj) && Objects.equals(processo, that.processo) && Objects.equals(idBulaPacienteProtegido, that.idBulaPacienteProtegido) && Objects.equals(nomeComercial, that.nomeComercial) && Objects.equals(classesTerapeuticas, that.classesTerapeuticas) && Objects.equals(medicamentoReferencia, that.medicamentoReferencia) && Objects.equals(codigoBulaPaciente, that.codigoBulaPaciente) && Objects.equals(principioAtivo, that.principioAtivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, razaoSocial, cnpj, processo, idBulaPacienteProtegido, nomeComercial, classesTerapeuticas, medicamentoReferencia, codigoBulaPaciente, principioAtivo);
    }

    @Override
    @NonNull
    public String toString() {
        return "Medicamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", processo='" + processo + '\'' +
                ", idBulaPacienteProtegido='" + idBulaPacienteProtegido + '\'' +
                ", nomeComercial='" + nomeComercial + '\'' +
                ", classesTerapeuticas='" + classesTerapeuticas + '\'' +
                ", medicamentoReferencia='" + medicamentoReferencia + '\'' +
                ", codigoBulaPaciente='" + codigoBulaPaciente + '\'' +
                ", principioAtivo='" + principioAtivo + '\'' +
                '}';
    }
}
