package org.ibd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.ibd.controller.AnsiCodes;
import org.ibd.model.TestDBItem;
import org.ibd.repository.AbstractMongoRepository;

import java.util.ArrayList;

public class Main {
    protected static final Logger logger = (Logger) LogManager.getLogger();

    public static void main(String[] args) {
        // Sorry for the disgusting logs, MongoDB logger is broken (config issues)
        // Output is highlighted in green / red
        try (AbstractMongoRepository mongoRepository = new AbstractMongoRepository()) {
            TestDBItem testDBItem = new TestDBItem("TestName", 420);
            mongoRepository.testAdd(testDBItem);
            ArrayList<TestDBItem> testDBItemArrayList = mongoRepository.testGet();
            System.out.println(AnsiCodes.ANSI_GREEN + testDBItemArrayList.get(0) + AnsiCodes.ANSI_RESET);
        } catch (Exception e) {
            System.out.println(AnsiCodes.ANSI_RED + "oops:\n" + e + AnsiCodes.ANSI_RESET);
        }
    }
}