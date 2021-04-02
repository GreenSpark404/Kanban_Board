package org.greenspark404.kanbanboard.data;

import org.greenspark404.kanbanboard.data.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByLogin(String login);

    User findByEmail(String email);


}
