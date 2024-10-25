package com.capstone.lovemarker.convention

import org.gradle.api.Project

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "com.capstone.lovemarker.$name"
    }
}
