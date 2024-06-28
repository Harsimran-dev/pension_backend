package com.pensionbackend.services.jwt;

import org.springframework.security.core.userdetails.User;

import com.pensionbackend.entities.PensionUser;
import com.pensionbackend.repositories.PensionUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;




import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PensionUserRepository pensionuserRepository;

    public UserDetailsServiceImpl(PensionUserRepository pensionuserRepository) {
        this.pensionuserRepository = pensionuserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<PensionUser> optionaluser = pensionuserRepository.findByEmail(email);
        if (optionaluser.isEmpty())
            throw new UsernameNotFoundException("User not found", null);
        return new User(optionaluser.get().getEmail(), optionaluser.get().getPassword(), new ArrayList());

    }

}