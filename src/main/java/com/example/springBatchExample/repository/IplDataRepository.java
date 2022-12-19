package com.example.springBatchExample.repository;

import com.example.springBatchExample.model.Match;
import com.example.springBatchExample.model.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IplDataRepository extends JpaRepository<Match,Long> {

    @Query(value = "select i.team1, count(*) from ipldata i group by i.team1",nativeQuery = true)
    List<Object[]> distnictTeam1();

    @Query(value = "select i.team2, count(*) from ipldata i group by i.team2",nativeQuery = true)
    List<Object[]> distnictTeam2();

    @Query(value = "select count(*) from ipldata i where i.match_winner = :team",nativeQuery = true)
    List<Object[]> totalWinsByATeam(@Param("team") String team);

    @Query(value = "select * from ipldata  where ipldata.team1 = :team or ipldata.team2 = :team order by ipldata.date desc",nativeQuery = true)
    Page<List<Match>> getAllMatchesPlayedByATeam(@Param("team") String team, Pageable page);


}
