package api.repository;


import api.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : NozjkoiTop
 * @Date : Created in 12.02.2022
 */

@Service
@Repository

public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    List<Developer> findDeveloperByEmail (String email);
}
