package pw.springdev.spring.batch.ordersparser.helper;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author      FedorD
 * Created on   2020-04-14
 */
public class ResourceHelper {

    public static Resource[] getResources(String argFiles) {
        String[] files = argFiles.trim().split(",");
        Set<Resource> resourceSet = new HashSet<>();


        for (String file : files) {
            resourceSet.add(new FileSystemResource(file));
            //"src/main/resources/".concat(file)));
        }

        Resource[] resources = new Resource[resourceSet.size()];

        return resourceSet.toArray(resources);
    }
}
