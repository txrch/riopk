package com.app.appUser;

import com.app.enums.Role;
import com.app.system.exception.BadRequestException;
import com.app.system.exception.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxiServiceQualityUserService implements UserDetailsService {

    private final TaxiServiceQualityUserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .map(TaxiServiceQualityUserPrincipal::new)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с логином " + username + " не найден"));
    }

    public TaxiServiceQualityUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return repository.findByUsername(currentUserName).orElseThrow(() -> new ObjectNotFoundException("Пользователь не найден"));
        }
        return null;
    }

    public void admin() {
        if (repository.findAll().isEmpty()) {
            TaxiServiceQualityUser user = new TaxiServiceQualityUser("admin");
            user.setRole(Role.ADMIN);
            user.setPassword(passwordEncoder.encode("admin"));
            repository.save(user);
        }
    }

    public List<TaxiServiceQualityUser> findAll() {
        return repository.findAll();
    }

    public TaxiServiceQualityUser findById(String id) {
        try {
            Long longId = Long.parseLong(id);
            return repository.findById(longId).orElseThrow();
        } catch (Exception e) {
            throw new ObjectNotFoundException("Не найден пользователь с ИД: " + id);
        }
    }

    public TaxiServiceQualityUser save(TaxiServiceQualityUser user) {
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new BadRequestException("Пользователь с таким логином уже существует");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (findAll().isEmpty()) {
            user.setRole(Role.ADMIN);
        }
        return repository.save(user);
    }

    public TaxiServiceQualityUser update(TaxiServiceQualityUser user) {
        TaxiServiceQualityUser old = getCurrentUser();
        old.set(user);
        return repository.save(old);
    }

    public TaxiServiceQualityUser updateRole(String id, String role) {
        TaxiServiceQualityUser user = findById(id);
        try {
            user.setRole(Role.valueOf(role));
        } catch (Exception e) {
            throw new BadRequestException("Некорректный выбор роли");
        }
        return repository.save(user);
    }

    public TaxiServiceQualityUser updateEnabled(String id) {
        TaxiServiceQualityUser user = findById(id);
        user.setEnabled(!user.isEnabled());
        return repository.save(user);
    }

    public void deleteById(String userId) {
        TaxiServiceQualityUser user = findById(userId);
        repository.deleteById(user.getId());
    }

}
