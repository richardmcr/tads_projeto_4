package uni9.projetopraticoemsistemas.myhealth;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;
import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        verifyStoragePermission(this);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationView navigationView = findViewById(R.id.navigationView);
        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentContainerViewHome);
        navController = Objects.requireNonNull(navHostFragment).getNavController();
        navController.addOnDestinationChangedListener((navController, navDestination, bundle) -> {
            if (navDestination.getId() == R.id.loginFragment) {
                toolbar.setVisibility(View.GONE);
                bottomNavigationView.setVisibility(View.GONE);
            } else if (navDestination.getId() == R.id.novoUsuarioFragment) {
                toolbar.setVisibility(View.VISIBLE);
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                toolbar.setVisibility(View.VISIBLE);
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });

        appBarConfiguration = new AppBarConfiguration
                .Builder(new HashSet<>(Arrays.asList(R.id.configuracoesFragment, R.id.homeFragment, R.id.perfiFragment)))
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);


        viewModel.getUsuarioLiveData().observe(this, usuario -> {
            if(!Objects.isNull(usuario)) {
                View headerView = navigationView.getHeaderView(0);
                TextView textViewNome = (TextView) headerView.findViewById(R.id.textViewNome);
                TextView textViewEmail = (TextView) headerView.findViewById(R.id.textViewEmail);

                textViewNome.setText(usuario.getNome());
                textViewEmail.setText(usuario.getEmail());
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    public void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);

        if (SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager() && permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE,}, 1);

                Intent intent = new Intent();
                intent.setAction(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        }
    }
}