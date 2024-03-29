package br.com.workmade.security.oauth2;

import br.com.workmade.service.CustomUserDetailService;
import lombok.NoArgsConstructor;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
@NoArgsConstructor
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    private TokenStore tokenStore = new InMemoryTokenStore();
    private String client = "frontend";
    private String clientSecret = "front123";
    private static final String RESOURCE_ID = "rest_service";


    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailService userDetailsService;

    @Autowired
    AuthorizationServerConfiguration(
            @Qualifier(value = "authenticationManagerBean") AuthenticationManager authenticationManager
    ){
        this.authenticationManager = authenticationManager;
    }


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(this.tokenStore)
                .authenticationManager(this.authenticationManager)
                .userDetailsService(this.userDetailsService);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient(this.client)
                .secret(new BCryptPasswordEncoder().encode(this.clientSecret))
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("bar", "read", "write")
                .resourceIds(this.RESOURCE_ID)
                .accessTokenValiditySeconds(60)
                .refreshTokenValiditySeconds(60*60*24);
    }


    @Bean
    @Primary
    public DefaultTokenServices defaultTokenServices(){
        var tokenService = new DefaultTokenServices();
        tokenService.setSupportRefreshToken(true);
        tokenService.setAccessTokenValiditySeconds(0);
        tokenService.setTokenStore(this.tokenStore);
        return tokenService;
    }
}
