package uni9.projetopraticoemsistemas.myhealth.apis;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import uni9.projetopraticoemsistemas.myhealth.model.dto.BuscaResponse;
import uni9.projetopraticoemsistemas.myhealth.model.dto.MedicamentoResponse;

public interface BuscaMedicamentoService {

    @GET("/pesquisar")
    Call<BuscaResponse> buscarMedicamentos(
            @Query("nome") String nome,
            @Query("pagina") Integer pagina
    );

    @GET("/medicamento/{numProcesso}")
    Call<MedicamentoResponse> obterMedicamento(
            @Path("numProcesso") String numProcesso
    );
}
