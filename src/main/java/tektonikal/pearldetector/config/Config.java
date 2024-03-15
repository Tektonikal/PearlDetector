package tektonikal.pearldetector.config;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.sound.SoundEvents;

public class Config extends MidnightConfig {
    @Entry(name = "Mod enabled")
    public static boolean isEnabled = true;
    @Entry(name = "Pearl scale", isSlider = true, min = 0.1F, max = 5F)
    public static float pearlScale = 1f;
    @Entry(name = "Draw as lit")
    public static boolean lit = true;
    @Entry(name = "Play sound")
    public static boolean playSound = true;
    @Entry(name = "Sound ID")
    public static String soundID = SoundEvents.ENTITY_ENDERMAN_TELEPORT.getId().toString();
    @Entry(name = "Sound volume")
    public static float volume = 1;
    @Entry(name = "Sound pitch")
    public static float pitch = 1;
    @Entry(name = "Minimum detection distance")
    public static float minDistance = 3.5F;
    @Entry(name = "Maximum detection distance")
    public static float maxDistance = 100000;
}
