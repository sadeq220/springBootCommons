package demo.repository;

import demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface SpringDAO extends JpaRepository<demo.model.User,String>,Reference {

    @Override
    <S extends User> S save(S entity);


    User findByUserName(String s);

    @Override
    Optional<User> findById(String s);

    @Override
    User getOne(String s);

    @Override
    User getReference(String userName);
}
