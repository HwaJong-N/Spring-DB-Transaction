package ghkwhd.springtx.apply;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@SpringBootTest
public class InternalCallV1Test {


    @Autowired
    CallService callService;



    @Test
    void printProxy() {
        log.info("callService class={}", callService.getClass());
    }

    @Test
    void internalCall() {
        callService.internal();
    }


    @Test
    void externalCall() {
        callService.external();
    }



    @TestConfiguration
    static class InternalCallV1TestConfig {
        @Bean
        CallService callService() {
            return new CallService();
        }
    }



    @Slf4j
    static class CallService{
        
        // 외부에서 호출하는 메서드
        public void external() {
            log.info("call external");
            printTxInfo();
            internal();
        }

        // 트랜잭션 적용이 필요한 메서드
        @Transactional
        public void internal() {
            log.info("call internal");
            printTxInfo();
        }


        private void printTxInfo() {
            boolean txActive = TransactionSynchronizationManager.isActualTransactionActive();
            log.info("tx Active={}", txActive);
        }
    }



}
