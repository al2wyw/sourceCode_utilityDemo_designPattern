package com.beancopy;

import com.google.common.base.Preconditions;
import com.utils.LoggerUtils;
import net.sf.cglib.core.*;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Type;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: johnny.ly
 * Date: 2019/8/23
 * Time: 13:42
 * Desc:
 */
public abstract class BeanCopierExt<S,T> {

    private static final BeanCopierExt.BeanCopierKey KEY_FACTORY =
            (BeanCopierExt.BeanCopierKey)KeyFactory.create(BeanCopierExt.BeanCopierKey.class);
    private static final Type BEAN_COPIER =
            TypeUtils.parseType("com.beancopy.BeanCopierExt");
    private static final Signature COPY =
            new Signature("copy", Type.VOID_TYPE, new Type[]{ Constants.TYPE_OBJECT, Constants.TYPE_OBJECT});
    private static final Signature convertColl =
            new Signature("convertColl", Type.getType(Collection.class), new Type[]{ Type.getType(Collection.class), Type.getType(Class.class)});
    private static final Signature convertMap =
            new Signature("convertMap", Type.getType(Map.class), new Type[]{ Type.getType(Map.class), Type.getType(Class.class)});

    interface BeanCopierKey {
        Object newInstance(String source, String target);
    }

    private CopyCallback<S,T> copyCallback = new CopyCallback<S,T>() {
        @Override
        public void OnSuccess(S s, T t) {

        }

        @Override
        public void onFailure(S s, T t, Exception e) {
            LoggerUtils.getLogger().error("", e);
        }
    };

    public void transform(S from, T to) {
        try{
            Preconditions.checkNotNull(from,"from Object is null");
            Preconditions.checkNotNull(to,"to Object is null");
            copy(from, to);
            copyCallback.OnSuccess(from, to);
        }catch (Exception e){
            copyCallback.onFailure(from, to, e);
        }
    }

    protected abstract void copy(Object from, Object to);


    public static Collection convertColl(Collection source, Class targetKlass) throws Exception{
        Collection collection = source.getClass().newInstance();
        for(Object s : source) {
            BeanCopierExt beanCopierExt = BeanCopierExt.create(s.getClass(),targetKlass);
            Object target = targetKlass.newInstance();
            beanCopierExt.copy(s,target);
            collection.add(target);
        }
        return collection;
    }

    public static Map convertMap(Map source, Class targetKlass) throws Exception{
        Map map = source.getClass().newInstance();
        for(Object key : source.keySet()) {
            Object s = source.get(key);
            BeanCopierExt beanCopierExt = BeanCopierExt.create(s.getClass(),targetKlass);
            Object target = targetKlass.newInstance();
            beanCopierExt.copy(s,target);
            map.put(key, target);
        }
        return map;
    }

    public static <S,T> BeanCopierExt<S,T> create(Class<S> source, Class<T> target){
        BeanCopierExt.Generator gen = new BeanCopierExt.Generator();
        gen.setSource(source);
        gen.setTarget(target);
        return gen.create();
    }

    public static <S,T> BeanCopierExt<S,T> create(Class<S> source, Class<T> target, CopyCallback<S,T> copyCallback, Map<String,String> propertyNameMapping){
        BeanCopierExt.Generator gen = new BeanCopierExt.Generator();
        gen.setSource(source);
        gen.setTarget(target);
        gen.setPropertyNameMapping(propertyNameMapping);
        BeanCopierExt ext =  gen.create();
        ext.copyCallback = copyCallback;
        return ext;
    }

    public static class Generator extends AbstractClassGenerator {
        private static final Source SOURCE = new Source(BeanCopierExt.class.getName());
        private Class source;
        private Class target;
        private Map<String,String> propertyNameMapping = new HashMap<>();

        public Generator() {
            super(SOURCE);
        }

        public void setSource(Class source) {
            if(!Modifier.isPublic(source.getModifiers())){
                setNamePrefix(source.getName());
            }
            this.source = source;
        }

        public void setTarget(Class target) {
            if(!Modifier.isPublic(target.getModifiers())){
                setNamePrefix(target.getName());
            }

            this.target = target;
        }

        public void setPropertyNameMapping(Map<String, String> propertyNameMapping) {
            this.propertyNameMapping = propertyNameMapping;
        }

        protected ClassLoader getDefaultClassLoader() {
            return source.getClassLoader();
        }

        protected ProtectionDomain getProtectionDomain() {
            return ReflectUtils.getProtectionDomain(source);
        }

        public BeanCopierExt create() {
            Object key = KEY_FACTORY.newInstance(source.getName(), target.getName());
            return (BeanCopierExt)super.create(key);
        }

        public void generateClass(ClassVisitor v) {
            Type sourceType = Type.getType(source);
            Type targetType = Type.getType(target);
            ClassEmitter ce = new ClassEmitter(v);
            ce.begin_class(Constants.V1_2,
                    Constants.ACC_PUBLIC,
                    getClassName(),
                    BEAN_COPIER,
                    null,
                    Constants.SOURCE_FILE);

            EmitUtils.null_constructor(ce);
            CodeEmitter e = ce.begin_method(Constants.ACC_PUBLIC, COPY, null);
            PropertyDescriptor[] getters = ReflectUtils.getBeanGetters(source);
            PropertyDescriptor[] setters = ReflectUtils.getBeanSetters(target);

            Map<String,PropertyDescriptor> names = new HashMap<>();
            for (int i = 0; i < getters.length; i++) {
                names.put(getters[i].getName(), getters[i]);
            }

            e.load_arg(1);
            e.checkcast(targetType);
            e.load_arg(0);
            e.checkcast(sourceType);

            for (int i = 0; i < setters.length; i++) {
                PropertyDescriptor setter = setters[i];
                PropertyDescriptor getter = names.get(
                        propertyNameMapping.containsKey(setter.getName()) ?
                        propertyNameMapping.get(setter.getName()) :
                        setter.getName()
                );

                if (getter != null) {
                    MethodInfo read = ReflectUtils.getMethodInfo(getter.getReadMethod());
                    MethodInfo write = ReflectUtils.getMethodInfo(setter.getWriteMethod());

                    if(Collection.class.isAssignableFrom(setter.getPropertyType()) && compatible(getter, setter)){
                        e.dup2();
                        e.invoke(read);
                        try {
                            String name = setter.getName();
                            java.lang.reflect.Type  genType = target.getDeclaredField(name).getGenericType();
                            ParameterizedType pamType = (ParameterizedType) genType;
                            Class type = (Class)(pamType.getActualTypeArguments()[0]);
                            EmitUtils.load_class(e, Type.getType(type));
                            e.invoke_static(Type.getType(BeanCopierExt.class), convertColl);
                        }catch (NoSuchFieldException ex){
                            LoggerUtils.getLogger().error("",e);
                        }
                        e.invoke(write);
                    }else if(Map.class.isAssignableFrom(setter.getPropertyType()) && compatible(getter, setter)){
                        e.dup2();
                        e.invoke(read);
                        try {
                            String name = setter.getName();
                            java.lang.reflect.Type  genType = target.getDeclaredField(name).getGenericType();
                            ParameterizedType pamType = (ParameterizedType) genType;
                            Class type = (Class)(pamType.getActualTypeArguments()[1]);
                            EmitUtils.load_class(e, Type.getType(type));
                            e.invoke_static(Type.getType(BeanCopierExt.class), convertMap);
                        }catch (NoSuchFieldException ex){
                            LoggerUtils.getLogger().error("",e);
                        }
                        e.invoke(write);
                    } else if (compatible(getter, setter)) {
                        e.dup2();
                        e.invoke(read);
                        e.invoke(write);
                    }
                }
            }
            e.return_value();
            e.end_method();
            ce.end_class();
        }

        private static boolean compatible(PropertyDescriptor getter, PropertyDescriptor setter) {
            return setter.getPropertyType().isAssignableFrom(getter.getPropertyType());
        }

        protected Object firstInstance(Class type) {
            return ReflectUtils.newInstance(type);
        }

        protected Object nextInstance(Object instance) {
            return instance;
        }
    }
}


