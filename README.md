# ddm-starter-security
Project with configuration for authentication.

## Usage
Auto-configuration should be activated through the `@SpringBootApplication` annotation
More advanced way: 
* using `@EnableAutoConfiguration` annotation in main class

Also you should specify dependency in your service:
```xml
<dependencies>
    ...
    <dependency>
          <groupId>com.epam.digital.data.platform</groupId>
          <artifactId>ddm-starter-security</artifactId>
          <version>...</version>
        </dependency>
    ...
</dependencies>
```

### Configuration properties
Available properties are following:
* `platform.security.enabled` (`boolean|default - true`) - whether auth configuration should be applied or not if false then local security configuration applies

