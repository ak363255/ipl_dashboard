package com.example.springBatchExample.batch.processor;
import com.example.springBatchExample.data.MatchInput;
import com.example.springBatchExample.model.Match;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class IplDataProcessor  implements ItemProcessor<MatchInput, Match> {

    @Override
    public Match process(MatchInput item) throws Exception {
        Match match = new Match();
        if (item != null) {
            if (item.getId() != null) {
                match.setId(Long.parseLong(item.getId()));
            }
            if (item.getDate() != null) {
                String dateString = item.getDate();
                System.out.println("data is "+dateString);
                LocalDate localDateTime = LocalDate.parse(dateString);
                match.setDate(localDateTime);
            }
            match.setCity(item.getCity());
            match.setMatchWinner(item.getWinner());
            match.setPlayerOfMatch(item.getPlayer_of_match());
            match.setResult(item.getResult());
            match.setResultMargin(item.getResult_margin());

            String firstInningTeam, secondInningTeam;

            if ("bat".equalsIgnoreCase(item.getToss_decision())) {
                firstInningTeam = item.getToss_winner();
                secondInningTeam = firstInningTeam.equalsIgnoreCase(item.getTeam1()) ? item.getTeam2() : item.getTeam1();
            } else {
                secondInningTeam = item.getToss_winner();
                firstInningTeam = secondInningTeam.equalsIgnoreCase(item.getTeam1()) ? item.getTeam2() : item.getTeam1();
            }
            match.setTeam1(firstInningTeam);
            match.setTeam2(secondInningTeam);
            match.setTossDecision(item.getToss_decision());
            match.setTossWinner(item.getToss_winner());
            match.setUmpire1(item.getUmpire1());
            match.setUmpire2(item.getUmpire2());
            match.setVenue(item.getVenue());
            return match;
        }
        return null;
    }
}
