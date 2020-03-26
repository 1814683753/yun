package com.le.yun.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author ：hjl
 * @date ：2020/3/13 16:53
 * @description：shiro session管理使用redis
 * @modified By：
 */
@Component
@Slf4j
public class RedisSessionDao extends AbstractSessionDAO {
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * redis数据保存时间，单位秒
     */
    private long expireTime = 36000;


    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.SECONDS);
        log.info("创建会话.........{}",sessionId);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.info("读取会话......{}",sessionId);
        return sessionId == null ? null : (Session) redisTemplate.opsForValue().get(sessionId);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session != null && session.getId() != null) {
            session.setTimeout(expireTime * 1000);
            log.info("更新会话.........{}",session.getId());
            redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.SECONDS);
        }
    }

    @Override
    public void delete(Session session) {
        if (session != null && session.getId() != null) {
            log.info("删除会话..........{}",session.getId());
            redisTemplate.opsForValue().getOperations().delete(session.getId());
        }
    }

    @Override
    public Collection<Session> getActiveSessions() {
        String  s = "";
        s.hashCode();
        log.info("查询活跃会话.........");
        return redisTemplate.keys("*");
    }
}
