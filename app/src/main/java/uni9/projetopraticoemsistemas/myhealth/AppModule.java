package uni9.projetopraticoemsistemas.myhealth;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import uni9.projetopraticoemsistemas.myhealth.home.agua.apis.ConsumoDiarioDao;
import uni9.projetopraticoemsistemas.myhealth.home.agua.apis.LembreteAguaDao;
import uni9.projetopraticoemsistemas.myhealth.home.agua.mappers.ConsumoDiarioMapper;
import uni9.projetopraticoemsistemas.myhealth.home.agua.mappers.ConsumoDiarioMapperImpl;
import uni9.projetopraticoemsistemas.myhealth.home.agua.mappers.LembreteAguaMapper;
import uni9.projetopraticoemsistemas.myhealth.home.agua.mappers.LembreteAguaMapperImpl;
import uni9.projetopraticoemsistemas.myhealth.home.agua.repositories.LembreteAguaRepository;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.apis.LembreteDao;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.apis.MedicamentoDao;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.mappers.LembreteMapper;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.mappers.LembreteMapperImpl;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.mappers.MedicamentoMapper;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.mappers.MedicamentoMapperImpl;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.repositories.LembreteRepository;
import uni9.projetopraticoemsistemas.myhealth.home.lembretes.repositories.MedicamentoRepository;
import uni9.projetopraticoemsistemas.myhealth.login.apis.UsuarioDao;
import uni9.projetopraticoemsistemas.myhealth.login.mappers.UsuarioMapper;
import uni9.projetopraticoemsistemas.myhealth.login.mappers.UsuarioMapperImpl;
import uni9.projetopraticoemsistemas.myhealth.login.repositories.UsuarioRepository;
import uni9.projetopraticoemsistemas.myhealth.perfil.apis.PerfilDao;
import uni9.projetopraticoemsistemas.myhealth.perfil.mappers.PerfilMapper;
import uni9.projetopraticoemsistemas.myhealth.perfil.mappers.PerfilMapperImpl;
import uni9.projetopraticoemsistemas.myhealth.perfil.repositories.PerfilRepository;

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
    PerfilMapper providePerfilMapper() {
        return new PerfilMapperImpl();
    }

    @Provides
    LembreteAguaMapper provideLembreteAguaMapper() {
        return new LembreteAguaMapperImpl();
    }

    @Provides
    ConsumoDiarioMapper provideConsumoDiarioMapper() {
        return new ConsumoDiarioMapperImpl();
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
    PerfilDao providePerfilDao(MyHealthDatabase myHealthDatabase) {
        return myHealthDatabase.perfilDao();
    }

    @Provides
    @Singleton
    LembreteAguaDao provideLembreteAguaDao(MyHealthDatabase myHealthDatabase) {
        return myHealthDatabase.lembreteAguaDao();
    }

    @Provides
    @Singleton
    ConsumoDiarioDao provideConsumoDiarioDao(MyHealthDatabase myHealthDatabase) {
        return myHealthDatabase.consumoDiarioDao();
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

    @Provides
    @Singleton
    PerfilRepository providePerfilRepository(PerfilDao perfilDao, PerfilMapper perfilMapper) {
        return new PerfilRepository(perfilDao, perfilMapper);
    }

    @Provides
    @Singleton
    LembreteAguaRepository provideLembreteAguaRepository(LembreteAguaDao lembreteAguaDao, LembreteAguaMapper lembreteAguaMapper) {
        return new LembreteAguaRepository(lembreteAguaDao, lembreteAguaMapper);
    }
}
