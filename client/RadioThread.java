package fr.pandaax.mytrilium.client;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class RadioThread implements Runnable
{
    public static Thread newThread()
    {
        Thread t = new Thread(new RadioThread());
        return t;
    }

    @Override
    public void run()
    {
        RadioThread.RadioPlayer.play("http://cdn.nrjaudio.fm/audio1/fr/30001/mp3_128.mp3?origine=fluxradios");
    }

    static class RadioPlayer
    {
        static Player player;
        static URLConnection urlcon;
        static boolean isPlaying = false;
        static RadioPlayer instance;

        static void play(String url)
        {
            try
            {
                urlcon = new URL(url).openConnection();
                urlcon.connect();
                player = new Player(urlcon.getInputStream());
                player.play();
                isPlaying = true;
            } catch (IOException e)
            {
                e.printStackTrace();
            } catch (JavaLayerException e)
            {
                e.printStackTrace();
            }
        }

        static void stop()
        {
            if (player != null)
            {
                player.close();
                isPlaying = false;
            }
        }

        static boolean isPlaying()
        {
            return isPlaying;
        }
    }
}
