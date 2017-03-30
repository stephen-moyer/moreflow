package com.moreflow.autoviewcontroller.compiler;

import com.moreflow.autoviewcontroller.ViewController;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.moreflow.autoviewcontroller.ViewController")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class Processor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("PROCESSING");
        StringBuilder builder = new StringBuilder()
                .append("package com.moreflow.generated;\n\n")
                .append("import com.moreflow.core.resolver.IControllerViewResolver;\n")
                .append("import com.moreflow.core.view.IView;\n")
                .append("import com.moreflow.core.controller.Controller;\n\n")
                .append("public class ViewControllerResolver_Generated implements IControllerViewResolver {\n\n") // open class
                .append("\tpublic Class<? extends IView> viewForController(Class<? extends Controller> controllerClass) {\n"); // open method

        generateBody(builder, roundEnvironment.getElementsAnnotatedWith(ViewController.class));

        builder.append("\t\treturn null;\n") // end return
                .append("\t}\n") // close method
                .append("}\n"); // close class

        try { // write the file
            JavaFileObject source = processingEnv.getFiler().createSourceFile("com.moreflow.generated.ViewControllerResolver_Generated");
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }
        return true;
    }

    private void generateBody(StringBuilder builder, Set<? extends Element> set) {
        boolean first = true;
        try {
            for (Element el : set) {
                ViewController controllerClass = el.getAnnotation(ViewController.class);
                System.out.println(el);
                System.out.println(controllerClass);
                String controllerClassName = getClassReference(getViewControllerClassTypeMirror(controllerClass));
                if (!first) {
                    builder.append(" else ");
                } else {
                    builder.append("\t\t");
                }
                builder.append("if (controllerClass == ").append(controllerClassName).append(") {\n")
                        .append("\t\t\treturn ").append(getClassReference(el.asType())).append(";\n")
                        .append("\t\t}");
                first = false;
            }
            builder.append("\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getClassReference(TypeMirror typeMirror) {
        return typeMirror.toString() + ".class";
    }

    private static TypeMirror getViewControllerClassTypeMirror(ViewController annotation) {
        try
        {
            annotation.controller(); // this should throw
        }
        catch( MirroredTypeException mte )
        {
            return mte.getTypeMirror();
        }
        return null; // can this ever happen ??
    }
}
