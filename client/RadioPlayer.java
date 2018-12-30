package fr.pandaax.mytrilium.client;

public class RadioPlayer
{
    public static Thread radioThread = RadioThread.newThread();
    public static boolean isRadioPlaying = false;

    public static void action()
    {
        if (radioThread != null)
        {
            if (!isRadioPlaying)
            {
                isRadioPlaying = true;
                radioThread.start();
            }
            else
            {
                isRadioPlaying = false;
                radioThread.stop();
                radioThread = RadioThread.newThread();
            }
        }
        else
        {
            radioThread = RadioThread.newThread();
            action();
        }
    }
}
