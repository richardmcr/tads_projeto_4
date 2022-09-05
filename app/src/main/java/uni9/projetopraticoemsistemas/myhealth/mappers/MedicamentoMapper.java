package uni9.projetopraticoemsistemas.myhealth.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import uni9.projetopraticoemsistemas.myhealth.model.dto.ContentResponse;
import uni9.projetopraticoemsistemas.myhealth.model.dto.MedicamentoResponse;
import uni9.projetopraticoemsistemas.myhealth.model.entity.MedicamentoEntity;

@Mapper(uses = ListMapper.class)
public interface MedicamentoMapper {

    @Mapping(target = "principioAtivo", ignore = true)
    @Mapping(target = "nomeComercial", ignore = true)
    @Mapping(target = "medicamentoReferencia", ignore = true)
    @Mapping(target = "codigoBulaPaciente", ignore = true)
    @Mapping(target = "classesTerapeuticas", ignore = true)
    @Mapping(target = "idProduto", source = "contentResponse.idProduto")
    @Mapping(target = "nomeProduto", source = "contentResponse.nomeProduto")
    @Mapping(target = "razaoSocial", source = "contentResponse.razaoSocial")
    @Mapping(target = "cnpj", source = "contentResponse.cnpj")
    @Mapping(target = "numProcesso", source = "contentResponse.numProcesso")
    @Mapping(target = "idBulaPacienteProtegido", source = "contentResponse.idBulaPacienteProtegido")
    MedicamentoEntity medicamentoDtoToEntity(ContentResponse contentResponse);

    @Mapping(target = "razaoSocial", ignore = true)
    @Mapping(target = "numProcesso", ignore = true)
    @Mapping(target = "nomeProduto", ignore = true)
    @Mapping(target = "idProduto", ignore = true)
    @Mapping(target = "idBulaPacienteProtegido", ignore = true)
    @Mapping(target = "cnpj", ignore = true)
    @Mapping(target = "nomeComercial", source = "medicamentoResponse.nomeComercial")
    @Mapping(target = "classesTerapeuticas", source = "medicamentoResponse.classesTerapeuticas")
    @Mapping(target = "medicamentoReferencia", source = "medicamentoResponse.medicamentoReferencia")
    @Mapping(target = "codigoBulaPaciente", source = "medicamentoResponse.codigoBulaPaciente")
    @Mapping(target = "principioAtivo", source = "medicamentoResponse.principioAtivo")
    void updateMedicamentoEntityFromDto(MedicamentoResponse medicamentoResponse, @MappingTarget MedicamentoEntity medicamentoEntity);
}
