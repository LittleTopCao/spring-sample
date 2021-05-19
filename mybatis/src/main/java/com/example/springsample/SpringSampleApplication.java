package com.example.springsample;

import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 不需要做任何配置，直接就可以 注入 mapper，自动扫描带有 @Mapper 的类
 *
 * 自动生成 SqlSessionTemplate  bean ，可以直接 注入 使用
 */
@SpringBootApplication
class SampleMybatisApplication implements CommandLineRunner {

    // 在构造方法中直接注入
    private final CityMapper cityMapper;

    // 在构造方法中直接注入
    private SqlSession sqlSession;


    /**
     * 构造方法
     */
    public SampleMybatisApplication(CityMapper cityMapper, SqlSession sqlSession) {
        this.cityMapper = cityMapper;
        this.sqlSession = sqlSession;
    }

    public static void main(String[] args) {
        SpringApplication.run(SampleMybatisApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.cityMapper.findByState("CA"));
    }

}