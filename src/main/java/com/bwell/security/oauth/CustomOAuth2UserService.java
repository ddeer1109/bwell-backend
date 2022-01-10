package com.bwell.security.oauth;

import com.bwell.exception.OAuth2AuthenticationProcessingException;
import com.bwell.security.UserPrincipal;
import com.bwell.security.oauth.user.OAuth2UserInfo;
import com.bwell.security.oauth.user.OAuth2UserInfoFactory;
import com.bwell.user.data.model.AuthProvider;
import com.bwell.user.data.model.Credentials;
import com.bwell.user.data.model.User;
import com.bwell.user.data.repository.CredentialsRepository;
import com.bwell.user.data.repository.UserRepository;
import com.bwell.user.data.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final CredentialsRepository credentialsRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException e) {
            throw e;
        }catch(Exception e){
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo auth2UserInfo = OAuth2UserInfoFactory
                .get0Auth2UserInfo(
                        userRequest.getClientRegistration().getRegistrationId(),
                        oAuth2User.getAttributes()
                );
        if (StringUtils.isEmpty(auth2UserInfo.getEmail()))
            throw new OAuth2AuthenticationProcessingException("Email not provided");

        Optional<Credentials> userOptional = credentialsRepository.findByEmail(auth2UserInfo.getEmail());
//        Optional<User> userOptional = userRepository.findByCredentialsProviderId(auth2UserInfo.getId());
        Credentials appUser;
        if (userOptional.isPresent()){
            appUser = userOptional.get();
            appUser = update(appUser, auth2UserInfo);
        } else {
            appUser = register(userRequest, auth2UserInfo);
        }

        return UserPrincipal.create(appUser);

    }

    private Credentials update(Credentials appUser, OAuth2UserInfo auth2UserInfo) {
        appUser.setName(auth2UserInfo.getName());
        appUser.setImageUrl(auth2UserInfo.getImgUrl());
        return credentialsRepository.save(appUser);
    }

    private Credentials register(OAuth2UserRequest userRequest, OAuth2UserInfo auth2UserInfo) {
        User appUser = userRepository.save(UserService.createEmptyUser());

        appUser.getCredentials().setProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()));
        appUser.getCredentials().setProviderId(auth2UserInfo.getId());
        appUser.getCredentials().setName(auth2UserInfo.getName());
        appUser.getCredentials().setEmail(auth2UserInfo.getEmail());
        appUser.getCredentials().setImageUrl(auth2UserInfo.getImgUrl());
        appUser.getCredentials().setUser(appUser);
        appUser.getCredentials().setVerified(true);
        appUser = userRepository.save(appUser);
        Credentials credentials = credentialsRepository.save(appUser.getCredentials());
        return credentials;

    }
}
