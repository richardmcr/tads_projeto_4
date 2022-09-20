package uni9.projetopraticoemsistemas.myhealth;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Provider;

import uni9.projetopraticoemsistemas.myhealth.lembretes.apis.LembreteDao;
import uni9.projetopraticoemsistemas.myhealth.lembretes.apis.MedicamentoDao;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.entity.LembreteEntity;
import uni9.projetopraticoemsistemas.myhealth.lembretes.model.entity.MedicamentoEntity;
import uni9.projetopraticoemsistemas.myhealth.login.apis.UsuarioDao;
import uni9.projetopraticoemsistemas.myhealth.login.model.entity.UsuarioEntity;
import uni9.projetopraticoemsistemas.myhealth.perfil.apis.PerfilDao;
import uni9.projetopraticoemsistemas.myhealth.perfil.model.entity.PerfilEntity;

@Database(entities = {MedicamentoEntity.class, LembreteEntity.class, UsuarioEntity.class, PerfilEntity.class}, version = 3, exportSchema = false)
public abstract class MyHealthDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract MedicamentoDao medicamentoDao();

    public abstract LembreteDao lembreteDao();

    public abstract UsuarioDao usuarioDao();

    public abstract PerfilDao perfilDao();

    public static class Callback extends RoomDatabase.Callback {

        private final Provider<MyHealthDatabase> myHealthDatabaseProvider;

        @Inject
        public Callback(Provider<MyHealthDatabase> myHealthDatabaseProvider) {
            this.myHealthDatabaseProvider = myHealthDatabaseProvider;
        }

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            MyHealthDatabase.databaseWriteExecutor.execute(() -> {
                UsuarioEntity u = new UsuarioEntity();
                u.setEmail("email@teste.com");
                u.setSenha("1234");
                u.setNome("Usuário 1");
                myHealthDatabaseProvider.get().usuarioDao().insert(u.getNome(), u.getEmail(), u.getSenha());

                MedicamentoEntity e1 = new MedicamentoEntity();
                e1.setIdProduto(750563L);
                e1.setNomeProduto("BENEGRIP");
                e1.setRazaoSocial("COSMED INDUSTRIA DE COSMETICOS E MEDICAMENTOS S.A.");
                e1.setCnpj("61082426000207");
                e1.setNumProcesso("25351637663200937");
                e1.setIdBulaPacienteProtegido("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxNDY3OTc4OCIsIm5iZiI6MTY2MzEwOTM5MywiZXhwIjoxNjYzMTA5NjkzfQ.S42G6EkMsFbWKZKdAqwbdt2pRU2njCa7L6S685a4xlRFCoFCNdQpuCtkrf4TPHdxnU5_Z1b4CGmgPvyXjmeiiw");
                e1.setNomeComercial("BENEGRIP");
                e1.setClassesTerapeuticas("PRODUTOS PARA TERAPIA SINTOMATICA DA GRIPE");
                e1.setMedicamentoReferencia(null);
                e1.setCodigoBulaPaciente("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxNDY3OTc4OCIsIm5iZiI6MTY2MzEwOTk2MiwiZXhwIjoxNjYzMTEwMjYyfQ.u-roIXPd9WItrlXzWEBEaTy8ghZ-4TvugkXwoCwEn340gL-ADdP_qdzKy20dHrX-sCeiCN2SNSCksUoOOpf0yw");
                e1.setPrincipioAtivo("CAFEÍNA, MALEATO DE CLORFENIRAMINA, dipirona monoidratada");
                myHealthDatabaseProvider.get().medicamentoDao().insert(e1);

                MedicamentoEntity e2 = new MedicamentoEntity();
                e2.setIdProduto(688380L);
                e2.setNomeProduto("IBUPROFENO");
                e2.setRazaoSocial("NOVA QUIMICA FARMACÊUTICA S/A");
                e2.setCnpj("72593791000111");
                e2.setNumProcesso("25351346397200910");
                e2.setIdBulaPacienteProtegido("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxNDMxMjU1MCIsIm5iZiI6MTY2MzEwOTY2MSwiZXhwIjoxNjYzMTA5OTYxfQ.Y1sO5kOKWY_73PVyEEIXH6H6m9oRmGVtMaUWJS55HFf2BqAgIbgRyxw4TWmSM4CQB1-Y-m4V8UDhbK3TfAc2Ug");
                e2.setNomeComercial("IBUPROFENO");
                e2.setClassesTerapeuticas("ANALGESICOS NAO NARCOTICOS");
                e2.setMedicamentoReferencia("ALIVIUM");
                e2.setCodigoBulaPaciente("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxNDMxMjU1MCIsIm5iZiI6MTY2MzExMDA0OSwiZXhwIjoxNjYzMTEwMzQ5fQ.bHNYhKbYdRSU1YZY_jDHGvd1lKqFNZbZE6Aaes3yBwbMzLIthLrsgZmrzJDls7xPrahHYV9wyx_LK0d0DGzYpw");
                e2.setPrincipioAtivo("IBUPROFENO");
                myHealthDatabaseProvider.get().medicamentoDao().insert(e2);

                MedicamentoEntity e3 = new MedicamentoEntity();
                e3.setIdProduto(2001579L);
                e3.setNomeProduto("NEOSALDINA");
                e3.setRazaoSocial("COSMED INDUSTRIA DE COSMETICOS E MEDICAMENTOS S.A.");
                e3.setCnpj("61082426000207");
                e3.setNumProcesso("25351110112202151");
                e3.setIdBulaPacienteProtegido("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxNDQ0NTkxNyIsIm5iZiI6MTY2MzEwOTgzNCwiZXhwIjoxNjYzMTEwMTM0fQ.-Bu-KSmWs8FKF2QhlMa7SN-vXa-HAlwLfCEk65cK9-f7Dq4w-Jbh7HB0XBIevXKzdq2a4CwHhRJ743-9mkNavA");
                e3.setNomeComercial("NEOSALDINA");
                e3.setClassesTerapeuticas("ANALGESICOS NAO NARCOTICOS");
                e3.setMedicamentoReferencia(null);
                e3.setCodigoBulaPaciente("eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiIxNDQ0NTkxNyIsIm5iZiI6MTY2MzExMDEyNSwiZXhwIjoxNjYzMTEwNDI1fQ.uf8YgQu85dljYBCfq4tkN1zjBjNNQjv8knUPlW7fc4MXoZQTjDGVbV4g61L_86caVPThIVpEcQrIahyAwviGnw");
                e3.setPrincipioAtivo("CAFEÍNA ANIDRA, DIPIRONA, CLORIDRATO DE ISOMETEPTENO, MUCATO DE ISOMETEPTENO");
                myHealthDatabaseProvider.get().medicamentoDao().insert(e3);

                myHealthDatabaseProvider.get().lembreteDao().insert(1L, 750563L, "detalhes do primeiro lembrete", System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000, 5L, 6L, true);
                myHealthDatabaseProvider.get().lembreteDao().insert(1L, 688380L, "o segundo lembrete", System.currentTimeMillis() - 8 * 60 * 60 * 1000, 2L, 4L, true);
                myHealthDatabaseProvider.get().lembreteDao().insert(1L, 2001579L, "tomar 3 comprimidos ao dia", System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000, 3L, 8L, true);
            });
        }

    }
}
