package uni9.projetopraticoemsistemas.myhealth;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import uni9.projetopraticoemsistemas.myhealth.apis.LembreteDao;
import uni9.projetopraticoemsistemas.myhealth.apis.MedicamentoDao;
import uni9.projetopraticoemsistemas.myhealth.mappers.LembreteMapper;
import uni9.projetopraticoemsistemas.myhealth.mappers.LembreteMapperImpl;
import uni9.projetopraticoemsistemas.myhealth.mappers.MedicamentoMapper;
import uni9.projetopraticoemsistemas.myhealth.mappers.MedicamentoMapperImpl;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    @Provides
    @Singleton
    MyHealthDatabase provideDatabase(Application app, MyHealthDatabase.Callback callback) {
        return Room.databaseBuilder(app, MyHealthDatabase.class, "MyHealthDatabase")
                .fallbackToDestructiveMigration()
                .addCallback(callback)
                .build();
    }

    @Provides
    LembreteMapper provideLembreteMapper() {
        return new LembreteMapperImpl();
    }

    @Provides
    MedicamentoMapper provideMedicamentoMapper() {
        return new MedicamentoMapperImpl();
    }

    @Provides
    @Singleton
    LembreteDao provideLembreteDao(MyHealthDatabase myHealthDatabase) {
        return myHealthDatabase.lembreteDao();
    }

    @Provides
    @Singleton
    MedicamentoDao provideMedicamentoDao(MyHealthDatabase myHealthDatabase) {
        return myHealthDatabase.medicamentoDao();
    }

    @Provides
    @Singleton
    LembreteRepository provideLembreteRepository(LembreteDao lembreteDao, LembreteMapper lembreteMapper) {
        return new LembreteRepository(lembreteDao, lembreteMapper);
    }

    @Provides
    @Singleton
    MedicamentoRepository provideMedicamentoRepository(MedicamentoDao medicamentoDao, MedicamentoMapper medicamentoMapper) {
        return new MedicamentoRepository(medicamentoDao, medicamentoMapper);
    }
}
