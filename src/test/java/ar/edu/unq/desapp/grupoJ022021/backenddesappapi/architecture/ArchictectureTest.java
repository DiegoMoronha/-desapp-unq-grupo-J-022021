package ar.edu.unq.desapp.grupoJ022021.backenddesappapi.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchictectureTest {
    private JavaClasses importedClasses;

    @BeforeEach
    public void setup() {
        importedClasses = new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .importPackages("ar.edu.unq.desapp.grupoJ022021.backenddesappapi");
    }
    @Test
    void serviceClassesShouldOnlyBeAccessedByController() {
        classes()
                .that().resideInAPackage("..service..")
                .should().onlyBeAccessed().byAnyPackage("..service..", "..controller..")
                .check(importedClasses);
    }
    @Test
    public void serviceClassesShouldContainSufixService(){
        classes()
                .that().resideInAPackage("..service..")
                .should().haveSimpleNameEndingWith("Service");
    }

    @Test
    public void classesControllerMustBeReturnResponseEntity() {
        methods().that().areDeclaredInClassesThat().resideInAPackage("..controller..")
                .and().haveRawReturnType(ResponseEntity.class);
    }
}
