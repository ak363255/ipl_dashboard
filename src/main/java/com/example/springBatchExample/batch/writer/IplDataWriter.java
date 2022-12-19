package com.example.springBatchExample.batch.writer;

import com.example.springBatchExample.model.Match;
import com.example.springBatchExample.repository.IplDataRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IplDataWriter implements ItemWriter<Match> {
    @Autowired
    IplDataRepository iplDataRepository;
    @Override
    public void write(List<? extends Match> list) throws Exception {
        System.out.println("size is "+list.size());
        iplDataRepository.saveAll(list);
    }
}
