package com.example.demo.utils;


 
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.coreos.jetcd.Client;
import com.coreos.jetcd.KV;
import com.coreos.jetcd.data.ByteSequence;
import com.coreos.jetcd.data.KeyValue;
import com.coreos.jetcd.kv.GetResponse;
import com.coreos.jetcd.kv.PutResponse;
import com.coreos.jetcd.lease.LeaseGrantResponse;
import com.coreos.jetcd.lock.LockResponse;
import com.coreos.jetcd.lock.UnlockResponse;
import com.coreos.jetcd.options.DeleteOption;
import com.coreos.jetcd.options.GetOption;
import com.coreos.jetcd.options.PutOption;


 
/**
 * etcd工具類
 * @author harmonyCloud
 *
 */
public class Etcd4j {
	
	
	public static void main(String[] args) {
		String value = getEtcdKey("test/1");
	}
	
    private static Client client = null;
 
    
    public static void closeClient() {
    	if(client != null) {
    		client.close();
    	}
    	client = null;
    }
 
    /**
     * init EtcdClient 初始化Etcd客户端
     * @return 
     */
    public static synchronized Client getClient() {
        if (null == client) {
            //read current ip;
            //String machineIp = getMachineIp();
            //create client;
            //client = Client.builder().endpoints("http://" + machineIp + port).build();
            client = Client.builder().endpoints("http://192.168.85.129:2379").build();
            //Client connect = Client.builder().endpoints(EtcdHosts).authority("root:password").build();
        }
        return client;
    }
    
    
    /**
     * get single etcdKey from etcd; 从Etcd获取单个key
     *
     * @param key etcdKey
     * @return    etcdKey and value 's instance
     */
    public static String getEtcdKey(String key) {
        try {
        	Client client = getClient();
        	KV kvClient = client.getKVClient();
        	ByteSequence keyByte = ByteSequence.fromString(key);
        	CompletableFuture<GetResponse> getResponse = kvClient.get(keyByte);
        	GetResponse response = getResponse.get();
        	List<KeyValue> kvs = response.getKvs();
            if(kvs.size()>0){
                String value = kvs.get(0).getValue().toStringUtf8();
                return value;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
 
    /**
     * get all etcdKey with this prefix 从Etcd获取满足前缀的所有key
     *
     * @param prefix etcdKey's prefix
     * @return       all etcdKey with this prefix
     */
    public static List<KeyValue> getEtcdKeyWithPrefix(String prefix) {
        List<KeyValue> keyValues = new ArrayList<>();
        GetOption getOption = GetOption.newBuilder().withPrefix(ByteSequence.fromString(prefix)).build();
        try {
            List<com.coreos.jetcd.data.KeyValue> kvs = getClient().getKVClient().get(ByteSequence.fromString(prefix), getOption).get().getKvs();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyValues;
    }
 
    /**
     * put single etcdKey 将单个key放入Etcd中
     *
     * @param key   single etcdKey
     * @param value etcdKey's value
     */
    public static void putEtcdKey(String key, String value) {
    	Client client = getClient();
    	KV kvClient = client.getKVClient();
    	ByteSequence keyByte = ByteSequence.fromString(key);
    	ByteSequence valueByte = ByteSequence.fromString(value);
    	kvClient.put(keyByte, valueByte);
    }
 
    /**
     * put single etcdKey with a expire time (by etcd lease) 将一个有过期时间的key放入Etcd，通过lease机制
     *
     * @param key        single etcdKey
     * @param value      etcdKey's value
     * @param expireTime expire time (s) 过期时间，单位秒
     * @return           lease id 租约id
     */
    public static long putEtcdKeyWithExpireTime(String key, String value, long expireTime) {
        CompletableFuture<LeaseGrantResponse> leaseGrantResponse = getClient().getLeaseClient().grant(expireTime);
        PutOption putOption;
        try {
            putOption = PutOption.newBuilder().withLeaseId(leaseGrantResponse.get().getID()).build();
            getClient().getKVClient().put(ByteSequence.fromString(key), ByteSequence.fromString(value), putOption);
            return leaseGrantResponse.get().getID();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }
 
    /**
     * put single etcdKey with a lease id 将一个key绑定指定的租约放入到Etcd。
     * @param key     single etcdKey
     * @param value   etcdKey's value
     * @param leaseId lease id 租约id
     * @return revision id if exception return 0L
     */
    public static long putEtcdKeyWithLeaseId(String key, String value, long leaseId) throws Exception{
        PutOption putOption = PutOption.newBuilder().withLeaseId(leaseId).build();
        CompletableFuture<PutResponse> putResponse = getClient().getKVClient().put(ByteSequence.fromString(key), ByteSequence.fromString(value), putOption);
        try {
            return putResponse.get().getHeader().getRevision();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }
 
    /**
     * keep alive for a single lease
     * @param leaseId lease id 租约Id
     */
    public static void keepAliveEtcdSingleLease(long leaseId) {
    	getClient().getLeaseClient().keepAliveOnce(leaseId);
    }
    
    
    /**
     * release lease
     * @param leaseId lease id 租约Id
     */
    public static void revokeLease(long leaseId) {
    	getClient().getLeaseClient().revoke(leaseId);
    }
    
    /**
     * 获取锁
     * @param name
     * @param leaseId
     */
    public static void getLock(String name, long leaseId) {
    	ByteSequence key = ByteSequence.fromString(name);
    	
    	CompletableFuture<LockResponse> lock = getClient().getLockClient().lock(key, leaseId);
    
    }
    
    /**
     * 获取锁
     * @param name
     * @param leaseId
     */
    public static void lock(String name, long leaseId) {
    	ByteSequence key = ByteSequence.fromString(name);
    	CompletableFuture<LockResponse> lock = getClient().getLockClient().lock(key, leaseId);
    }
    
    /**
     * 获取锁
     * @param name
     * @param leaseId
     */
    public static void unLock(String name, long leaseId) {
    	ByteSequence key = ByteSequence.fromString(name);
    	CompletableFuture<UnlockResponse> unlock = getClient().getLockClient().unlock(key);
    	
    }
    
 
    /**
     * delete single etcdKey 从Etcd中删除单个key
     *
     * @param key etcdKey
     */
    public static void deleteEtcdKey(String key) {
    	getClient().getKVClient().delete(ByteSequence.fromString(key));
    }
 
    /**
     * delete all key with prefix 从Etcd中删除所有满足前缀匹配的key
     *
     * @param prefix etcdKey's prefix
     */
    public static void deleteEtcdKeyWithPrefix(String prefix) {
        DeleteOption deleteOption = DeleteOption.newBuilder().withPrefix(ByteSequence.fromString(prefix)).build();
        getClient().getKVClient().delete(ByteSequence.fromString(prefix), deleteOption);
    }
 
 
 
}
