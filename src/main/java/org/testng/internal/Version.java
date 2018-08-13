package org.testng.internal;

public class Version {

    public static final String VERSION = "DEV-SNAPSHOT-3e745818c8-6.14.8";

    public static String getVersionString() {
        return VERSION;
    }

    public static void displayBanner() {
        System.out.println("...\n... TestNG " + getVersionString() + " by Cédric Beust (cedric@beust.com)\n...\n");
    }
}
