package biz.lwb.system.holder;

import com.google.common.base.Preconditions;
import com.google.common.io.ByteStreams;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kzarychta.
 */
public class YamlObjectFactory {

    public static final Logger LOG = LoggerFactory.getLogger(YamlObjectFactory.class);

    private static final String ROOT = "/";

    private String directory = ROOT;

    private YamlObjectFactory(String directory) {
        if (StringUtils.isNotBlank(directory)) {
            this.directory += directory + "/";
        }
    }

    public <Any> Any create(String fileName, Class resultClass) {
        String filePath = getFilePath(fileName);
        //noinspection unchecked
        try (InputStream is = this.getClass().getResourceAsStream(filePath)) {
            Preconditions.checkNotNull(is);
            //noinspection unchecked
            return (Any) new Yaml().loadAs(new ByteArrayInputStream(ByteStreams.toByteArray(is)), resultClass);
        } catch (IOException e) {
            LOG.error("Error during creation object of type {} from {} file", resultClass, filePath);
        }
        return null;
    }

    public String dump(Object object) {
        DumperOptions options = new DumperOptions();
        options.setAllowUnicode(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        return new Yaml(options).dump(object);
    }

    private String getFilePath(String fileName) {
        return directory + fileName + ".yaml";
    }

    public static YamlObjectFactory yamlObjectFactory() {
        return new YamlObjectFactory(null);
    }

    public static YamlObjectFactory yamlObjectFactory(String directory) {
        return new YamlObjectFactory(directory);
    }
}