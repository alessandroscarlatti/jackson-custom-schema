package com.scarlatti;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Wednesday, 4/18/2018
 */
public class TestModule extends Module {

    @Override
    public String getModuleName() {
        return "javaTimeJsonSchemaModule";
    }

    @Override
    public Version version() {
        return new Version(0, 0, 1, "SNAPSHOT", null, null);
    }

    @Override
    public void setupModule(SetupContext context) {
        System.out.println("setting up module");
    }
}
