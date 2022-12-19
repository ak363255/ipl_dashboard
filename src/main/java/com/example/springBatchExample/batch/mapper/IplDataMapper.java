package com.example.springBatchExample.batch.mapper;


import com.example.springBatchExample.data.MatchInput;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class IplDataMapper implements FieldSetMapper<MatchInput> {
    @Override
    public MatchInput mapFieldSet(FieldSet fieldSet) throws BindException {
        MatchInput matchInput = new MatchInput();
        matchInput.setId(fieldSet.readString("id"));
        matchInput.setCity(fieldSet.readString("city"));
        matchInput.setDate(fieldSet.readString("date"));
        matchInput.setPlayer_of_match(fieldSet.readString("player_of_match"));
        matchInput.setVenue(fieldSet.readString("venue"));
        matchInput.setNeutral_venue(fieldSet.readString("neutral_venue"));
        matchInput.setTeam1(fieldSet.readString("team1"));
        matchInput.setTeam2(fieldSet.readString("team2"));
        matchInput.setToss_winner(fieldSet.readString("toss_winner"));
        matchInput.setToss_decision(fieldSet.readString("toss_decision"));
        matchInput.setWinner(fieldSet.readString("winner"));
        matchInput.setResult(fieldSet.readString("result"));
        matchInput.setResult_margin(fieldSet.readString("result_margin"));
        matchInput.setEliminator(fieldSet.readString("eliminator"));
        matchInput.setMethod(fieldSet.readString("method"));
        matchInput.setUmpire1(fieldSet.readString("umpire1"));
        matchInput.setUmpire2(fieldSet.readString("umpire2"));
        return matchInput;
    }
}
