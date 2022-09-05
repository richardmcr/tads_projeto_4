package uni9.projetopraticoemsistemas.myhealth;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import uni9.projetopraticoemsistemas.myhealth.apis.MedicamentoDao;
import uni9.projetopraticoemsistemas.myhealth.model.entity.MedicamentoEntity;

@Database(entities = MedicamentoEntity.class, version = 1, exportSchema = false)
public abstract class MyHealthDatabase extends RoomDatabase {
    
    private static MyHealthDatabase instance;

    public abstract MedicamentoDao medicamentoDao();

    public static synchronized MyHealthDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MyHealthDatabase.class , "Database_name")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }


    private static final RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}
