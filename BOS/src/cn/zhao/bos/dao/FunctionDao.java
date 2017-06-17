package cn.zhao.bos.dao;

import java.util.List;

import cn.zhao.bos.dao.base.BaseDao;
import cn.zhao.bos.vo.Function;

public interface FunctionDao extends BaseDao<Function> {
    /**
     * 根据用户名查询权限
     * @param id
     */
    public List<Function> findListByUserId(String id);
    /**
     * 查询所有菜单
     * @return
     */
    public List<Function> findAllMenu();
    /**
     * 根据用户ID查询菜单
     * @param id
     * @return
     */
    public List<Function> findMenuByUserId(String id);

}
