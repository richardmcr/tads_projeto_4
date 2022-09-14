package uni9.projetopraticoemsistemas.myhealth.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import uni9.projetopraticoemsistemas.myhealth.model.Lembrete;
import uni9.projetopraticoemsistemas.myhealth.model.Medicamento;
import uni9.projetopraticoemsistemas.myhealth.model.entity.LembreteEntity;
import uni9.projetopraticoemsistemas.myhealth.model.entity.LembreteJoinMedicamento;
import uni9.projetopraticoemsistemas.myhealth.model.entity.MedicamentoEntity;

@Mapper(uses = DateMapper.class)
public interface LembreteMapper {

    @Mapping(target = "id", source = "lembreteJoinMedicamento.lembreteEntity.id")
    @Mapping(target = "usuario", ignore = true)//source = "lembreteJoinMedicamento.usuario")
    @Mapping(target = "medicamento", source = "lembreteJoinMedicamento.medicamentoEntity")
    @Mapping(target = "detalhes", source = "lembreteJoinMedicamento.lembreteEntity.detalhes")
    @Mapping(target = "dataInicio", source = "lembreteJoinMedicamento.lembreteEntity.dataInicio", qualifiedBy = {DateConverter.class, LongToStringDate.class})
    @Mapping(target = "horaInicio", source = "lembreteJoinMedicamento.lembreteEntity.dataInicio", qualifiedBy = {DateConverter.class, LongToStringTime.class})
    @Mapping(target = "duracao", source = "lembreteJoinMedicamento.lembreteEntity.duracao")
    @Mapping(target = "intervalo", source = "lembreteJoinMedicamento.lembreteEntity.intervalo")
    @Mapping(target = "alertas", source = "lembreteJoinMedicamento.lembreteEntity.alertas")
    @Mapping(target = "dataCriacao", source = "lembreteJoinMedicamento.lembreteEntity.dataCriacao")
    @Mapping(target = "proximaDose", source = "lembreteJoinMedicamento.lembreteEntity.proximaDose", qualifiedBy = {DateConverter.class, LongToStringDateTime.class})
    Lembrete lembreteJoinMedicamentoToLembrete(LembreteJoinMedicamento lembreteJoinMedicamento);

    List<Lembrete> lembreteJoinMedicamentoToLembretes(List<LembreteJoinMedicamento> lembreteJoinMedicamento);

    @Mapping(target = "id", source = "medicamentoEntity.idProduto")
    @Mapping(target = "nome", source = "medicamentoEntity.nomeProduto")
    @Mapping(target = "razaoSocial", source = "medicamentoEntity.razaoSocial")
    @Mapping(target = "cnpj", source = "medicamentoEntity.cnpj")
    @Mapping(target = "processo", source = "medicamentoEntity.numProcesso")
    @Mapping(target = "idBulaPacienteProtegido", source = "medicamentoEntity.idBulaPacienteProtegido")
    @Mapping(target = "nomeComercial", source = "medicamentoEntity.nomeComercial")
    @Mapping(target = "classesTerapeuticas", source = "medicamentoEntity.classesTerapeuticas")
    @Mapping(target = "medicamentoReferencia", source = "medicamentoEntity.medicamentoReferencia")
    @Mapping(target = "codigoBulaPaciente", source = "medicamentoEntity.codigoBulaPaciente")
    @Mapping(target = "principioAtivo", source = "medicamentoEntity.principioAtivo")
    Medicamento medicamentoEntityToMedicamento(MedicamentoEntity medicamentoEntity);

    @Mapping(target = "id", source = "lembrete.id")
    @Mapping(target = "idUsuario", ignore = true)
    @Mapping(target = "idMedicamento", ignore = true)
    @Mapping(target = "detalhes", ignore = true)
    @Mapping(target = "dataInicio", ignore = true)
    @Mapping(target = "duracao", ignore = true)
    @Mapping(target = "intervalo", ignore = true)
    @Mapping(target = "alertas", ignore = true)
    @Mapping(target = "proximaDose", ignore = true)
    LembreteEntity lembreteToLembreteEntity(Lembrete lembrete);
}
