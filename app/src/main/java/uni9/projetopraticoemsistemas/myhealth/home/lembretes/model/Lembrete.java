package uni9.projetopraticoemsistemas.myhealth.home.lembretes.model;

import static uni9.projetopraticoemsistemas.myhealth.helper.DateFormatter.stringDateTimeTolong;

import androidx.annotation.NonNull;

import java.util.Objects;

import uni9.projetopraticoemsistemas.myhealth.login.model.Usuario;

public class Lembrete {

    private Long id;
    private Usuario usuario;
    private Medicamento medicamento;
    private String detalhes;
    private String dataInicio;
    private String horaInicio;
    private Long duracao;
    private Long intervalo;
    private Boolean alertas;
    private String dataCriacao;
    private String proximaDose;

    public Boolean isCompleto() {
        long agora = System.currentTimeMillis();
        Long inicioTratamento = Objects.requireNonNull(stringDateTimeTolong(this.getDataInicio() + " " + this.getHoraInicio()));

        return agora >= inicioTratamento + (1000 * 60 * 60 * 24 * this.getDuracao());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Medicamento getMedicamento() {
        return medicamento;
    }

    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Long getDuracao() {
        return duracao;
    }

    public void setDuracao(Long duracao) {
        this.duracao = duracao;
    }

    public Long getIntervalo() {
        return intervalo;
    }

    public void setIntervalo(Long intervalo) {
        this.intervalo = intervalo;
    }

    public Boolean getAlertas() {
        return alertas;
    }

    public void setAlertas(Boolean alertas) {
        this.alertas = alertas;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getProximaDose() {
        return proximaDose;
    }

    public void setProximaDose(String proximaDose) {
        this.proximaDose = proximaDose;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lembrete lembrete = (Lembrete) o;
        return Objects.equals(id, lembrete.id) && Objects.equals(usuario, lembrete.usuario) && Objects.equals(medicamento, lembrete.medicamento) && Objects.equals(detalhes, lembrete.detalhes) && Objects.equals(dataInicio, lembrete.dataInicio) && Objects.equals(horaInicio, lembrete.horaInicio) && Objects.equals(duracao, lembrete.duracao) && Objects.equals(intervalo, lembrete.intervalo) && Objects.equals(alertas, lembrete.alertas) && Objects.equals(dataCriacao, lembrete.dataCriacao) && Objects.equals(proximaDose, lembrete.proximaDose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, usuario, medicamento, detalhes, dataInicio, horaInicio, duracao, intervalo, alertas, dataCriacao, proximaDose);
    }

    @NonNull
    @Override
    public String toString() {
        return "Lembrete{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", medicamento=" + medicamento +
                ", detalhes='" + detalhes + '\'' +
                ", dataInicio='" + dataInicio + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", duracao=" + duracao +
                ", intervalo=" + intervalo +
                ", alertas=" + alertas +
                ", dataCriacao='" + dataCriacao + '\'' +
                ", proximaDose='" + proximaDose + '\'' +
                '}';
    }
}
