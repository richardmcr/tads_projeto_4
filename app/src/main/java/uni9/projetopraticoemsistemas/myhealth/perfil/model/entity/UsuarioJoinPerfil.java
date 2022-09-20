package uni9.projetopraticoemsistemas.myhealth.perfil.model.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

import uni9.projetopraticoemsistemas.myhealth.login.model.entity.UsuarioEntity;

public class UsuarioJoinPerfil {

    @Embedded
    private UsuarioEntity usuarioEntity;
    @Relation(
            parentColumn = "id",
            entityColumn = "idUsuario"
    )
    private PerfilEntity perfilEntity;

    public UsuarioEntity getUsuarioEntity() {
        return usuarioEntity;
    }

    public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
        this.usuarioEntity = usuarioEntity;
    }

    public PerfilEntity getPerfilEntity() {
        return perfilEntity;
    }

    public void setPerfilEntity(PerfilEntity perfilEntity) {
        this.perfilEntity = perfilEntity;
    }

    @NonNull
    @Override
    public String toString() {
        return "UsuarioJoinPerfil{" +
                "usuarioEntity=" + usuarioEntity +
                ", perfilEntity=" + perfilEntity +
                '}';
    }
}
