package uni9.projetopraticoemsistemas.myhealth;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Provider;

import uni9.projetopraticoemsistemas.myhealth.apis.LembreteDao;
import uni9.projetopraticoemsistemas.myhealth.apis.MedicamentoDao;
import uni9.projetopraticoemsistemas.myhealth.model.entity.LembreteEntity;
import uni9.projetopraticoemsistemas.myhealth.model.entity.MedicamentoEntity;

@Database(entities = {MedicamentoEntity.class, LembreteEntity.class}, version = 2, exportSchema = false)
public abstract class MyHealthDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public abstract MedicamentoDao medicamentoDao();

    public abstract LembreteDao lembreteDao();

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
                MedicamentoEntity e1 = new MedicamentoEntity();
                e1.setIdProduto(1L);
                e1.setNomeProduto("Benegripe");
                e1.setNumProcesso("1234");
                myHealthDatabaseProvider.get().medicamentoDao().insert(e1);
                MedicamentoEntity e2 = new MedicamentoEntity();
                e2.setIdProduto(2L);
                e2.setNomeProduto("Ibuprofeno");
                e2.setNumProcesso("2345");
                myHealthDatabaseProvider.get().medicamentoDao().insert(e2);
                MedicamentoEntity e3 = new MedicamentoEntity();
                e3.setIdProduto(3L);
                e3.setNomeProduto("Neosaldina");
                e3.setNumProcesso("3456");
                myHealthDatabaseProvider.get().medicamentoDao().insert(e3);

                myHealthDatabaseProvider.get().lembreteDao().insert(1L, 1L, "detalhes do primeiro lembrete", System.currentTimeMillis() - 2 * 24 * 60 * 60 * 1000, 5L, 6L, true);
                myHealthDatabaseProvider.get().lembreteDao().insert(1L, 2L, "o segundo lembrete", System.currentTimeMillis() - 8 * 60 * 60 * 1000, 2L, 4L, true);
                myHealthDatabaseProvider.get().lembreteDao().insert(1L, 3L, "tomar 3 comprimidos ao dia", System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000, 3L, 8L, true);
            });
        }

    }
}
