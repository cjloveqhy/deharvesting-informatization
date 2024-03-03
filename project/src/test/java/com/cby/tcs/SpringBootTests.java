package com.cby.tcs;

import com.cby.tcs.permission.entity.po.Permission;
import com.freedom.cloud.enums.MenuType;
import com.freedom.cloud.generate.InteractionGenerate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringBootTests {

    public static void main(String[] args) {
        InteractionGenerate.generatorAll();
    }

    @Test
    void addServerNode() {
        new Permission().setType(MenuType.Service).setName("脱采收权限根节点").insert();
    }
}
