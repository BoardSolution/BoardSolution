package com.example.boardsolution.service;

import com.example.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;


@Service
@Transactional
public class MybaService {
    @Autowired
    TestMapper mapper;

    @Autowired
    PlatformTransactionManager manager;

    public int totSize(String str) {
//추가함ㅈㄷㅎ
        TransactionStatus status =
                manager.getTransaction(new DefaultTransactionDefinition());
        int totSize =0;
        try {
            totSize = mapper.totSize("");

            //manager.commit(status);
            //manager.rollback(status);

        }catch(Exception ex) {
            ex.printStackTrace();
        }
        return totSize;
    }

}