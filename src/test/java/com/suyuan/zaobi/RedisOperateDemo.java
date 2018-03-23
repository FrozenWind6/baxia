package com.suyuan.zaobi;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * redis常用命令扫盲
 */
public class RedisOperateDemo {

  private static Jedis jedis;

  private static final String TEST_KEY1 = "testkey1";

  private static final String TEST_KEY2 = "testkey2";

  private static final String TEST_KEY3 = "testkey3";

  static {
    jedis = new Jedis("127.0.0.1", 6379);
  }


  /**
   * Redis Zadd 命令用于将一个或多个成员元素及其分数值加入到有序集当中。
   * 如果某个成员已经是有序集的成员，那么更新这个成员的分数值，并通过重新插入这个成员元素，来保证该成员在正确的位置上。
   * 分数值可以是整数值或双精度浮点数。
   * 如果有序集合 key 不存在，则创建一个空的有序集并执行 ZADD 操作。
   * 当 key 存在但不是有序集类型时，返回一个错误。
   * 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员。
   */
  @Test
  public void zaddOper() {
    Map<String, Double> map = Maps.newHashMap();
    map.put("a", 1.0);
    map.put("b", 1.0);
    map.put("c", 1.0);
    map.put("d", 1.0);
    map.put("e", 1.0);
    map.put("f", 1.0);

    Long result = jedis.zadd(TEST_KEY1, map);

    System.out.println(result);
  }

  /**
   * Redis Zrange 返回有序集中，指定区间内的成员。
   * 其中成员的位置按分数值递增(从小到大)来排序。
   * 具有相同分数值的成员按字典序(lexicographical order )来排列。
   * 如果你需要成员按值递减(从大到小)来排列，请使用 ZREVRANGE 命令。
   * 标参数 start 和 stop 都以 0 为底，也就是说，以 0 表示有序集第一个成员，以 1 表示有序集第二个成员，以此类推。
   * 你也可以使用负数下标，以 -1 表示最后一个成员， -2 表示倒数第二个成员，以此类推。
   */
  @Test
  public void zrangeOper() {
    Set<String> set = jedis.zrange(TEST_KEY1, 0, 5);
    for (String str : set) {
      System.out.println(str);
    }
  }


  /**
   * Redis Hset 命令用于为哈希表中的字段赋值 。
   * 如果哈希表不存在，一个新的哈希表被创建并进行 HSET 操作。
   * 如果字段已经存在于哈希表中，旧值将被覆盖。
   * 如果字段是哈希表中的一个新建字段，并且值设置成功，返回 1 。 如果哈希表中域字段已经存在且旧值已被新值覆盖，返回 0 。
   */
  @Test
  public void hsetOper() {
    Long r1 = jedis.hset(TEST_KEY2, "name", "suyuan");
    Long r2 = jedis.hset(TEST_KEY2, "tele", "13733332222");
    Long r3 = jedis.hset(TEST_KEY2, "add", "beijing");
    System.out.println(r1 + "-" + r2 + "-" + r3);
  }

  /**
   * Redis Hget 命令用于返回哈希表中指定字段的值
   * 返回给定字段的值。如果给定的字段或 key 不存在时，返回 nil 。
   */
  @Test
  public void hgetOper() {
    String result = jedis.hget(TEST_KEY2, "name");
    System.out.println(result);
  }

  /**
   * Redis Hgetall 命令用于返回哈希表中，所有的字段和值。
   * 在返回值里，紧跟每个字段名(field name)之后是字段的值(value)，所以返回值的长度是哈希表大小的两倍。
   * 以列表形式返回哈希表的字段及字段值。 若 key 不存在，返回空列表。
   */
  @Test
  public void hgetAllOper() {
    Map<String, String> result = jedis.hgetAll(TEST_KEY2);
    for (String str : result.keySet()) {
      System.out.println(result.get(str));
    }
  }

  /**
   * Redis Hmset 命令用于同时将多个 field-value (字段-值)对设置到哈希表中。
   * 此命令会覆盖哈希表中已存在的字段。
   * 如果哈希表不存在，会创建一个空哈希表，并执行 HMSET 操作。
   * 如果命令执行成功，返回 OK 。
   */
  @Test
  public void hmsetOper() {
    Map<String, String> map = Maps.newHashMap();
    map.put("name", "suyuan");
    map.put("id", "2057");
    map.put("age", "23");
    String result = jedis.hmset(TEST_KEY3, map);

    System.out.println(result);
  }

  /**
   * Redis Hmget 命令用于返回哈希表中，一个或多个给定字段的值。
   * 如果指定的字段不存在于哈希表，那么返回一个 nil 值。
   * 一个包含多个给定字段关联值的表，表值的排列顺序和指定字段的请求顺序一样。
   */
  @Test
  public void hmgetOper() {
    List<String> result = jedis.hmget(TEST_KEY3, "age", "name");
    for (String str : result) {
      System.out.println(str);
    }
  }

  /**
   * Redis EXISTS 命令用于检查给定 key 是否存在。
   * 若 key 存在返回 1 ，否则返回 0 。
   */
  @Test
  public void existsOper() {
    System.out.println(jedis.exists(TEST_KEY3));
  }

}
