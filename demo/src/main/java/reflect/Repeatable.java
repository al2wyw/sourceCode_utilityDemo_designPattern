package reflect;

import annotation.Role;
import annotation.Roles;

import java.lang.annotation.Annotation;

/**
 * Created with IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2017/4/1
 * Time: 11:16
 * Desc:
 */
//java编译器隐式的在该注解使用中加入@Roles -> @Roles(value = {@Role(name="test"),@Role(name="bbbb",level = 2)})
//Roles的type字段如果没有default，则无法编译
@Role(name="test")
@Role(name="bbbb",level = 2)
public class Repeatable {
    public static void main(String args[]) throws Exception{
        Class claz = Repeatable.class;
        Annotation[] annotations = claz.getAnnotations();
        for(Annotation an : annotations){
            System.out.println(an.annotationType());
        }

        System.out.println();
        annotations = claz.getAnnotationsByType(Role.class);
        for(Annotation an : annotations){
            System.out.println(an.annotationType());
        }

        annotations = claz.getAnnotationsByType(Roles.class);
        for(Annotation an : annotations){
            System.out.println(an.annotationType());
        }

        System.out.println();
        Annotation annotation = claz.getAnnotation(Roles.class);
        System.out.println("Annotation: " + annotation.annotationType());

        System.out.println();
        annotation = claz.getDeclaredAnnotation(Roles.class);
        System.out.println("DeclaredAnnotation: " + annotation.annotationType());

        //System.out.println();
        //annotation = claz.getAnnotation(Target.class);
        //System.out.println("Annotation: " + annotation.annotationType());//npe

        System.out.println();
        //annotation = claz.getAnnotation(Role.class);
        //System.out.println(annotation.annotationType());//npe

        if(claz.isAnnotationPresent(Role.class)){
            System.out.println("role present");
        }
        if(claz.isAnnotationPresent(Roles.class)){
            System.out.println("roles present");
        }
    }
}
