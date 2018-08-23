package me.shouheng.knife.compiler.model;

import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;

import me.shouheng.knife.annotation.OnClick;

/**
 * @author shouh
 * @version $Id: OnClickMethod, v 0.1 2018/8/22 22:56 shouh Exp$
 */
public class OnClickMethod {

    private Name methodName;

    private int[] ids;

    public OnClickMethod(Element element) {
        if (element.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException(String.format("Only method can be annotated with %s", OnClick.class.getSimpleName()));
        }
        ExecutableElement executableElement = (ExecutableElement) element;
        methodName = executableElement.getSimpleName();
        OnClick onClick = executableElement.getAnnotation(OnClick.class);
        ids = onClick.ids();
        for (int id : ids) {
            if (id < 0) {
                throw new IllegalArgumentException(String.format("Must set valid ids for @%s", OnClick.class.getSimpleName()));
            }
        }
        List<? extends VariableElement> params = executableElement.getParameters();
        if (params.size() > 0) {
            throw new IllegalArgumentException(String.format("The method annotated with @%s must have no parameters", OnClick.class.getSimpleName()));
        }
    }

    public Name getMethodName() {
        return methodName;
    }

    public int[] getIds() {
        return ids;
    }
}
