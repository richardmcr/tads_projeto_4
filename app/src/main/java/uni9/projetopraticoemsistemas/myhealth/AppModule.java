package uni9.projetopraticoemsistemas.myhealth;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import uni9.projetopraticoemsistemas.myhealth.lembretes.apis.LembreteDao;
import uni9.projetopraticoemsistemas.myhealth.lembretes.apis.MedicamentoDao;
import uni9.projetopraticoemsistemas.myhealth.lembretes.mappers.LembreteMapper;
import uni9.projetopraticoemsistemas.myhealth.lembretes.mappers.LembreteMapperImpl;
import uni9.projetopraticoemsistemas.myhealth.lembretes.mappers.MedicamentoMapper;
import uni9.projetopraticoemsistemas.myhealth.lembretes.mappers.MedicamentoMapperImpl;
import uni9.projetopraticoemsistemas.myhealth.lembretes.repositories.LembreteRepository;
import uni9.projetopraticoemsistemas.myhealth.lembretes.repositories.MedicamentoRepository;
import uni9.projetopraticoemsistemas.myhealth.login.UsuarioRepository;
import uni9.projetopraticoemsistemas.myhealth.login.apis.UsuarioDao;
import uni9.projetopraticoemsistemas.myhealth.login.mappers.UsuarioMapper;
import uni9.projetopraticoemsistemas.myhealth.login.mappers.UsuarioMapperImpl;

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
    UsuarioMapper provideUsuarioMapper() {
        return new UsuarioMapperImpl();
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
    UsuarioDao provideUsuarioDao(MyHealthDatabase myHealthDatabase) {
        return myHealthDatabase.usuarioDao();
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

    @Provides
    @Singleton
    UsuarioRepository provideUsuarioRepository(Application app, UsuarioDao usuarioDao, UsuarioMapper usuarioMapper) {
        return new UsuarioRepository(app.getApplicationContext(), usuarioDao, usuarioMapper);
    }
}
