package com.fana.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.pojo.TbUser;
import com.fana.entry.pojo.TbWebUser;
import com.fana.entry.vo.IPageVo;
import com.fana.entry.vo.LoginVo;
import com.fana.entry.vo.WebUserVo;
import com.fana.exception.CustomException;
import com.fana.mapper.TbUserMapper;
import com.fana.mapper.TbWebUserMapper;
import com.fana.service.ITbWebUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-05-25
 */
@Slf4j
@Service
public class TbWebUserServiceImpl extends ServiceImpl<TbWebUserMapper, TbWebUser> implements ITbWebUserService {

    @Resource
    private TbWebUserMapper webUserMapper;
    @Resource
    private TbUserMapper userMapper;
    @Resource
    FileUtils fileUtils;
    @Resource
    private TokenManager tokenManager;

    @Override
    public ResponseResult login(LoginVo vo) {

        TbWebUser tbWebUser = webUserMapper.selectOne(new QueryWrapper<TbWebUser>()
                .lambda().eq(TbWebUser::getUsername, vo.getUsername()));
        if (ObjectUtil.isEmpty(tbWebUser)) {
            log.info("用户不存在...");
            throw new CustomException(Status.USER_VOID.code, Status.USER_VOID.message);
        }
        if (!MD5Util.inputPassToDbPass(vo.getPassword()).equals(tbWebUser.getPassword())) {
            log.info("密码错误...");
            throw new CustomException(Status.PASSWORD_ERROR.code, Status.PASSWORD_ERROR.message);
        }
        String token = tokenManager.createToken(tbWebUser.getId());
        LoginVo response = LoginVo.builder()
                .username(tbWebUser.getUsername())
                .id(tbWebUser.getId().toString())
                .roles(tbWebUser.getRoleId())
                .token(token)
                .build();
        return ResponseResult.success(response);
    }

    @Override
    public ResponseResult getList(WebUserVo vo) {
        LogUtil.addInfoLog("获取用户列表", "/user/list", JSON.toJSON(vo));
        QueryWrapper<TbWebUser> queryWrapper = new QueryWrapper<>();
        QueryWrapper<TbUser> query = new QueryWrapper<>();

        if (StrUtil.isNotBlank(vo.getSearch()))
            queryWrapper.like("username", vo.getSearch()).or().like("role_id", vo.getSearch());
        IPage<TbWebUser> page = new Page<>(vo.getPageNum(), vo.getPageSize());
        IPage<TbWebUser> iPage = webUserMapper.selectPage(page, queryWrapper);
        IPageVo build = IPageVo.builder().total(iPage.getTotal()).pageSize(iPage.getSize()).pageNum(iPage.getCurrent()).list(iPage.getRecords()).build();
        return ResponseResult.success(build);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateUser(WebUserVo vo,String Authorization) {
        LogUtil.addInfoLog("修改用户信息", "/user/update", JSON.toJSON(vo));
        TbWebUser webUser = TbWebUser.builder().build();
        TbWebUser tbWebUser = webUserMapper.selectById(tokenManager.getUserId(Authorization));
        if (ObjectUtil.isNull(tbWebUser)) throw new CustomException(201, "user don`t exist");
        webUser = TbWebUser.builder()
                .id(tokenManager.getUserId(Authorization))
                .username(vo.getUsername())
                .password(
                        StrUtil.isNotBlank(vo.getPassword()) ? !MD5Util.inputPassToDbPass(vo.getPassword()).equals(tbWebUser.getPassword()) ?
                                MD5Util.inputPassToDbPass(vo.getPassword()) : tbWebUser.getPassword() : tbWebUser.getPassword()
                )
                .roleId(vo.getRoleId())
                .isDelete(vo.getIsDelete())
                .build();
        try {
            webUserMapper.updateById(webUser);
        } catch (Exception e) {
            LogUtil.addErrorLog("修改用户信息error", "/user/update", e.getMessage());
            throw new CustomException(201, e.getMessage());
        }


        return ResponseResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult selectUser(WebUserVo vo,String Authorization) {
        LogUtil.addInfoLog("获取用户信息详情", "/user/select", JSON.toJSON(vo));
        if (vo.getPlatform().equals(0)) {//web
            TbWebUser tbWebUser = webUserMapper.selectById(tokenManager.getUserId(Authorization));
            return ResponseResult.success(tbWebUser);
        }
        return ResponseResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteUser(WebUserVo vo,String Authorization) {
        LogUtil.addInfoLog("delete用户信息详情", "/user/delete", JSON.toJSON(vo));
        UpdateWrapper queryWrapper = new UpdateWrapper();
        try {
            queryWrapper.eq("id", tokenManager.getUserId(Authorization));
            queryWrapper.set("id", tokenManager.getUserId(Authorization));
//                webUserMapper.updateById(TbWebUser.builder().id(vo.getId()).isDelete(1).build());
            webUserMapper.update(TbWebUser.builder().id(vo.getId()).isDelete(1).build(), queryWrapper);
        } catch (Exception e) {
            LogUtil.addErrorLog("delete用户信息详情error", "/user/delete", e.getMessage());
            throw new CustomException(201, e.getMessage());
        }
        return ResponseResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addUser(WebUserVo vo) {
        try {
            List<TbWebUser> tbWebUsers = webUserMapper.selectList(new QueryWrapper<TbWebUser>().lambda().eq(TbWebUser::getUsername, vo.getUsername()));
            if (!tbWebUsers.isEmpty())  return new ResponseResult(201,"username is exist");
            webUserMapper.insert(TbWebUser.builder()
                    .username(vo.getUsername())
                    .password(MD5Util.inputPassToDbPass(vo.getPassword()))
                    .build());
        } catch (Exception e) {
            LogUtil.addErrorLog("add用户信息详情error", "/user/add", e.getMessage());
            throw new CustomException(201, e.getMessage());
        }

        return ResponseResult.success();
    }

    @Override
    public ResponseResult uploadUserImage(MultipartFile file) {
        if (file == null) {
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The file did not fill in  ");
        }
        String upload = null;
        try {
            upload = fileUtils.uploadFile(file, "user");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(upload)) {
            throw new CustomException(Status.IMAGE_UPLOAD_FAILED.getCode(), Status.IMAGE_UPLOAD_FAILED.getMessage());
        }
        return ResponseResult.success(upload);
    }


}
