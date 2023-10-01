package com.polkins;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import java.util.UUID;

@Slf4j
@ContextConfiguration(initializers = TestPostgresContainer.Initializer.class)
public class TestPostgresContainer {

    private static final String SUBSTITUTED_NAME = "postgres";
    private static final String IMAGE_NAME = "postgres:14.1";
    private static final String CONTAINER_USERNAME = "postgres";
    private static final String CONTAINER_PASSWORD = "postgres";
    private static final String CONTAINER_SCHEMA = "music_metadata";
    private static final String CONTAINER_INIT_SCRIPT = "dev/1-init.sql";

    @SuppressWarnings("rawtypes")
    private static final PostgreSQLContainer postgreSQLContainer;

    static {
        DockerImageName postgresImage = DockerImageName.parse(IMAGE_NAME)
                .asCompatibleSubstituteFor(SUBSTITUTED_NAME);

        postgreSQLContainer = new PostgreSQLContainer<>(postgresImage)
                .withReuse(true)
                .withUsername(CONTAINER_USERNAME)
                .withPassword(CONTAINER_PASSWORD)
                .withUrlParam("currentSchema", CONTAINER_SCHEMA)
                .withExposedPorts(5432)
                .withInitScript(CONTAINER_INIT_SCRIPT)
                .withCreateContainerCmdModifier(it -> it.withName("test_containers_pg_container_" + UUID.randomUUID()))
                .waitingFor(Wait.forListeningPort());

        postgreSQLContainer.start();
    }

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues
                    .of(springApplicationProperties())
                    .applyTo(applicationContext.getEnvironment(), TestPropertyValues.Type.SYSTEM_ENVIRONMENT, "test");
        }

        private static String[] springApplicationProperties() {

            return new String[]{
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword(),
                    "spring.liquibase.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.liquibase.user=" + postgreSQLContainer.getUsername(),
                    "spring.liquibase.password=" + postgreSQLContainer.getPassword()
            };
        }
    }

}
