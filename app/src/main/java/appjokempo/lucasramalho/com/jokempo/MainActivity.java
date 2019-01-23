package appjokempo.lucasramalho.com.jokempo;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageButton pedra;
    ImageButton papel;
    ImageButton tesoura;

    ImageView jogador1;
    ImageView jogador2;

    Animation some;
    Animation aparece;

    int jogada1 = 0;
    int jogada2 = 0;

    MediaPlayer som;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        som = MediaPlayer.create(MainActivity.this, R.raw.alex_play);

        pedra = findViewById(R.id.botaoPedra);
        papel = findViewById(R.id.botaoPapel);
        tesoura = findViewById(R.id.botaoTesoura);

        jogador1 = findViewById(R.id.imagemJogador1);
        jogador2 = findViewById(R.id.imagemJogador2);

        some = new AlphaAnimation(1,0);
        aparece = new AlphaAnimation(0,1);

        some.setDuration(1500);
        aparece.setDuration(100);

        some.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                jogador2.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                jogador2.setVisibility(View.INVISIBLE);
                jogador2.startAnimation(aparece);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        aparece.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                sorteiaJogadaAdversario();
                jogador2.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                verificaVencedor();
                jogador2.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void clicouBotao(View view) {

        tocaSom();
        jogador1.setScaleX(-1);
        switch (view.getId()) {
            case(R.id.botaoPedra):
                jogador1.setImageResource(R.drawable.pedra);
                jogada1 = 1;
                break;
            case(R.id.botaoPapel):
                jogador1.setImageResource(R.drawable.papel);
                jogada1 = 2;
                break;
            case(R.id.botaoTesoura):
                jogador1.setImageResource(R.drawable.tesoura);
                jogada1 = 3;
                break;
        }
        jogador2.setImageResource(R.drawable.interrogacao);
        jogador2.startAnimation(some);
    }

    public void sorteiaJogadaAdversario() {
        Random r = new Random();
        int numRandom = r.nextInt(3);

        switch (numRandom) {
            case 0:
                jogador2.setImageResource(R.drawable.pedra);
                jogada2 = 1;
                break;
            case 1:
                jogador2.setImageResource(R.drawable.papel);
                jogada2 = 2;
                break;
            case 2:
                jogador2.setImageResource(R.drawable.tesoura);
                jogada2 = 3;
                break;
        }
    }

    public void verificaVencedor() {
        if(jogada1 == jogada2) {
            Toast.makeText(this, "Empate. Jogue novamente!", Toast.LENGTH_SHORT).show();
        } else if((jogada1 == 1 && jogada2 == 3) || (jogada1 == 2 && jogada2 == 1) || (jogada1 == 3 && jogada2 == 2)) {
            Toast.makeText(this, "Você Ganhou! Parabéns.", Toast.LENGTH_SHORT).show();
        } else if ((jogada1 == 1 && jogada2 == 2) || (jogada1 == 2 && jogada2 == 3) || (jogada1 == 3 && jogada2 == 1)) {
            Toast.makeText(this, "Você perdeu!", Toast.LENGTH_SHORT).show();
        }
    }

    public void tocaSom() {
        if(som != null) {
            som.start();
        }
    }

    @Override
    protected void onDestroy() {
        if(som != null) {
            som = null;
        }
        super.onDestroy();
    }
}
