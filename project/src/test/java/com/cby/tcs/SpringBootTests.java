package com.cby.tcs;

import com.freedom.cloud.generate.InteractionGenerate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBootTests {

    public static void main(String[] args) {
        InteractionGenerate.generatorAll();
    }
}
