package uni9.projetopraticoemsistemas.myhealth.login.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import uni9.projetopraticoemsistemas.myhealth.login.model.Usuario;
import uni9.projetopraticoemsistemas.myhealth.login.model.entity.UsuarioEntity;

@Mapper
public interface UsuarioMapper {

    @Mapping(target = "id", source = "usuarioEntity.id")
    @Mapping(target = "nome", source = "usuarioEntity.nome")
    @Mapping(target = "email", source = "usuarioEntity.email")
    @Mapping(target = "senha", source = "usuarioEntity.senha")
    Usuario usuarioEntityToUsuario(UsuarioEntity usuarioEntity);

    @Mapping(target = "id", source = "usuario.id")
    @Mapping(target = "nome", source = "usuario.nome")
    @Mapping(target = "email", source = "usuario.email")
    @Mapping(target = "senha", source = "usuario.senha")
    @Mapping(target = "dataCriacao", expression = "java( new java.util.Date().getTime() )")
    UsuarioEntity usuarioToUsuarioEntity(Usuario usuario);

}
