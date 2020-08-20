package demo.repository;

import demo.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface DAOmessage extends JpaRepository<demo.model.Message, LocalDateTime> {

    @Override
    <S extends Message> S save(S entity);

    @Override
    Page<Message> findAll(Pageable pageable);


    @Query(value = "select e from Message e where e.user.userName=:username")
    List<Message> findParticular(@Param("username") String username,Pageable pageable);
}
