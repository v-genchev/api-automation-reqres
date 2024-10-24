package helpers;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class Properties {
    private final PropertiesConfiguration properties = new PropertiesConfiguration();

    private Properties() {
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("config.properties");) {
            InputStreamReader reader = new InputStreamReader(Objects.requireNonNull(in));
            properties.read(reader);
        } catch (ConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    private static final class InstanceHolder {
        private static final Properties instance = new Properties();
    }

    public static Properties getInstance() {
        return InstanceHolder.instance;
    }

    public String getProperty(String key) {
        return properties.getString(key);
    }
}
