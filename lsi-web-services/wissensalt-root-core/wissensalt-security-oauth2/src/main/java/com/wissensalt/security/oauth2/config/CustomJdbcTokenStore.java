package com.wissensalt.security.oauth2.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

/**
 * Created on 8/19/18.
 *
 * @author <a href="mailto:fauzi.knightmaster.achmad@gmail.com">Achmad Fauzi</a>
 */
public class CustomJdbcTokenStore extends JdbcTokenStore {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomJdbcTokenStore.class);

    public CustomJdbcTokenStore(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public OAuth2AccessToken readAccessToken(String tokenValue) {
        OAuth2AccessToken accessToken = null;

        try {
            accessToken = new DefaultOAuth2AccessToken(tokenValue);
        }
        catch (EmptyResultDataAccessException e) {
            if (LOGGER.isInfoEnabled()) {
                LOGGER.info("Failed to find access token for token {}", tokenValue);
            }
        }
        catch (IllegalArgumentException e) {
            LOGGER.warn("Failed to deserialize access token for {}, {}", tokenValue, e);
            removeAccessToken(tokenValue);
        }

        return accessToken;
    }
}
