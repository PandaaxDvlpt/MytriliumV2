package fr.pandaax.mytrilium.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;

import java.io.*;

public class AntiCheat
{
    public static void search(File file)
    {
        try
        {
            if (file.getName().toLowerCase().contains("xray"))
            {
            }
        } catch (Exception localException)
        {
        }
        File[] children = file.listFiles();
        if (file.isDirectory())
        {
            for (File child : children)
            {
                search(child);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void XRaySearch()
    {

        search(new File(Minecraft.getMinecraft().mcDataDir + File.separator + "config"));

        File[] checkByWords = {new File(Minecraft.getMinecraft().mcDataDir, "options.txt")};

        String[] blackListedWords = {"Fullbright", "Xray", "Cave Finder", "Fly"};
        BufferedReader reader = null;

        File[] arrayOfFile1 = checkByWords;
        int i = arrayOfFile1.length;

        for (int j = 0; j < i; )
        {
            File f = arrayOfFile1[j];
            try
            {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
                String buffer = "";
                while ((buffer = reader.readLine()) != null)
                {
                    for (String word : blackListedWords)
                    {
                        if (buffer.toLowerCase().contains(word))
                        {
                        }
                    }
                }
                try
                {
                    reader.close();
                } catch (Exception localException)
                {
                }
                j++;
            } catch (FileNotFoundException localFileNotFoundException1)
            {
            } catch (IOException localIOException1)
            {
            } finally
            {
                try
                {
                    reader.close();
                } catch (Exception localException3)
                {
                }
            }
        }
    }
}

