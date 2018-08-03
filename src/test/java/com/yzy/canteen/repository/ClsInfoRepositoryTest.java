package com.yzy.canteen.repository;

import com.yzy.canteen.dataobject.ClsInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ClsInfoRepositoryTest {
    @Autowired
    private ClsInfoRepository repository;
    @Test
    public void findByIdTest() {
        Optional<ClsInfo> clsInfo = repository.findById(1);
        System.out.println(clsInfo.toString());
        assertNotNull(clsInfo);
    }

}