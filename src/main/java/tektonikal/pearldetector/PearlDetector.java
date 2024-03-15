package tektonikal.pearldetector;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import tektonikal.pearldetector.config.Config;

public class PearlDetector implements ModInitializer {
    @Override
    public void onInitialize() {
        MidnightConfig.init("pearldetector", Config.class);
    }
}
