package com.test.controller;


import com.test.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 普通 控制器
 *
 * @Controller 注解就是特殊的 @Component，其实就是声明一个 bean, 替换为 @Component 完全正常
 *
 * @RequestMapping 注解 类 指定整个控制器的 url 前缀
 *
 * @RequestMapping 注解 方法 ，指定方法处理请求
 *      可以指定请求路径
 *      请求方法
 *      请求参数
 *      请求头
 *      请求包含的数据类型
 *      请求的数据类型
 *
 * @GetMapping 代替 @RequestMapping(method = RequestMethod.GET)
 *
 *
 * 接收请求参数
 *      query参数
 *      路径参数
 *      post参数
 *          x-www-form-urlencoded
 *          form-data
 *          application/json
 *          text/xml
 *
 * 解析 Header
 *
 *
 */
@Controller
@RequestMapping("ControllerSample")
public class ControllerSample {

    /**
     * @RequestMapping 指定方法处理请求
     *
     * 返回的字符串 作为 逻辑视图名
     *
     * model 用来为视图传递数据
     */
    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
        model.addAttribute("abc", "acb");
        return "home";
    }


    /**
     * 查询参数
     *
     * 直接使用同名属性就可以, 如果没有 传 请求参数， 那么 就是 null
     *
     * 也可以使用 @RequestParam 注解
     *      可以改名
     *      可以指定是否必须有
     *      可以指定默认值
     */
    @GetMapping("m1")
    @ResponseBody
    public String m1(String a, String b, @RequestParam(value = "c", required = false, defaultValue = "333") String cc) {

        return a + b + cc;
    }


    /**
     * 路径参数
     *
     * 需要 使用 @PathVariable 指定
     *      可以改名
     *      可以指定是否必须（好像不管用啊）
     *
     *
     */
    @GetMapping("m2/{b}")
    @ResponseBody
    public String m2(@PathVariable() String b) {

        return b;
    }


    /**
     * x-www-form-urlencoded
     *
     * 不需要注释，于 请求参数 同名 即可
     *
     * 也可以使用 @RequestParam 注解
     *      可以改名
     *      可以指定是否必须有
     *      可以指定默认值
     */
    @PostMapping("m3")
    @ResponseBody
    public String m3(String a, String b, @RequestParam("c") String cc) {

        return a + b + cc;
    }


    /**
     * form-data
     *
     * 不需要注释，于 请求参数 同名 即可
     *
     * 也可以使用 @RequestParam 注解
     *      可以改名
     *      可以指定是否必须有
     *      可以指定默认值
     */
    @PostMapping("m4")
    @ResponseBody
    public String m4(String a, String b, @RequestParam("c") String cc) {

        return a + b + cc;
    }

    /**
     * application/json
     *
     * 需要用 @RequestBody 注解 接收 的参数
     *
     * 其中 query参数 还可正常工作
     *
     */
    @PostMapping(value = "m5")
    @ResponseBody
    public String m5(@RequestBody User user, String b) {

        return user.toString() + b;
    }


    /**
     * 把参数封装成对象
     *
     * 最终逻辑是这样的:
     *      不管是 query参数、form-data、x-www-form-urlencoded 都是混用的
     *      遇到对象自动设置到 同名请求参数 同名的 属性
     *      并且还可以和 直接参数 混用
     *
     */
    @PostMapping("m6/{id}")
    @ResponseBody
    public String m6(User user, String b, @PathVariable("id") String id) {

        return user.toString() + b + id;
    }


    /**
     * 混用
     *      query参数
     *      路径参数
     *      post选一种
     *
     */
    @PostMapping("m7/{id}")
    @ResponseBody
    public String m7(@RequestBody User user, String b, @PathVariable("id") String id) {

        return user.toString() + b + id;
    }


}
