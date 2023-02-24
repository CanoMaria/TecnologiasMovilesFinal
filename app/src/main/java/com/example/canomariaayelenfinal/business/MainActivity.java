package com.example.canomariaayelenfinal.business;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;


import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.databinding.ActivityMainBinding;
import com.example.canomariaayelenfinal.business.Fragments.FavoriteFragment;
import com.example.canomariaayelenfinal.business.Fragments.HomeFragment;
import com.example.canomariaayelenfinal.business.Notifications.NotificationReceiver;
import com.example.canomariaayelenfinal.business.Fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

  //  FilmDAO filmDAO;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private BottomNavigationView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        menu = findViewById(R.id.menu);


        //Creamos un canal y definimos las notificaciones
        createNotificationChannel();
        scheduleNotification(this);

        //Si queremos redirigir automaticamente a favoritos
        openFavorites();

        //Definimos el menu
        createMenu();
    }

    private void openFavorites(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (getIntent().hasExtra("fragment_to_load") && getIntent().getStringExtra("fragment_to_load").equals("favorites")) {
            fragmentManager.beginTransaction().replace(R.id.container_main, new FavoriteFragment()).commit();
        }else{
            fragmentManager.beginTransaction().replace(R.id.container_main, new HomeFragment()).commit();
        }
    }

    private void createMenu() {

        menu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                switch (item.getItemId()) {
                    case R.id.home_btn:
                        fragmentManager.beginTransaction().replace(R.id.container_main, new HomeFragment()).commit();
                        return true;
                    case R.id.favorites_btn:
                        fragmentManager.beginTransaction().replace(R.id.container_main, new FavoriteFragment()).commit();
                        return true;
                    case R.id.settings_btn:
                        fragmentManager.beginTransaction().replace(R.id.container_main, new SettingsFragment()).commit();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Noticacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    private void scheduleNotification(Context context) {
        Intent _intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, _intent, PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 45);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

}