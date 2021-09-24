package com.xiexin.redistest;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static redis.clients.jedis.BinaryClient.LIST_POSITION.BEFORE;

/**
 * redis 的测试 测试和ssm项目结合
 * ssm如何使用redis 第一种方式 使用jedis,类似jdbc
 * 第一步：在applicationContext.xml中注释掉
 * 第二步：把db.properties中的把redis配置的注视去掉
 *
 * springmvc中的单元测试
 * 为什么要用juint单元测试，因为在框架中，传统的main方法以及无法
 * 处理，如req请求，等等，无法满足测试需求了
 * 单元测试的好处是，在最小的代码借工单元中找出bug,最快速的招数bug所在的地方
 * 迅速解决，1个dao方法一个测试 一个controller 1一个测试，一个service 1个 测试
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)//使用spring的junit测试
@ContextConfiguration({"classpath:applicationContext(1).xml"})//模拟ssm框架运行后加载xml容器
public class MyRedisTest {
    @Autowired
    private JedisPool jedisPool;
    //测试string类型
    @Test
    public void test01() throws InterruptedException {
       String pcode = jedisPool.getResource().set("pcode","4758");
        System.out.println("pcode = " + pcode);
        //查询pcode 这个key 在不在，如果在，把他设置成120到技术，且值也改为7788
        //并且在10s后输出所剩下的到技术！
        //查询pcode这个key在不在------exists key
        Boolean b =jedisPool.getResource().exists("pcode");
        System.out.println("b = " + b);
        if (b){
            System.out.println("key的值存在 b= " + b);
            //如果在，把他设置成120秒的倒计时
            jedisPool.getResource().setex("pcode",120,"7788");
                    //并且在10s后输出所剩下的倒计时！
            Thread.sleep(1000);
            Long ttl = jedisPool.getResource().ttl("pcode");
            System.out.println("ttl = " + ttl);
            //输出完毕后，将该key设置成永久的key
            jedisPool.getResource().persist("pcode"); // 注意， 他的返回值不是-1
            Long ttl2 = jedisPool.getResource().ttl("pcode");
            System.out.println("ttl2 = " + ttl2);
            }else {
            System.out.println("b = " + b + ",key不存在");
        }
    }

    // 测试 常用命令
    @Test
    public void test02(){
        //查询所有的key
        Set<String> keys = jedisPool.getResource().keys("*");
        for (String key : keys){
            //System.out.println("key = " + key);
            String value = jedisPool.getResource().get(key);
            System.out.println("key = " + key +":"+ "value = " + value);
            //自增
            Long incr = jedisPool.getResource().incr(key);
            System.out.println("incr = " + incr);
            String value1 = jedisPool.getResource().get(key);
            System.out.println("key = " + key +":"+ "value = " + value1);
        }

    }
    //测试hash
    @Test
    public void test03(){
        jedisPool.getResource().hset("food","name","苹果");
        jedisPool.getResource().hset("food","color","红色");
        //查
        String color= jedisPool.getResource().hget("food","color");
        System.out.println("color = " + color);
        //查k
        Set<String> food = jedisPool.getResource().hkeys("food");
        for (String key : food){
            System.out.println("key = " + key);
        }
        //查kv
        Map<String, String> food1 = jedisPool.getResource().hgetAll("food");
        for (String s : food1.keySet()){
            System.out.println("s = " + s);
        }
    }
    // 测试list
    @Test
    public void test04(){
        jedisPool.getResource().lpush("names", "唐僧", "孙悟空");
        List<String> names = jedisPool.getResource().lrange("names", 0, -1);
        for (String name : names) {
            System.out.println("names = " + names);
        }

        String names1 = jedisPool.getResource().lpop("names");
        System.out.println("names1 = " + names1);

        List<String> names2 = jedisPool.getResource().lrange("names", 0, -1);
        for (String s : names2) {
            System.out.println("s = " + s);
        }

    }

    // 测试 set
    @Test
    public void test05(){
        jedisPool.getResource().sadd("pnames","张三","李四");
        Set<String> pnames = jedisPool.getResource().smembers("pnames");
        for (String pname : pnames) {
            System.out.println("pname = " + pname);
        }
        Long pnames1 = jedisPool.getResource().scard("pnames");
        System.out.println("pnames1 = " + pnames1);

        //指定删除： srem key value
        jedisPool.getResource().srem("pnames","张三");
        //随机的删除！   spop names
        jedisPool.getResource().spop("pnames");
    }

    //测试 zest
    @Test
    public void test06(){
//        增加： zadd key 分数  值  pnames
        jedisPool.getResource().zadd("xnames",1.0,"大娃");
        jedisPool.getResource().zadd("xnames",2.0,"二娃");
        jedisPool.getResource().zadd("xnames",3.0,"三娃");
        jedisPool.getResource().zadd("xnames",4.0,"四娃");
//        遍历： zrange key 0 -1 withscores  加上 withscores  带分数（下标）， 不带直接全部显示值
        Set<String> xnames = jedisPool.getResource().zrange("xnames", 0, -1);
            for (String xname : xnames) {
                System.out.println("xnames = " + xnames);
        }

//        查条数： zcard key
        Long xnames1 = jedisPool.getResource().zcard("xnames");
        System.out.println("xnames1 = " + xnames1);

//        指定的删除： 移除集合中的一个或者多个成员 zrem key value
        Long zrem = jedisPool.getResource().zrem("xnames", "三娃");
        System.out.println("zrem = " + zrem);
    }
    @Test
    public void test07(){
        jedisPool.getResource().sadd("pname","白世纪", "陈红利" , "陈世纪" , "陈洋洋" , "杜晓梦" , "付春辉" , "高芳芳" , "郭旭" , "胡艺果" , "贾礼博" , "李雪莹" , "李祎豪" , "林梦娇" , "刘顺顺" , "卢光辉" ,
                "吕亚伟" , "宁静静" , "牛志洋" , "史倩影" , "宋健行" , "孙超阳" , "孙乾力" , "田君垚" , "汪高洋" , "王学斌" , "杨天枫" , "杨原辉" , "袁仕奇" , "张浩宇" , "张晓宇" , "张志鹏" , "赵博苛" , "邹开源");
        Set<String> pnames = jedisPool.getResource().smembers("pname");
        for (String pname : pnames) {
            System.out.println("pname = " + pname);
        }
        Long pname1 = jedisPool.getResource().scard("pname");
        System.out.println("pname1 = " + pname1);
        String pname2 = jedisPool.getResource().srandmember("pname");
        System.out.println("pname2= " + pname2);

        Long pname3 = jedisPool.getResource().srem("pname",pname2);
        System.out.println("pname3 = " + pname3);
    }
    @Test
    public void test08(){
        jedisPool.getResource().lpush("tt","盲僧","瑞文","亚索","剑圣","剑姬","万豪","莉莉娅","努努","女枪","石头人");
        List<String> tt = jedisPool.getResource().lrange("tt", 0, -1);
        for (String s : tt) {
            System.out.println("s = " + s);
        }
        System.out.println("--------------------------");
        String tt1 = jedisPool.getResource().lindex("tt", 4);
        System.out.println("tt1 = " + tt1);
        jedisPool.getResource().lpop("tt");
        jedisPool.getResource().rpop("tt");
        List<String> tt2 = jedisPool.getResource().lrange("tt", 0, -1);
        for (String s : tt2) {
            System.out.println("s = " + s);
        }
        jedisPool.getResource().linsert("tt",BEFORE,"莉莉娅","豹女");
        List<String> tt3 = jedisPool.getResource().lrange("tt", 0, -1);
        for (String s : tt3) {
            System.out.println("s = " + s);

        }
    }
}
