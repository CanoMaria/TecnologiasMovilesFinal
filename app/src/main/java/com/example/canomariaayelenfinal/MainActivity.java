package com.example.canomariaayelenfinal;

import static com.spotify.sdk.android.auth.AccountsQueryParameters.CLIENT_ID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentManager;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;


import com.example.canomariaayelenfinal.R;
import com.example.canomariaayelenfinal.databinding.ActivityMainBinding;
import com.example.canomariaayelenfinal.business.Fragments.FavoriteFragment;
import com.example.canomariaayelenfinal.business.Fragments.HomeFragment;
import com.example.canomariaayelenfinal.business.Notifications.NotificationReceiver;
import com.example.canomariaayelenfinal.business.Fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.types.Track;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;


import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

  //  FilmDAO filmDAO;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private BottomNavigationView menu;
    private static final String CLIENT_ID = "805ac951d0504b5282ce995fef2a1aad";
    private static final String REDIRECT_URI = "com.example.canomariaayelenfinal://callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    private static final String PLAYLIST_URI = "spotify:playlist:37i9dQZF1DX2sUQwD7tbmL";
    private static final int REQUEST_CODE = 1337;
    private SharedPreferences.Editor editor;

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
    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    @Override
    protected void onStart() {
        super.onStart();
       /* AuthorizationRequest.Builder builder = new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-recently-played,user-library-modify,user-library-read,playlist-modify-public,playlist-modify-private,user-read-email,user-read-private,user-read-birthdate,playlist-read-private,playlist-read-collaborative"});
        AuthorizationRequest request = builder.build();
        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);*/

        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();
        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected to Spotify!");
                        // Now you can start interacting with App Remote
                        playPlaylist();
                    }

                    public void onFailure(Throwable throwable) {
                        Log.e("ERROOOOOOR", throwable.getMessage(), throwable);

                    }
                });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthorizationResponse.Type.TOKEN) {
                // El usuario ha autorizado nuestra aplicación a acceder a sus datos de Spotify
                String accessToken = response.getAccessToken();
                // Usa el accessToken para hacer llamadas a la Web API de Spotify
            } else {
                // No se concedió autorización
            }
        }
    }

    private void playPlaylist() {
        // Play a playlist
        mSpotifyAppRemote.getPlayerApi().play(PLAYLIST_URI);
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
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 10);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

}