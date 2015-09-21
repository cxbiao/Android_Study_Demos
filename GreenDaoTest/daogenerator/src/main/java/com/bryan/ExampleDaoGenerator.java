package com.bryan;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.bryan.greendao");
        //Keep sections 防止覆盖
        schema.enableKeepSectionsByDefault();
        Entity note= schema.addEntity("Note");
        note.addIdProperty();
        note.addStringProperty("text").notNull();
        note.addStringProperty("comment");
        note.addDateProperty("date");
        new DaoGenerator().generateAll(schema, "F:/school/GreenDaoTest/app/src/main/java-gen");
    }
}
