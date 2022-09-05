package uni9.projetopraticoemsistemas.myhealth.model.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "medicamento")
public class MedicamentoEntity {

    @PrimaryKey
    private Long idProduto;

    private String nomeProduto;

    private String razaoSocial;

    private String cnpj;

    private String numProcesso;

    private String idBulaPacienteProtegido;

    private String codigoProduto;

    private String nomeComercial;

    private String classesTerapeuticas;

    private String medicamentoReferencia;

    private String codigoBulaPaciente;

    private String principioAtivo;

    public Long getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(Long idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
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

    public String getNumProcesso() {
        return numProcesso;
    }

    public void setNumProcesso(String numProcesso) {
        this.numProcesso = numProcesso;
    }

    public String getIdBulaPacienteProtegido() {
        return idBulaPacienteProtegido;
    }

    public void setIdBulaPacienteProtegido(String idBulaPacienteProtegido) {
        this.idBulaPacienteProtegido = idBulaPacienteProtegido;
    }

    public String getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
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
        MedicamentoEntity that = (MedicamentoEntity) o;
        return Objects.equals(idProduto, that.idProduto) && Objects.equals(nomeProduto, that.nomeProduto) && Objects.equals(razaoSocial, that.razaoSocial) && Objects.equals(cnpj, that.cnpj) && Objects.equals(numProcesso, that.numProcesso) && Objects.equals(idBulaPacienteProtegido, that.idBulaPacienteProtegido) && Objects.equals(codigoProduto, that.codigoProduto) && Objects.equals(nomeComercial, that.nomeComercial) && Objects.equals(classesTerapeuticas, that.classesTerapeuticas) && Objects.equals(medicamentoReferencia, that.medicamentoReferencia) && Objects.equals(codigoBulaPaciente, that.codigoBulaPaciente) && Objects.equals(principioAtivo, that.principioAtivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, nomeProduto, razaoSocial, cnpj, numProcesso, idBulaPacienteProtegido, codigoProduto, nomeComercial, classesTerapeuticas, medicamentoReferencia, codigoBulaPaciente, principioAtivo);
    }

    @Override
    public String toString() {
        return "MedicamentoEntity{" +
                "idProduto=" + idProduto +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", numProcesso='" + numProcesso + '\'' +
                ", idBulaPacienteProtegido='" + idBulaPacienteProtegido + '\'' +
                ", codigoProduto='" + codigoProduto + '\'' +
                ", nomeComercial='" + nomeComercial + '\'' +
                ", classesTerapeuticas='" + classesTerapeuticas + '\'' +
                ", medicamentoReferencia='" + medicamentoReferencia + '\'' +
                ", codigoBulaPaciente='" + codigoBulaPaciente + '\'' +
                ", principioAtivo='" + principioAtivo + '\'' +
                '}';
    }
}
