package uni9.projetopraticoemsistemas.myhealth.lembretes.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

import uni9.projetopraticoemsistemas.myhealth.helper.mappers.ListMapper;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.Medicamento;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.dto.ContentResponse;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.dto.MedicamentoResponse;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.entity.MedicamentoEntity;

@Mapper(uses = ListMapper.class)
public interface MedicamentoMapper {

    @Mapping(target = "id", source = "contentResponse.idProduto")
    @Mapping(target = "nome", source = "contentResponse.nomeProduto")
    @Mapping(target = "razaoSocial", source = "contentResponse.razaoSocial")
    @Mapping(target = "cnpj", source = "contentResponse.cnpj")
    @Mapping(target = "processo", source = "contentResponse.numProcesso")
    @Mapping(target = "idBulaPacienteProtegido", source = "contentResponse.idBulaPacienteProtegido")
    @Mapping(target = "nomeComercial", ignore = true)
    @Mapping(target = "classesTerapeuticas", ignore = true)
    @Mapping(target = "medicamentoReferencia", ignore = true)
    @Mapping(target = "codigoBulaPaciente", ignore = true)
    @Mapping(target = "principioAtivo", ignore = true)
    Medicamento contentResponseToMedicamento(ContentResponse contentResponse);

    List<Medicamento> contentResponseToMedicamentos(List<ContentResponse> contentResponse);

    @Mapping(target = "idProduto", ignore = true)
    @Mapping(target = "nomeProduto", ignore = true)
    @Mapping(target = "razaoSocial", ignore = true)
    @Mapping(target = "cnpj", ignore = true)
    @Mapping(target = "numProcesso", ignore = true)
    @Mapping(target = "idBulaPacienteProtegido", ignore = true)
    @Mapping(target = "nomeComercial", source = "medicamentoResponse.nomeComercial")
    @Mapping(target = "classesTerapeuticas", source = "medicamentoResponse.classesTerapeuticas")
    @Mapping(target = "medicamentoReferencia", source = "medicamentoResponse.medicamentoReferencia")
    @Mapping(target = "codigoBulaPaciente", source = "medicamentoResponse.codigoBulaPaciente")
    @Mapping(target = "principioAtivo", source = "medicamentoResponse.principioAtivo")
    void updateMedicamentoEntityFromMedicamentoResponse(MedicamentoResponse medicamentoResponse, @MappingTarget MedicamentoEntity medicamentoEntity);

    @Mapping(target = "idProduto", source = "id")
    @Mapping(target = "nomeProduto", source = "nome")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    @Mapping(target = "cnpj", source = "cnpj")
    @Mapping(target = "numProcesso", source = "processo")
    @Mapping(target = "idBulaPacienteProtegido", source = "idBulaPacienteProtegido")
    @Mapping(target = "nomeComercial", source = "nomeComercial")
    @Mapping(target = "classesTerapeuticas", source = "classesTerapeuticas")
    @Mapping(target = "medicamentoReferencia", source = "medicamentoReferencia")
    @Mapping(target = "codigoBulaPaciente", source = "codigoBulaPaciente")
    @Mapping(target = "principioAtivo", source = "principioAtivo")
    MedicamentoEntity medicamentoToMedicamentoEntity(Medicamento medicamento);

    @Mapping(target = "id", source = "idProduto")
    @Mapping(target = "nome", source = "nomeProduto")
    @Mapping(target = "razaoSocial", source = "razaoSocial")
    @Mapping(target = "cnpj", source = "cnpj")
    @Mapping(target = "processo", source = "numProcesso")
    @Mapping(target = "idBulaPacienteProtegido", source = "idBulaPacienteProtegido")
    @Mapping(target = "nomeComercial", source = "nomeComercial")
    @Mapping(target = "classesTerapeuticas", source = "classesTerapeuticas")
    @Mapping(target = "medicamentoReferencia", source = "medicamentoReferencia")
    @Mapping(target = "codigoBulaPaciente", source = "codigoBulaPaciente")
    @Mapping(target = "principioAtivo", source = "principioAtivo")
    Medicamento medicamentoEntityToMedicamento(MedicamentoEntity medicamentoentity);

    List<Medicamento> medicamentoEntityToMedicamentos(List<MedicamentoEntity> medicamentoentity);
}
