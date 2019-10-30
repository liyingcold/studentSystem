import com.ly.pojo.Role;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisListCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import javax.servlet.Filter;
import java.net.URL;
import java.util.*;

public class test {

    @Test
    public void get(){
//        找到冲突的jar包
        URL url= Filter.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println("path:"+url.getPath()+" name:"+url.getFile());
    }

//    简单示例
    @Test
    public  void testRedis() {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate=applicationContext.getBean(RedisTemplate.class);
        Role role=new Role();
        role.setId(1L);
        role.setRoleName("redis_role_name1");
        role.setNote("role_note_1");
//        无法保证来自于同一个redis连接池的同一个redis连接
//        redisTemplate.opsForValue().set("role_1",role);
//        Role role1= (Role) redisTemplate.opsForValue().get("role_1");
        SessionCallback callback=new SessionCallback<Role>() {
            @Override
            public Role execute(RedisOperations redisOperations) throws DataAccessException {
                redisOperations.boundValueOps("role_1").set(role);
                return (Role) redisOperations.boundValueOps("role_1").get();
            }
        };
        Role savedRole= (Role) redisTemplate.execute(callback);
        System.out.println(savedRole.getRoleName());
    }

//    Redis字符串操作
    @Test
    public  void testStrings(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate=applicationContext.getBean(RedisTemplate.class);
//        设值
        redisTemplate.opsForValue().set("key1","value1");
        redisTemplate.opsForValue().set("key2","value2");
//        通过key获取值
        String value1= (String) redisTemplate.opsForValue().get("key1");
//        通过key删除值
        redisTemplate.delete("key1");
//        求长度
        Long length=redisTemplate.opsForValue().size("key2");
        System.out.println(length);
//        设置新值并返回旧值
        String oldValue2= (String) redisTemplate.opsForValue().getAndSet("key2","new_value2");
        System.out.println(oldValue2);
//        通过key获取值
        String value2= (String) redisTemplate.opsForValue().get("key2");
        System.out.println(value2);
//        求子串
        String rangeValue2=redisTemplate.opsForValue().get("key2",0,3);
        System.out.println(rangeValue2);
//        追加字符串到末尾，返回新串长度
        int newLen=redisTemplate.opsForValue().append("key2","_app");
        System.out.println(newLen);
        String appendValue2= (String) redisTemplate.opsForValue().get("key2");
        System.out.println(appendValue2);


    }


//    Redis运算
    @Test
    public  void testCal(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate=applicationContext.getBean(RedisTemplate.class);
        redisTemplate.opsForValue().set("i","9");
        printCurrValue(redisTemplate,"i");
        redisTemplate.opsForValue().increment("i",1);
        printCurrValue(redisTemplate,"i");
        redisTemplate.getConnectionFactory().getConnection().decr(redisTemplate.getKeySerializer().serialize("i"));
        printCurrValue(redisTemplate,"i");
        redisTemplate.getConnectionFactory().getConnection().decrBy(redisTemplate.getKeySerializer().serialize("i"),6);
        printCurrValue(redisTemplate,"i");
        redisTemplate.opsForValue().increment("i",2.3);
        printCurrValue(redisTemplate,"i");

    }
//    打印当前key的值
    public static void printCurrValue(RedisTemplate redisTemplate,String key){
        String i= (String) redisTemplate.opsForValue().get(key);
        System.out.println(i);
    }

//    hash结构
    @Test
    public void testRedisHash(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate=applicationContext.getBean(RedisTemplate.class);
        String key="hash";
        Map<String,String> map=new HashMap<String, String>();
        map.put("f1","val1");
        map.put("f2","val2");
//        存入一个map
        redisTemplate.opsForHash().putAll(key,map);
//        存入一个键值对
        redisTemplate.opsForHash().put(key,"f3","6");
        printValueHash(redisTemplate,key,"f3");
//        键是否存在
        Boolean exists=redisTemplate.opsForHash().hasKey(key,"f3");
        System.out.println(exists);

        Map keyValueMap=redisTemplate.opsForHash().entries(key);
//        给键为f3的加上一个整数
        redisTemplate.opsForHash().increment(key,"f3",2);
        printValueHash(redisTemplate,key,"f3");

        redisTemplate.opsForHash().increment(key,"f3",0.88);
        printValueHash(redisTemplate,key,"f3");

        List valueList=redisTemplate.opsForHash().values(key);
        Set keyList=redisTemplate.opsForHash().keys(key);
        List<String> fieldList=new ArrayList<String>();
        fieldList.add("f1");
        fieldList.add("f2");

        List valueList2=redisTemplate.opsForHash().multiGet(key,keyList);
        boolean success=redisTemplate.opsForHash().putIfAbsent(key,"f4","val4");
        System.out.println(success);

        Long result=redisTemplate.opsForHash().delete(key,"f1","f2");
        System.out.println(result);
    }
    public static void printValueHash(RedisTemplate redisTemplate,String key,String field){
        String value= (String) redisTemplate.opsForHash().get(key,field);
        System.out.println(value);
    }

//    链表结构
    @Test
    public void testList(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("applicationContext.xml");
        RedisTemplate redisTemplate=applicationContext.getBean(RedisTemplate.class);
        try{
//            删除链表，方便反复测试
            redisTemplate.delete("list");
//            把node3插入链表 [node3]
            redisTemplate.opsForList().leftPush("list","node3");
            List<String> nodeList=new ArrayList<String>();
            for (int i=2;i>=1;i--){
                nodeList.add("node"+i);
            }
//            将多个值从左插入链表[node1,node2,node3]
            redisTemplate.opsForList().leftPushAll("list",nodeList);
//            从右边插入一个节点[node1,node2,node3,node4]
            redisTemplate.opsForList().rightPush("list","node4");
//            获取下标值为0的节点node1
            String node1= (String) redisTemplate.opsForList().index("list",0);
//            获取链表长度4
            long size=redisTemplate.opsForList().size("list");
//            从左边弹出一个节点[node2,node3,node4]
            String lpop= (String) redisTemplate.opsForList().leftPop("list");
//              从右边弹出一个节点[node2,node3]
            String rpop= (String) redisTemplate.opsForList().rightPop("list");
//            linsert命令需要更为底层的命令才能操作
//            使用linsert命令在node2前插入一个节点[before_node,node2,node3]
            redisTemplate.getConnectionFactory().getConnection().lInsert("list".getBytes("utf-8"), RedisListCommands.Position.BEFORE,"node2".getBytes("utf-8"),"before_node".getBytes("utf-8"));
//            使用linsert命令在node2后插入一个节点[before_node,node2,node3,after_node]
            redisTemplate.getConnectionFactory().getConnection().lInsert("list".getBytes("utf-8"),RedisListCommands.Position.AFTER,"node2".getBytes("utf-8"),"after_node".getBytes("utf-8"));
//            判断list是否存在，存在则从左边插入head节点[head,before_node,node2,node3,after_node]
            redisTemplate.opsForList().leftPushIfPresent("list","head");
//            判断list是否存在，存在从右边插入end节点[head,before_node,node2,node3,after_node,end]
            redisTemplate.opsForList().rightPushIfPresent("list","end");
//            从左到右，或者0-10的节点元素
            List valueList=redisTemplate.opsForList().range("list",0,10);
            nodeList.clear();
            for (int i=1;i<=3;i++){
                nodeList.add("node");
            }
//            在表的左边插入三个值为node的节点
            redisTemplate.opsForList().leftPushAll("list",nodeList);
//            从左到右删除至多三个node节点
            redisTemplate.opsForList().remove("list",3,"node");
//            给下标节点为0的节点设置新值[new_head_value,before_node,node2,node3,after_node,end]
            redisTemplate.opsForList().set("list",0,"new_head_value");

        }catch ( Exception e){
            e.printStackTrace();
        }
        printList(redisTemplate,"list");
    }

    public static void printList(RedisTemplate redisTemplate,String key){
//        链表长度
        Long size=redisTemplate.opsForList().size(key);
//        获取整个链表的值
        List valueList=redisTemplate.opsForList().range(key,0,size);
        System.out.println(valueList);
    }

}
