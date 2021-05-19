[项目地址](https://github.com/mybatis/spring-boot-starter)

这是为了和 Spring Boot 集成的一个项目

提供功能：
    1. 自动检测现有 datasource
    2. 自动创建 SqlSessionFactory
    3. 自动创建 SqlSessionTemplate（为了接入Spring，线程安全的，对应于 mybatis 原生的 SqlSession）
    4. 自动扫描 Mapper 类， 将它们注册到 Spring 上下文， 并连接到 SqlSessionTemplate

自动扫描@Mapper 生成 bean，可用于注入
自动生成SqlSessionTemplate bean，可用于注入

支持在 application.properties 中配置，所有配置以 mybatis 开头

支持以 mybatis 的 ConfigurationCustomizer 配置方式，只需要声明 bean

SpringBootVFS 是个什么东西呢？

自动应用 一下 mybatis 组件，只需要声明为 bean
    Interceptor
    TypeHandler
    LanguageDriver (Requires to use together with mybatis-spring 2.0.2+)
    DatabaseIdProvider

把前端的模板技术 应用到 生成 xml 中去？
    ThymeleafLanguageDriverConfig
    FreeMarkerLanguageDriverConfig
    VelocityLanguageDriverConfig 