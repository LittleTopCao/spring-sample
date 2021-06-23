package core;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.validation.Errors;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * 3. Validation, Data Binding, and Type Conversion
 *      1. Validation by Using Spring’s Validator Interface
 *      2. Resolving Codes to Error Messages
 *      3. Bean Manipulation and the BeanWrapper
 *      4. Spring Type Conversion
 *      5. Spring Field Formatting
 *      6. Configuring a Global Date and Time Format
 *      7. Java Bean Validation
 *
 * @Author ext.caojinsong
 * @Date 6/23/2021
 */
public class ValidationAndDataBindingAndTypeConversionTest {

    /**
     * 1. Validation by Using Spring’s Validator Interface
     *
     *  Spring 的校验接口
     *
     *  主要代码在 org.springframework.validation 包中
     *
     */
    @Data
    static class Person {
        private String name;
        private Integer age;
    }

    /*
     * Spring 自己提供的验证接口
     */
    static class PersonValidator implements Validator {

        /*
         * 判断类型， 只验证 Person 的实例
         */
        public boolean supports(Class clazz) {
            return Person.class.equals(clazz);
        }

        /*
         * 验证对象的方法，把 错误存储到 Errors 参数中
         * <p>
         * ValidationUtils 为 Spring 提供的验证工具
         */
        public void validate(Object obj, Errors e) {

            ValidationUtils.rejectIfEmpty(e, "name", "name.empty");

            Person p = (Person) obj;

            if (p.getAge() < 0) {
                e.rejectValue("age", "negativevalue");
            } else if (p.getAge() > 110) {
                e.rejectValue("age", "too.darn.old");
            }
        }
    }

    /**
     * 2. Resolving Codes to Error Messages
     *
     *  解析 Errors 中的错误
     *
     *  MessageCodesResolver 接口
     *
     *  默认实现为 DefaultMessageCodesResolver
     *
     */
    @Test
    public void test01() {

    }

    /**
     * 3. Bean Manipulation and the BeanWrapper
     *  bean 操作 和 BeanWrapper
     *
     *  javaee 有自己的 javabean 规范 ： https://docs.oracle.com/javase/8/docs/api/java/beans/package-summary.html
     *
     *  主要代码在 org.springframework.beans 包中
     */

    /*  BeanWrapper 接口负责包装 bean ，然后进行 便捷的操作
     *      设置和获取属性
     *      获取属性的描述符
     *      支持嵌套属性操作
     *      支持标准JavaBean的 PropertyChangeListeners 和 VetoableChangeListeners
     *
     *  实现类 BeanWrapperImpl
     *
     *  主要就是通过 name 操作 属性
     *      setPropertyValue
     *      getPropertyValue
     *
     * name的格式
     *      1. name 普通属性
     *      2. name.name 嵌套属性
     *      3. name[1] list属性
     *      4. name[name] map属性
     *
     */
    @Test
    public void test02() {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(new Person());

        //设置属性
        beanWrapper.setPropertyValue("name", "Some Company Inc.");

        //另一种设置属性的方式
        PropertyValue value = new PropertyValue("name", "Some Company Inc.");
        beanWrapper.setPropertyValue(value);

        //获取属性
        Integer age = (Integer) beanWrapper.getPropertyValue("age");
    }

    /*
     * 内置的 PropertyEditor 实现
     *
     * PropertyEditor 是 javaee javabean 规范中的 一个接口，用来实现 字符串 与 属性的转换，例如 Date 类型 转换为 2021-06-23 16:54:55
     *
     * 使用时机：
     *      在 xml 中给 bean 设置属性，如果属性是一个 复杂对象，将会调用 ClassEditor 去转换
     *      在 spring mvc 中 ，当 从 请求中 解析 参数时
     *
     * 列表，它们都在 org.springframework.beans.propertyeditors 包中
     *      ByteArrayPropertyEditor
     *      ClassEditor
     *      CustomBooleanEditor
     *      CustomCollectionEditor
     *      CustomDateEditor
     *      CustomNumberEditor
     *      FileEditor
     *      InputStreamEditor
     *      LocaleEditor
     *      PatternEditor
     *      PropertiesEditor
     *      StringTrimmerEditor
     *      URLEditor
     *
     * Spring 使用 java.beans.PropertyEditorManager 来管理 PropertyEditor
     *      搜索的路径包括 sun.bean.editors（有 jdk 自带的 Font, Color 和 原始类型 的 PropertyEditor ）
     *
     * 还支持 bean 目录下 PropertyEditor 的自动发现
     *      1. Something 和 SomethingEditor 在同一个目录
     *      2. Something 和 SomethingBeanInfo 在同一个目录
     *
     */

    /*
     * 注册自定义的  PropertyEditor
     *
     * 1. ConfigurableBeanFactory.registerCustomEditor() 方法
     * 2. CustomEditorConfigurer
     * 3. PropertyEditorRegistrar
     */

    /**
     * 4. Spring Type Conversion
     *
     *  Spring 类型转换
     *
     *  Spring 3 引入了一个core.convert 提供通用类型转换系统的包。
     *
     *  系统定义了一个 SPI 来实现类型转换逻辑和一个 API 来在运行时执行类型转换。
     *
     *  可以作为 PropertyEditor 的替代方案
     *
     *  并且支持在任何位置使用 api 进行转换
     *
     *
     *
     */

    /*
     * 使用 Converter 接口表示 类型转换器
     *      public interface Converter<S, T> {
     *
     *          T convert(S source);
     *      }
     *
     * core.convert.support 包中提供了几个常见类型转换器实现。
     *
     */

    /*
     * 使用 ConverterFactory 代表 类型转换器工厂
     *
     * 它可以实现 整个类层次结构的转换，例如 枚举 到 String
     *
     * 父类 到 某个类型
     */

    /*
     * GenericConverter 代表一个更复杂的 类型转换器
     *
     * 它先通过方法定义 可以转换的 类型
     *
     */

    /*
     * ConditionalGenericConverter 定义 在某些条件下 才支持的类型转换器
     *
     * 例如 只有类上有某个 注解时 才可以
     */

    /*
     * ConversionService 代表使用转换器的 外部接口
     *
     * 通过 ConversionService 来在代码中使用 转换器
     *
     * 提供了 实现 GenericConversionService
     *
     * 通常我们在 ConversionService 中 包含  ConverterRegistry 用来注册转换器
     *
     * 注册 conversionService bean， 同时 注册 Converter
     *
<bean id="conversionService"
        class="org.springframework.context.support.ConversionServiceFactoryBean">
    <property name="converters">
        <set>
            <bean class="example.MyCustomConverter"/>
        </set>
    </property>
</bean>
     *
     * 我们也可以将 conversionService 注入到 任何 bean 中 ，然后 直接调用 方法
     */

    /*
     * Spring 在没有 ConversionService 注册时（bean） 使用 PropertyEditor 系统 来转换
     *
     */

    /*
     * 我们在代码中使用 conversionService 时，如果碰到 list 等 范型 类型，需要配合 TypeDescriptor 来明确指出类型
     *
     * DefaultConversionService自动注册适用于大多数环境的转换器。
     *       This includes collection converters, scalar converters, and basic Object-to-String converters.
     *
     */
    @Test
    public void test03() {
        DefaultConversionService cs = new DefaultConversionService();

        List<Integer> input = new ArrayList<>(3);
        cs.convert(input,
                TypeDescriptor.forObject(input), // List<Integer> type descriptor
                TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(String.class)));
    }


}


