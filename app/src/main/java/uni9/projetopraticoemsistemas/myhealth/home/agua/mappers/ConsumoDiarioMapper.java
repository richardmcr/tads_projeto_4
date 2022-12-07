package uni9.projetopraticoemsistemas.myhealth.home.agua.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import uni9.projetopraticoemsistemas.myhealth.home.agua.model.ConsumoDiario;
import uni9.projetopraticoemsistemas.myhealth.home.agua.model.entity.ConsumoDiarioEntity;

@Mapper
public interface ConsumoDiarioMapper {

    @Mapping(target = "idUsuario", source = "idUsuario")
    @Mapping(target = "data", source = "consumoDiario.data")
    @Mapping(target = "meta", source = "consumoDiario.meta")
    @Mapping(target = "consumo", source = "consumoDiario.consumo")
    ConsumoDiarioEntity consumoDiarioToConsumoDiarioEntity(ConsumoDiario consumoDiario, Long idUsuario);

    @Mapping(target = "idUsuario", source = "idUsuario")
    @Mapping(target = "data", source = "data")
    @Mapping(target = "meta", source = "meta")
    @Mapping(target = "consumo", source = "consumo")
    ConsumoDiario consumoDiarioEntityToConsumoDiario(ConsumoDiarioEntity consumoDiarioEntity);
}
