package uni9.projetopraticoemsistemas.myhealth.lembretes.model;

public class QueryParams {

    private String busca;
    private Ordenacao ordenacao;
    private Boolean ocultarCompletos;

    public String getBusca() {
        return busca;
    }

    public void setBusca(String busca) {
        this.busca = busca;
    }

    public Ordenacao getOrdenacao() {
        return ordenacao;
    }

    public void setOrdenacao(Ordenacao ordenacao) {
        this.ordenacao = ordenacao;
    }

    public Boolean getOcultarCompletos() {
        return ocultarCompletos;
    }

    public void setOcultarCompletos(Boolean ocultarCompletos) {
        this.ocultarCompletos = ocultarCompletos;
    }
}
