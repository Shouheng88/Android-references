package me.shouheng.knife.compiler.model;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.LinkedList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import me.shouheng.knife.compiler.TypeUtils;

/**
 * @author shouh
 * @version $Id: AnnotatedClass, v 0.1 2018/8/22 22:55 shouh Exp$
 */
public class AnnotatedClass {

    private TypeElement typeElement;

    private List<BindViewField> bindViewFields;

    private List<OnClickMethod> onClickMethods;

    private Elements elements;

    public AnnotatedClass(TypeElement typeElement, Elements elements) {
        this.typeElement = typeElement;
        this.bindViewFields = new LinkedList<>();
        this.onClickMethods = new LinkedList<>();
        this.elements = elements;
    }

    public String getFullClassName() {
        return typeElement.getQualifiedName().toString();
    }

    public void addField(BindViewField bindViewField) {
        bindViewFields.add(bindViewField);
    }

    public void addMethod(OnClickMethod method) {
        onClickMethods.add(method);
    }

    public JavaFile generateFinder() {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("inject")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(TypeName.get(typeElement.asType()), "host", Modifier.FINAL)
                .addParameter(TypeName.OBJECT, "source")
                .addParameter(TypeUtils.FINDER, "finder");
        for (BindViewField field : bindViewFields) {
            builder.addStatement("host.$N=($T)finder.findView(source, $L)",
                    field.getFieldName(),
                    ClassName.get(field.getFieldType()),
                    field.getViewId());
        }
        if (onClickMethods.size() > 0) {
            builder.addStatement("$T listener", TypeUtils.ONCLICK_LISTENER);
        }
        for (OnClickMethod method : onClickMethods) {
            TypeSpec listener = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(TypeUtils.ONCLICK_LISTENER)
                    .addMethod(MethodSpec.methodBuilder("onClick")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(TypeName.VOID)
                            .addParameter(TypeUtils.ANDROID_VIEW, "view")
                            .addStatement("host.$N()", method.getMethodName())
                            .build())
                    .build();
            builder.addStatement("listener = $L", listener);
            for (int id : method.getIds()) {
                builder.addStatement("finder.findView(source, $L).setOnClickListener(listener)", id);
            }
        }
        String packageName = getPackageName(typeElement);
        String className = getClassName(typeElement, packageName);
        ClassName bindClassName = ClassName.get(packageName, className);

        TypeSpec finderClass = TypeSpec.classBuilder(bindClassName.simpleName() + "$$Injector")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtils.INJECTOR, TypeName.get(typeElement.asType())))
                .addMethod(builder.build())
                .build();
        return JavaFile.builder(packageName, finderClass).build();
    }

    private String getPackageName(TypeElement type) {
        return elements.getPackageOf(type).getQualifiedName().toString();
    }

    private String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen).replace('.', '$');
    }
}
