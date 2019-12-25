package com.kylingo.plugin.publish

import org.gradle.api.Plugin
import org.gradle.api.Project

public class PluginPublish implements Plugin<Project> {

    @Override
    void apply(Project project) {
        System.out.println("========================")
        System.out.println("hello gradle plugin version:" + "1.0.8")
        System.out.println("========================")
    }
}