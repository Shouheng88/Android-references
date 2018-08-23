package me.shouheng.knife.compiler.model;

import java.util.LinkedList;
import java.util.List;

import javax.lang.model.element.TypeElement;

/**
 * @author shouh
 * @version $Id: AnnotatedClass, v 0.1 2018/8/22 22:55 shouh Exp$
 */
public class AnnotatedClass {

    private TypeElement typeElement;

    private List<BindViewField> bindViewFields;

    private List<OnClickMethod> onClickMethods;

    public AnnotatedClass(TypeElement typeElement) {
        this.typeElement = typeElement;
        this.bindViewFields = new LinkedList<>();
        this.onClickMethods = new LinkedList<>();
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


}
