package br.com.rfb;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("br.com.rfb");

        noClasses()
            .that()
                .resideInAnyPackage("br.com.rfb.service..")
            .or()
                .resideInAnyPackage("br.com.rfb.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..br.com.rfb.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
