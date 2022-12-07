package uni9.projetopraticoemsistemas.myhealth.home.agua.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import uni9.projetopraticoemsistemas.myhealth.home.agua.model.LembreteAgua;
import uni9.projetopraticoemsistemas.myhealth.home.agua.model.entity.LembreteAguaEntity;

@Mapper
public interface LembreteAguaMapper {

    @Mapping(target = "idUsuario", source = "idUsuario")
    @Mapping(target = "metaDiaria", source = "lembreteAgua.metaDiaria")
    @Mapping(target = "horaInicio", source = "lembreteAgua.horaInicio")
    @Mapping(target = "horaFim", source = "lembreteAgua.horaFim")
    @Mapping(target = "alertas", source = "lembreteAgua.alertas")
    LembreteAguaEntity lembreteAguaToLembreteAguaEntity(LembreteAgua lembreteAgua, Long idUsuario);

    @Mapping(target = "metaDiaria", source = "metaDiaria")
    @Mapping(target = "horaInicio", source = "horaInicio")
    @Mapping(target = "horaFim", source = "horaFim")
    @Mapping(target = "alertas", source = "alertas")
    LembreteAgua lembreteAguaEntityToLembreteAgua(LembreteAguaEntity lembreteAguaEntity);
}
