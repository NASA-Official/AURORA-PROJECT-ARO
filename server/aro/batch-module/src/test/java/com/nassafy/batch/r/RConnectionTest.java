package com.nassafy.batch.r;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RConnectionTest {
    @Test
    @DisplayName("r connection test")
    public void rConnectionTest ()  {
        RConnection conn = null;
        double[] x;
        try {
            conn = new RConnection("rstudio", 6311);  // 로컬에서는 host를 j8d106.p.ssafy.io로, 코드 올릴 때는 rstudio로 변경하기
            REXP exp = conn.eval("source('/home/rstudio/getAceLocData2.R')");
            RList rList = conn.eval("getAceLocData()").asList();

            x = rList.at("GES_X").asDoubles();

        } catch (RserveException | REXPMismatchException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }

        Assertions.assertThat(x[0]).isEqualTo(246.3);
    }
}
