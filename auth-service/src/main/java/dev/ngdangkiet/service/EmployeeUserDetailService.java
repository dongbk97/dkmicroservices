package dev.ngdangkiet.service;

import dev.ngdangkiet.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author ngdangkiet
 * @since 11/6/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeUserDetailService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findEmployeeByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Not found username with value " + username));
    }
}
