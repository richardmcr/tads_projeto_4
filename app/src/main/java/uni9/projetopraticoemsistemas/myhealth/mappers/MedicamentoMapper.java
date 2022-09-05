package uni9.projetopraticoemsistemas.myhealth.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import uni9.projetopraticoemsistemas.myhealth.model.dto.ContentResponse;
import uni9.projetopraticoemsistemas.myhealth.model.entity.MedicamentoEntity;

@Mapper(uses = ListMapper.class)
public interface MedicamentoMapper {

    @Mapping(target = "idProduto", source = "contentResponse.idProduto")
    @Mapping(target = "nomeProduto", source = "contentResponse.nomeProduto")
    @Mapping(target = "razaoSocial", source = "contentResponse.razaoSocial")
    @Mapping(target = "cnpj", source = "contentResponse.cnpj")
    @Mapping(target = "numProcesso", source = "contentResponse.numProcesso")
    @Mapping(target = "idBulaPacienteProtegido", source = "contentResponse.idBulaPacienteProtegido")
//    @Mapping(target = "codigoProduto", source = "contentResponse.codigoProduto")
//    @Mapping(target = "nomeComercial", source = "contentResponse.nomeComercial")
//    @Mapping(target = "classesTerapeuticas", source = "contentResponse.classesTerapeuticas")
//    @Mapping(target = "medicamentoReferencia", source = "contentResponse.medicamentoReferencia")
//    @Mapping(target = "codigoBulaPaciente", source = "contentResponse.codigoBulaPaciente")
//    @Mapping(target = "principioAtivo", source = "contentResponse.principioAtivo")
    MedicamentoEntity medicamentoDtoToEntity(ContentResponse contentResponse);
}
