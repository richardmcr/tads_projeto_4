package uni9.projetopraticoemsistemas.myhealth.home.lembretes.model.dto;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class MedicamentoResponse {

    @SerializedName("codigoProduto")
    @Expose
    private Long codigoProduto;

    @SerializedName("nomeComercial")
    @Expose
    private String nomeComercial;

    @SerializedName("classesTerapeuticas")
    @Expose
    private List<String> classesTerapeuticas;

    @SerializedName("medicamentoReferencia")
    @Expose
    private String medicamentoReferencia;

    @SerializedName("codigoBulaPaciente")
    @Expose
    private String codigoBulaPaciente;

    @SerializedName("principioAtivo")
    @Expose
    private String principioAtivo;

    public Long getCodigoProduto() {
        return codigoProduto;
    }

    public void setCodigoProduto(Long codigoProduto) {
        this.codigoProduto = codigoProduto;
    }

    public String getNomeComercial() {
        return nomeComercial;
    }

    public void setNomeComercial(String nomeComercial) {
        this.nomeComercial = nomeComercial;
    }

    public List<String> getClassesTerapeuticas() {
        return classesTerapeuticas;
    }

    public void setClassesTerapeuticas(List<String> classesTerapeuticas) {
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
        MedicamentoResponse that = (MedicamentoResponse) o;
        return Objects.equals(codigoProduto, that.codigoProduto) && Objects.equals(nomeComercial, that.nomeComercial) && Objects.equals(classesTerapeuticas, that.classesTerapeuticas) && Objects.equals(medicamentoReferencia, that.medicamentoReferencia) && Objects.equals(codigoBulaPaciente, that.codigoBulaPaciente) && Objects.equals(principioAtivo, that.principioAtivo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigoProduto, nomeComercial, classesTerapeuticas, medicamentoReferencia, codigoBulaPaciente, principioAtivo);
    }

    @NonNull
    @Override
    public String toString() {
        return "MedicamentoResponse{" +
                "codigoProduto='" + codigoProduto + '\'' +
                ", nomeComercial='" + nomeComercial + '\'' +
                ", classesTerapeuticas=" + classesTerapeuticas +
                ", medicamentoReferencia='" + medicamentoReferencia + '\'' +
                ", codigoBulaPaciente='" + codigoBulaPaciente + '\'' +
                ", principioAtivo='" + principioAtivo + '\'' +
                '}';
    }
}
