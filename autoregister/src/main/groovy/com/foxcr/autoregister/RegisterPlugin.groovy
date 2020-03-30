package com.foxcr.autoregister

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class RegisterPlugin implements Plugin<Project> {
    public static final String EXT_NAME = "autoregister";
    @Override
    void apply(Project project) {
        /**
         * 注册transform接口
         */
        def isApp = project.plugins.hasPlugin(AppPlugin)
        project.extensions.create(EXT_NAME, AutoRegisterConfig)
        if (isApp) {
            println 'project(' + project.name + ') apply auto-register plugin'
            def android = project.extensions.getByType(AppExtension)
            def transformImpl = new RegisterTransform(project)
            android.registerTransform(transformImpl)
            project.afterEvaluate {
                init(project, transformImpl)//此处要先于transformImpl.transform方法执行
            }
        }
    }

    static void init(Project project, RegisterTransform transformImpl) {
        AutoRegisterConfig config = new AutoRegisterConfig()
        ArrayList<Map<String, Object>> registerInfo = new ArrayList<>()
        Map<String, Object> registerInfoMap = new HashMap<>()
        registerInfoMap.put('scanInterface','com.foxcr.ycdevcore.integration.ConfigModule')
        registerInfoMap.put('codeInsertToClassName','com.foxcr.ycdevcore.base.delegate.AppDelegate')
        registerInfoMap.put('codeInsertToMethodName','loadAutoRegister')
        registerInfoMap.put('registerMethodName','register')

        registerInfo.add(registerInfoMap)
        config.registerInfo.addAll(registerInfo)
        config.project = project
        config.convertConfig()
        transformImpl.config = config
    }
}
