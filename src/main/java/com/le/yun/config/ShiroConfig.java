package com.le.yun.config;

import com.le.yun.filter.MyLogoutFilter;
import com.le.yun.filter.ShiroLoginFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author ：hjl
 * @date ：2019/11/21 17:58
 * @description： shiro配置类
 * @modified By：
 */
@Configuration
@Slf4j
public class ShiroConfig {

        @Value("${spring.redis.shiro.host}")
        private String host;
        @Value("${spring.redis.shiro.timeout}")
        private int timeout;
        /**
         * shiro过滤器设置
         * @param securityManager
         * @return
         */
        @Bean
        public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
            log.info("ShiroConfiguration.shirFilter()");
            ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
            Map<String, Filter> filterMap = shiroFilterFactoryBean.getFilters();
            filterMap.put("authc",new ShiroLoginFilter());
            filterMap.put("logout",new MyLogoutFilter());
            shiroFilterFactoryBean.setSecurityManager(securityManager);
            Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
            // 注意过滤器配置顺序 不能颠倒
            // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
            filterChainDefinitionMap.put("/logout", "logout");
            filterChainDefinitionMap.put("/common/login", "anon");
            filterChainDefinitionMap.put("/**", "anon");
            shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
            return shiroFilterFactoryBean;
        }

        /**
         * 凭证匹配器
         * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了）
         *
         * @return
         */
        @Bean
        public HashedCredentialsMatcher hashedCredentialsMatcher() {
            HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
            //散列算法:这里使用MD5算法;
            hashedCredentialsMatcher.setHashAlgorithmName("md5");
            //散列的次数，比如散列两次，相当于 md5(md5(""));
            hashedCredentialsMatcher.setHashIterations(2);
            return hashedCredentialsMatcher;
        }

        @Bean
        public MyShiroRealm myShiroRealm() {
            MyShiroRealm myShiroRealm = new MyShiroRealm();
            myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
            return myShiroRealm;
        }


        @Bean
        public SecurityManager securityManager(RedisSessionDao redisSessionDAO) {
            DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
            securityManager.setRealm(myShiroRealm());
            // 自定义session管理 使用redis
            securityManager.setSessionManager(sessionManager(redisSessionDAO));
            // 自定义缓存实现 使用redis
            securityManager.setCacheManager(shiroCacheManager());
            return securityManager;
        }

        /**
         * 自定义sessionManager
         */
        @Bean
        public SessionManager sessionManager(RedisSessionDao redisSessionDAO) {
            DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
            sessionManager.setGlobalSessionTimeout(3600 * 1000);
            sessionManager.setDeleteInvalidSessions(true);
            sessionManager.setSessionDAO(redisSessionDAO);
            sessionManager.setSessionValidationSchedulerEnabled(true);
            sessionManager.setDeleteInvalidSessions(true);
            /**
             * 修改Cookie中的SessionId的key，默认为JSESSIONID，自定义名称
             */
            sessionManager.setSessionIdCookie(new SimpleCookie("JSESSIONID"));
            /*MySessionManager mySessionManager = new MySessionManager();
            mySessionManager.setSessionDAO(redisSessionDAO());*/
            return sessionManager;
        }

        /**
         * 配置shiro redisManager
         * <p>
         * 使用的是shiro-redis开源插件
         *
         * @return
         */
        public RedisManager redisManager() {
            RedisManager redisManager = new RedisManager();
            // host 127.0.0.1:6379
            redisManager.setHost(host);
            redisManager.setTimeout(timeout);
            return redisManager;
        }

        /**
         * cacheManager 缓存 redis实现
         * <p>
         * 使用的是shiro-redis开源插件
         *
         * @return
         */
        @Bean
        public RedisCacheManager shiroCacheManager() {
            RedisCacheManager redisCacheManager = new RedisCacheManager();
            redisCacheManager.setRedisManager(redisManager());
            return redisCacheManager;
        }

        /**
         * 开启shiro aop注解支持.
         * 使用代理方式;所以需要开启代码支持;
         *
         * @param securityManager
         * @return
         */
        @Bean
        public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
            AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
            authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
            return authorizationAttributeSourceAdvisor;
        }

        /**
         * 注册全局异常处理
         * @return
         */
        /*@Bean(name = "exceptionHandler")
        public HandlerExceptionResolver handlerExceptionResolver() {
            return new MyExceptionHandler();
        }*/
}
