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
public class Repeatable {
    public static void main(String args[]) throws Exception{
        Class claz = father.class;
        Annotation[] annotations = claz.getAnnotations();
        for(Annotation an : annotations){
            System.out.println(an.annotationType());//interface annotation.Roles
        }

        System.out.println();
        annotations = claz.getAnnotationsByType(Role.class);
        for(Annotation an : annotations){
            System.out.println(an.annotationType());
            //interface annotation.Role
            //interface annotation.Role
        }
        System.out.println();
        annotations = claz.getAnnotationsByType(Roles.class);
        for(Annotation an : annotations){
            System.out.println(an.annotationType());
            //interface annotation.Roles
        }

        System.out.println();
        annotations = claz.getDeclaredAnnotationsByType(Role.class);
        for(Annotation an : annotations){
            System.out.println(an.annotationType());
            //interface annotation.Role
            //interface annotation.Role
        }
        System.out.println();
        annotations = claz.getDeclaredAnnotationsByType(Roles.class);
        for(Annotation an : annotations){
            System.out.println(an.annotationType());
            //interface annotation.Roles
        }

        System.out.println("get by type done!!!");
        Annotation annotation = claz.getAnnotation(Roles.class);
        System.out.println("Annotation: " + annotation.annotationType());
        //Annotation: interface annotation.Roles

        System.out.println();
        annotation = claz.getDeclaredAnnotation(Roles.class);
        System.out.println("DeclaredAnnotation: " + annotation.annotationType());
        //DeclaredAnnotation: interface annotation.Roles

        //System.out.println();
        //annotation = claz.getAnnotation(Target.class);
        //System.out.println("Annotation: " + annotation.annotationType());//npe

        System.out.println();
        //annotation = claz.getAnnotation(Role.class);
        //System.out.println(annotation.annotationType());//npe

        if(claz.isAnnotationPresent(Role.class)){
            System.out.println("role present");
            //not executed
        }
        if(claz.isAnnotationPresent(Roles.class)){
            System.out.println("roles present");
            //roles present
        }

        Class klas = son.class;
        System.out.println();
        annotations = klas.getAnnotationsByType(Role.class);
        for(Annotation an : annotations){
            System.out.println(an.annotationType());
            //interface annotation.Role
            //interface annotation.Role
        }
        System.out.println();
        annotations = klas.getAnnotationsByType(Roles.class);
        for(Annotation an : annotations){
            System.out.println(an.annotationType());
            //interface annotation.Roles
        }
    }

    @Role(name="test")
    @Role(name="bbbb",level = 2)
    class father{

    }

    class son extends father{

    }
}
