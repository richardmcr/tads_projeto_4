package uni9.projetopraticoemsistemas.myhealth.perfil.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import uni9.projetopraticoemsistemas.myhealth.helper.mappers.DateConverter;
import uni9.projetopraticoemsistemas.myhealth.helper.mappers.DateMapper;
import uni9.projetopraticoemsistemas.myhealth.helper.mappers.LongToStringDate;
import uni9.projetopraticoemsistemas.myhealth.helper.mappers.StringToLongDate;
import uni9.projetopraticoemsistemas.myhealth.perfil.model.Perfil;
import uni9.projetopraticoemsistemas.myhealth.perfil.model.entity.PerfilEntity;
import uni9.projetopraticoemsistemas.myhealth.perfil.model.entity.UsuarioJoinPerfil;

@Mapper(uses = DateMapper.class)
public interface PerfilMapper {

    @Mapping(target = "nome", source = "usuarioJoinPerfil.usuarioEntity.nome")
    @Mapping(target = "dataNascimento", source = "usuarioJoinPerfil.perfilEntity.dataNascimento", qualifiedBy = {DateConverter.class, LongToStringDate.class})
    @Mapping(target = "peso", source = "usuarioJoinPerfil.perfilEntity.peso")
    @Mapping(target = "altura", source = "usuarioJoinPerfil.perfilEntity.altura")
    @Mapping(target = "genero", source = "usuarioJoinPerfil.perfilEntity.genero")
    @Mapping(target = "tipoSanguineo", source = "usuarioJoinPerfil.perfilEntity.tipoSanguineo")
    Perfil perfilEntityToPerfil(UsuarioJoinPerfil usuarioJoinPerfil);

    @Mapping(target = "idUsuario", source = "idUsuario")
    @Mapping(target = "dataNascimento", source = "perfil.dataNascimento", qualifiedBy = {DateConverter.class, StringToLongDate.class})
    @Mapping(target = "peso", source = "perfil.peso")
    @Mapping(target = "altura", source = "perfil.altura")
    @Mapping(target = "genero", source = "perfil.genero")
    @Mapping(target = "tipoSanguineo", source = "perfil.tipoSanguineo")
    PerfilEntity perfilToPerfilEntity(Perfil perfil, Long idUsuario);

}
