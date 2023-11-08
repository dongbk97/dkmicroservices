package dev.ngdangkiet.service;

import dev.ngdangkiet.domain.EmployeeEntity;
import dev.ngdangkiet.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author ngdangkiet
 * @since 11/6/2023
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public Mono<EmployeeEntity> loadUserByUsername(String username) throws UsernameNotFoundException {
        return Mono.justOrEmpty(employeeRepository.findEmployeeByEmail(username));
    }
}
