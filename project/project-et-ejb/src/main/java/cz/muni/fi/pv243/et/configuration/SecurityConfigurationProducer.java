package cz.muni.fi.pv243.et.configuration;

import org.jboss.ejb3.annotation.Clustered;import org.picketbox.core.config.ConfigurationBuilder;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@Clustered
@ApplicationScoped
public class SecurityConfigurationProducer {

    @Inject
    private IdentityManagerLookupStrategy entityManagerLookupStrategy;

    @Produces
    public ConfigurationBuilder configure() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

        // configures a JPA-based identity store.
        configurationBuilder.identityManager().jpaStore().entityManagerLookupStrategy(entityManagerLookupStrategy);

        return configurationBuilder;
    }

}
