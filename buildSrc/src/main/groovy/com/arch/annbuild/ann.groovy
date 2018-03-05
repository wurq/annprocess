package com.arch.annbuild

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.arch.annbuild.exten.ComExtension

class ann implements Plugin<Project> {
     @Override
     void apply(Project project) {

         project.extensions.create('ann', ComExtension);
         System.out.println("apply plugin ann is " + 'com.android.library')

     }
 }
