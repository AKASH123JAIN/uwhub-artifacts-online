package com.ew.ajbackend.uwhubuser;
import com.ew.ajbackend.system.exception.ObjectNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UWHubUser> findAll() {
        return this.userRepository.findAll();
    }

    public UWHubUser findById(Integer userId) {
        return this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
    }

    public UWHubUser save(UWHubUser newHogwartsUser) {
        // We NEED to encode plain password before saving to the DB! TODO
        return this.userRepository.save(newHogwartsUser);
    }

    /**
     * We are not using this update to change user password.
     *
     * @param userId
     * @param update
     * @return
     */
    public UWHubUser update(Integer userId, UWHubUser update) {
        UWHubUser oldHogwartsUser = this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
        oldHogwartsUser.setUsername(update.getUsername());
        oldHogwartsUser.setEnabled(update.isEnabled());
        oldHogwartsUser.setRoles(update.getRoles());
        return this.userRepository.save(oldHogwartsUser);
    }

    public void delete(Integer userId) {
        this.userRepository.findById(userId)
                .orElseThrow(() -> new ObjectNotFoundException("user", userId));
        this.userRepository.deleteById(userId);
    }

}