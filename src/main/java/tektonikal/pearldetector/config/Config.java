package tektonikal.pearldetector.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.sound.SoundEvents;

public class Config extends MidnightConfig {
    @Entry(name = "Mod Enabled")
    public static boolean isEnabled = true;
    @Entry(name = "Pearl Scale", isSlider = true, min = 0.1F, max = 5F)
    public static float pearlScale = 1f;
    @Entry(name = "Light Level", isSlider = true, min = -1, max = 255)
    public static int lightLevel = 255;
    @Entry(name = "Play Sound")
    public static boolean playSound = true;
    @Entry(name = "Sound ID")
    public static String soundID = SoundEvents.ENTITY_ENDERMAN_TELEPORT.getId().toString();
    @Entry(name = "Sound Volume", isSlider = true, min = 0.1F, max = 3)
    public static float volume = 1;
    @Entry(name = "Sound pitch", isSlider = true, min = 0.1F, max = 3)
    public static float pitch = 1;
    @Entry(name = "Minimum Distance", min = 0, max = 10)
    public static float minDistance = 3.5F;
    @Entry(name = "Maximum Distance", min = 20)
    public static float maxDistance = 1000;
}
