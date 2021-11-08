package com.bwell.modules.security.oauth.user;

import com.bwell.modules.exception.OAuth2AuthenticationProcessingException;
import com.bwell.modules.user.data.model.AuthProvider;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo get0Auth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.google.toString())){
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(registrationId + " is not supported");
        }
    }
}
