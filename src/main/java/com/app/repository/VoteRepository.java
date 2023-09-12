package com.app.repository;

import com.app.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByCandidateId(Long candidateId);

    List<Vote> findByCandidateConstituencyId(Long constituencyId);
}
