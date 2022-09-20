package uni9.projetopraticoemsistemas.myhealth.lembretes.apis;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import uni9.projetopraticoemsistemas.myhealth.lembretes.model.entity.LembreteEntity;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.entity.LembreteJoinMedicamento;

@Dao
public interface LembreteDao {

    @Transaction
    @Query("SELECT *, \n" +
            "CASE WHEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) > (:data) THEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) \n" +
            "ELSE (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo+1) * lembrete.intervalo * 3600000 + lembrete.dataInicio) END proximaDose \n" +
            "FROM lembrete \n" +
            "INNER JOIN medicamento ON medicamento.idProduto = lembrete.idMedicamento \n" +
            "WHERE lembrete.idUsuario = :idUsuario \n" +
            "   AND (upper(medicamento.nomeProduto) LIKE '%'||:medicamento||'%' OR upper(medicamento.nomeComercial) = '%'||:medicamento||'%') \n" +
            "   AND :data <= 86400000 * lembrete.duracao + lembrete.dataInicio \n" +
            "ORDER BY medicamento.nomeProduto ASC")
    List<LembreteJoinMedicamento> findAllLembretesCompletosByIdUsuarioAndMedicamentoSortedByMedicamentoNomeProduto(Long idUsuario, String medicamento, Long data);

    @Transaction
    @Query("SELECT *, \n" +
            "CASE WHEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) > (:data) THEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) \n" +
            "ELSE (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo+1) * lembrete.intervalo * 3600000 + lembrete.dataInicio) END proximaDose \n" +
            "FROM lembrete \n" +
            "INNER JOIN medicamento ON medicamento.idProduto = lembrete.idMedicamento \n" +
            "WHERE lembrete.idUsuario = :idUsuario \n" +
            "   AND (upper(medicamento.nomeProduto) LIKE '%'||:medicamento||'%' OR upper(medicamento.nomeComercial) = '%'||:medicamento||'%') \n" +
            "ORDER BY :data <= 86400000 * lembrete.duracao + lembrete.dataInicio DESC, \n" +
            "   medicamento.nomeProduto ASC")
    List<LembreteJoinMedicamento> findAllLembretesByIdUsuarioAndMedicamentoSortedByMedicamentoNomeProduto(Long idUsuario, String medicamento, Long data);

    @Transaction
    @Query("SELECT *, \n" +
            "CASE WHEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) > (:data) THEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) \n" +
            "ELSE (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo+1) * lembrete.intervalo * 3600000 + lembrete.dataInicio) END proximaDose \n" +
            "FROM lembrete \n" +
            "INNER JOIN medicamento ON medicamento.idProduto = lembrete.idMedicamento \n" +
            "WHERE lembrete.idUsuario = :idUsuario \n" +
            "   AND (upper(medicamento.nomeProduto) LIKE '%'||:medicamento||'%' OR upper(medicamento.nomeComercial) = '%'||:medicamento||'%') \n" +
            "   AND :data <= 86400000 * lembrete.duracao + lembrete.dataInicio \n" +
            "ORDER BY CASE WHEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) > (:data) THEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) \n" +
            "         ELSE (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo+1) * lembrete.intervalo * 3600000 + lembrete.dataInicio) END ASC")
    List<LembreteJoinMedicamento> findAllLembretesCompletosByIdUsuarioAndMedicamentoSortedByProximaDose(Long idUsuario, String medicamento, Long data);

    @Transaction
    @Query("SELECT *, \n" +
            "CASE WHEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) > (:data) THEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) \n" +
            "ELSE (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo+1) * lembrete.intervalo * 3600000 + lembrete.dataInicio) END proximaDose \n" +
            "FROM lembrete \n" +
            "INNER JOIN medicamento ON medicamento.idProduto = lembrete.idMedicamento \n" +
            "WHERE lembrete.idUsuario = :idUsuario \n" +
            "   AND (upper(medicamento.nomeProduto) LIKE '%'||:medicamento||'%' OR upper(medicamento.nomeComercial) = '%'||:medicamento||'%') \n" +
            "ORDER BY :data <= 86400000 * lembrete.duracao + lembrete.dataInicio DESC, \n" +
            "   CASE WHEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) > (:data) THEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) \n" +
            "         ELSE (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo+1) * lembrete.intervalo * 3600000 + lembrete.dataInicio) END ASC")
    List<LembreteJoinMedicamento> findAllLembretesByIdUsuarioAndMedicamentoSortedByProximaDose(Long idUsuario, String medicamento, Long data);

    @Transaction
    @Query("SELECT *, \n" +
            "CASE WHEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) > (:data) THEN (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo) * lembrete.intervalo * 3600000 + lembrete.dataInicio) \n" +
            "ELSE (((:data - lembrete.dataInicio)/3600000/lembrete.intervalo+1) * lembrete.intervalo * 3600000 + lembrete.dataInicio) END proximaDose \n" +
            "FROM lembrete \n" +
            "INNER JOIN medicamento ON medicamento.idProduto = lembrete.idMedicamento \n" +
            "WHERE lembrete.id = :id ")
    LembreteJoinMedicamento findLembreteById(Long id, Long data);

    @Query("INSERT INTO lembrete (idUsuario, idMedicamento, detalhes, dataInicio, duracao, intervalo, alertas) " +
            "VALUES(:idUsuario, :idMedicamento, :detalhes, :dataInicio, :duracao, :intervalo, :alertas)")
    void insert(Long idUsuario, Long idMedicamento, String detalhes, Long dataInicio, Long duracao, Long intervalo, Boolean alertas);

    @Delete
    void delete(LembreteEntity lembreteEntity);

    @Query("DELETE FROM lembrete WHERE :data > 86400000 * lembrete.duracao + lembrete.dataInicio and idUsuario = :idUsuario")
    void deleteAllLembretesCompletos(Long data, Long idUsuario);
}
