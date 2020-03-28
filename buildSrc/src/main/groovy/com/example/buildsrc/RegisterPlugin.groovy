package com.example.buildsrc

import com.android.build.gradle.internal.plugins.AppPlugin;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class RegisterPlugin implements Plugin<Project> {
    public static final String EXT_NAME = "autoregister"
    @Override
    public void apply(Project project) {
        def isApp = project.plugins.hasPlugin(AppPlugin)
        project.extensions.create(EXT_NAME,AutoRegisterConfig)
    }
}
