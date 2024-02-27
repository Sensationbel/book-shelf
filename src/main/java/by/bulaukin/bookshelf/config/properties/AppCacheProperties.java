package by.bulaukin.bookshelf.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "app.cache")
public class AppCacheProperties {

    private final List<String> cacheNames = new ArrayList<>();
    private final Map<String, CacheProperties> caches = new HashMap<>();
    private final CacheType cacheType;

    @Data
    public static class CacheProperties {
        private Duration expire = Duration.ZERO;
    }

    public interface CacheNames {
    String DATABASE_ENTITIES_BY_BOOKNAME_AND_AUTHOR = "databaseEntitiesByBookNameAndAuthor";
    String DATABASE_ENTITIES_BY_CATEGORY = "databaseEntitiesByCategory";
    String DATABASE_ENTITIES_BY_ID ="databaseEntitiesById";
    }

    public enum CacheType {
        REDIS
    }

}
