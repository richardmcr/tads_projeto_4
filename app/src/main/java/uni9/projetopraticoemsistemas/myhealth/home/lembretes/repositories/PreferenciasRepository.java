package uni9.projetopraticoemsistemas.myhealth.home.lembretes.repositories;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.model.Ordenacao;

@Singleton
public class PreferenciasRepository {

    private final SharedPreferences preferencias;
    private final SharedPreferences.Editor editor;

    @Inject
    public PreferenciasRepository(@ApplicationContext Context context) {
        this.preferencias = context.getSharedPreferences("MyHealthPreferencias", 0);
        this.editor = this.preferencias.edit();
    }

    public void atualizarOrdenacao(Ordenacao ordenacao) {
        this.editor.putString("ordenacao", ordenacao.name());
        this.editor.commit();
    }

    public void atualizarOcultarCompletos(Boolean ocultarCompletos) {
        this.editor.putBoolean("ocultarCompletos", ocultarCompletos);
        this.editor.commit();
    }

    public Ordenacao getOrdenacao() {
        return Ordenacao.valueOf(this.preferencias.getString("ordenacao", "BY_DATA"));
    }

    public Boolean getOcultarCompletos() {
        return this.preferencias.getBoolean("ocultarCompletos", true);
    }
}
