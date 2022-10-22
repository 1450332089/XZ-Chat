package client.service;

import client.mapper.UserMapper;
import common.pojo.LoggedUser;
import client.pojo.User;
import common.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.HashSet;

public class UserServiceImpl implements UserService{
    private SqlSession sqlSession = MybatisUtils.getSqlSession();
    private UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    //登录，返回值为1，则登陆成功
    @Override
    public String logIn(int id) {
         String res = userMapper.logIn(id);
        sqlSession.commit();
        return res;
    }
    //注册
    @Override
    public int addUser(User user) {
        int i = userMapper.addUser(user);
        sqlSession.commit();
        return i;
    }

    @Override
    public int updateName(int id,String name) {
        HashMap<String , Object> map = new HashMap<>();
        map.put("id",id);
        map.put("name",name);
        int i = userMapper.updateName(map);
        sqlSession.commit();
        return i;
    }


    @Override
    public String getName(int id) {
        return userMapper.getName(id);
    }

    @Override
    public void setUser(int id, String name) {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String hostAddress = ip.getHostAddress();
        LoggedUser user = new LoggedUser(id, name, hostAddress);
        User.setLoggedUser(user);
    }



    public void close(){
        sqlSession.close();
    }
}
