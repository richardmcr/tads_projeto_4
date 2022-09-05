package uni9.projetopraticoemsistemas.myhealth.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class ContentResponse {

    @SerializedName("idProduto")
    @Expose
    private Long idProduto;

    @SerializedName("nomeProduto")
    @Expose
    private String nomeProduto;

    @SerializedName("razaoSocial")
    @Expose
    private String razaoSocial;

    @SerializedName("cnpj")
    @Expose
    private String cnpj;

    @SerializedName("numProcesso")
    @Expose
    private String numProcesso;

    @SerializedName("idBulaPacienteProtegido")
    @Expose
    private String idBulaPacienteProtegido;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContentResponse contentResponse = (ContentResponse) o;
        return Objects.equals(idProduto, contentResponse.idProduto) && Objects.equals(nomeProduto, contentResponse.nomeProduto) && Objects.equals(razaoSocial, contentResponse.razaoSocial) && Objects.equals(cnpj, contentResponse.cnpj) && Objects.equals(numProcesso, contentResponse.numProcesso) && Objects.equals(idBulaPacienteProtegido, contentResponse.idBulaPacienteProtegido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduto, nomeProduto, razaoSocial, cnpj, numProcesso, idBulaPacienteProtegido);
    }

    @Override
    public String toString() {
        return "Content{" +
                "idProduto=" + idProduto +
                ", nomeProduto='" + nomeProduto + '\'' +
                ", razaoSocial='" + razaoSocial + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", numProcesso='" + numProcesso + '\'' +
                ", idBulaPacienteProtegido='" + idBulaPacienteProtegido + '\'' +
                '}';
    }
}
