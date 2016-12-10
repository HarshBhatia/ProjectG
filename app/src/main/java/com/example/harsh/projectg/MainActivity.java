package com.example.harsh.projectg;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.harsh.projectg.databinding.ActivityMainBinding;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.SimpleMainThreadMediaPlayerListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    public static final String BASE_URL = "http://api.giphy.com/v1/gifs/";
    public static final String API_KEY = "dc6zaTOxFJmzC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GiphyInterface giphyService = retrofit.create(GiphyInterface.class);

        binding.gifPlayer1.addMediaPlayerListener(new SimpleMainThreadMediaPlayerListener(){
            @Override
            public void onVideoPreparedMainThread() {
                // We hide the cover when video is prepared. Playback is about to start
                Toast.makeText(MainActivity.this, "Video 1 Prepared!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoStoppedMainThread() {
                // We show the cover when video is stopped
                Toast.makeText(MainActivity.this, "Video 1 Stopped!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoCompletionMainThread() {
                // We show the cover when video is completed
                Toast.makeText(MainActivity.this, "Video 1 completed", Toast.LENGTH_SHORT).show();
            }
        });
        binding.gifPlayer2.addMediaPlayerListener(new SimpleMainThreadMediaPlayerListener(){
            @Override
            public void onVideoPreparedMainThread() {
                // We hide the cover when video is prepared. Playback is about to start
                Toast.makeText(MainActivity.this, "Video 2 Prepared!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoStoppedMainThread() {
                // We show the cover when video is stopped
                Toast.makeText(MainActivity.this, "Video 2 Stopped!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVideoCompletionMainThread() {
                // We show the cover when video is completed
                Toast.makeText(MainActivity.this, "Video 2 completed", Toast.LENGTH_SHORT).show();
            }
        });

        
        Call<GiphyResponse> call = giphyService.getTrending(API_KEY);
        call.enqueue(new Callback<GiphyResponse>() {
            @Override
            public void onResponse(Call<GiphyResponse> call, Response<GiphyResponse> response) {
                int statusCode = response.code();
                binding.responseCode.setText("RESPONSE CODE: " + statusCode);
                final List<ImagesMetadata> body = response.body().data;
                binding.responseBody.setText(body.get(2).url);
                binding.gifPlayer1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Clicked Player1", Toast.LENGTH_SHORT).show();
                        mVideoPlayerManager.playNewVideo(null,binding.gifPlayer1,body.get(2).url);
                    }
                });

                binding.gifPlayer2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        binding.gifPlayer2.start();
                        mVideoPlayerManager.playNewVideo(null,binding.gifPlayer2,body.get(3).url);
                        Toast.makeText(MainActivity.this, "Clicked Player2", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onFailure(Call<GiphyResponse> call, Throwable t) {
                // Log error here since request failed
            }
        });


    }

    private VideoPlayerManager<MetaData> mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
        @Override
        public void onPlayerItemChanged(MetaData metaData) {

        }
    });
}
