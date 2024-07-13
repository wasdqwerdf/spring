package com.github.wasdqwerdf.spring.projectWizard

import com.intellij.icons.AllIcons
import com.intellij.ide.starters.local.*
import com.intellij.ide.starters.local.wizard.StarterInitialStep
import com.intellij.ide.starters.shared.*
import com.intellij.openapi.observable.properties.GraphProperty
import com.intellij.openapi.util.Key
import com.intellij.pom.java.LanguageLevel
import com.intellij.ui.dsl.builder.*
import com.intellij.util.lang.JavaVersion
import javax.swing.Icon


/**
 * Spring ModuleBuilder
 */
class SpringModuleBuilder :StarterModuleBuilder() {
    override fun getBuilderId(): String = "Spring Boot Helper"
    override fun getDescription(): String = "Spring Boot Helper"
    override fun getNodeIcon(): Icon = AllIcons.Nodes.Plugin
    override fun getPresentableName(): String = "Spring Boot Helper"

    // 语言: Java 和 Kotlin
    override fun getLanguages(): List<StarterLanguage> {
        return listOf(JAVA_STARTER_LANGUAGE, KOTLIN_STARTER_LANGUAGE)
    }

    override fun getProjectTypes(): List<StarterProjectType> {
        return listOf(MAVEN_PROJECT, GRADLE_PROJECT)
    }

    // JDK17及以上
    override fun getMinJavaVersion(): JavaVersion = LanguageLevel.JDK_17.toJavaVersion()

    override fun getAssets(starter: Starter): List<GeneratorAsset> {
        val assets = mutableListOf<GeneratorAsset>()
        return assets
    }

    override fun getStarterPack(): StarterPack {
        return StarterPack("spring boot", listOf())
    }

    override fun createOptionsStep(contextProvider: StarterContextProvider): StarterInitialStep {
        return SpringBootInitialStep(contextProvider)
    }

    private val packingTypeKey: Key<PackagingType> = Key.create("spring.boot.packaging.type")

    fun setPackagingType(packagingType: PackagingType) {
        return starterContext.putUserData(packingTypeKey, packagingType)
    }

    private fun getPackagingType(): PackagingType {
        return starterContext.getUserData(packingTypeKey) ?: PackagingType.JAR
    }

    private inner class SpringBootInitialStep(contextProvider: StarterContextProvider) :
        StarterInitialStep(contextProvider) {
        private val typeProperty: GraphProperty<PackagingType> = propertyGraph.property(PackagingType.JAR)
        override fun addFieldsAfter(layout: Panel) {
            layout.row("Package name:") {
                textField()
                    .columns(COLUMNS_MEDIUM)
                    .gap(RightGap.SMALL)
            }.bottomGap(BottomGap.SMALL)

            layout.row("Java:") {

            }

            layout.row("Packaging:") {
                segmentedButton(listOf(PackagingType.JAR, PackagingType.WAR)) { text = it.typeName }
                    .bind(typeProperty)
            }.bottomGap(BottomGap.SMALL)
            setPackagingType(PackagingType.JAR)

            layout.row("Server URL:") {

            }

            layout.row("Spring Boot:") {

            }
        }
    }

    enum class PackagingType(
        val typeName: String
    ) {
        JAR("Jar"),
        WAR("War")
    }
}