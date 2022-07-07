package com.fana.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.pojo.TbUser;
import com.fana.entry.vo.AppUserListVo;
import com.fana.entry.vo.AppUserVo;
import com.fana.entry.vo.IPageVo;
import com.fana.exception.CustomException;
import com.fana.mapper.TbCharityMapper;
import com.fana.mapper.TbUserMapper;
import com.fana.service.ITbUserService;
import com.fana.utils.FileUtils;
import com.fana.utils.LogUtil;
import com.fana.utils.MD5Util;
import com.fana.utils.TokenManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TbUserServiceImpl extends ServiceImpl<TbUserMapper, TbUser> implements ITbUserService {

    @Resource
    TbUserMapper userMapper;
    @Resource
    FileUtils fileUtils;
    @Resource
    TokenManager tokenManager;
    @Value("${fana.ip}")
    private String fanaIp;

    @Resource
    private TbCharityMapper charityMapper;

    @Override
    public ResponseResult getList(AppUserVo vo) {
        LogUtil.addInfoLog("(app)获取用户列表", "/user/app/list", JSON.toJSON(vo));
        IPageVo iPageVo = new IPageVo();
        iPageVo.setPageNum(vo.getPageNum());
        iPageVo.setPageSize(vo.getPageSize());
        //page
        if (vo.getPageNum() == 1||vo.getPageNum() ==0) {
            vo.setPageNum(0l);
        } else {
            vo.setPageNum((vo.getPageNum() - 1) * vo.getPageSize());
        }
        List<AppUserListVo> userList = charityMapper.getUserList(vo);
        Integer userListCount = charityMapper.getUserListCount(vo);
        iPageVo.setTotal(userListCount.longValue());
        iPageVo.setList(userList);

        userList.stream().forEach(a->{
            if(StrUtil.isNotBlank(a.getAvator())){
                a.setAvator(fanaIp+"user/"+a.getAvator());
            }
            if(ObjectUtil.isEmpty(a.getSex())) a.setSex(2);
            if(StrUtil.isNotBlank(a.getType())){
                List<Integer> integers = new ArrayList<>();
                if(a.getType().contains("apple")) integers.add(1);
                if(a.getType().contains("google")) integers.add(2);
                a.setTypeList(integers);
            }
        });
        return ResponseResult.success(iPageVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult updateUser(AppUserVo vo, String Authorization) {
        LogUtil.addInfoLog("(app)修改用户信息", "/user/app/update", JSON.toJSON(vo));
        TbUser user = TbUser.builder().build();
        TbUser tbUser = userMapper.selectById(vo.getId());
        if (ObjectUtil.isNull(tbUser)) throw new CustomException(201, "The user does not exist.");
        user = user.builder()
                .id(vo.getId())
                .email(vo.getUsername())
                .password(StrUtil.isNotBlank(vo.getPassword()) ? !MD5Util.inputPassToDbPass(vo.getPassword()).equals(tbUser.getPassword()) ?
                        MD5Util.inputPassToDbPass(vo.getPassword()) : tbUser.getPassword() : tbUser.getPassword())
                .birthday(StrUtil.isBlank(vo.getBirthday()) ? tbUser.getBirthday() : vo.getBirthday())
                .lastName(StrUtil.isBlank(vo.getLastName()) ? "-" : vo.getLastName())
                .firstName(StrUtil.isBlank(vo.getFirstName()) ? "-" : vo.getFirstName())
                .avator(StrUtil.isBlank(vo.getAvator()) ? tbUser.getAvator() : fileUtils.getFileName(vo.getAvator()))
                .sex(ObjectUtil.isNull(vo.getSex()) ? tbUser.getSex() : vo.getSex())
                .isDelete(ObjectUtil.isNull(vo.getIsDelete()) ? tbUser.getIsDelete() : vo.getIsDelete())
                .build();

        try {
            userMapper.updateById(user);
        } catch (Exception e) {
            LogUtil.addErrorLog("(app)修改用户信息error", "/user/app/update", e.getMessage());
            throw new CustomException(201, e.getMessage());
        }

        return ResponseResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult selectUser(AppUserVo vo, String Authorization) {
        LogUtil.addInfoLog("(app)获取用户信息详情", "/user/app/select", JSON.toJSON(vo));
        TbUser tbUser = userMapper.selectById(vo.getId());
        if(StrUtil.isNotBlank(tbUser.getAvator())){
            tbUser.setAvator(fanaIp+"user/"+tbUser.getAvator());
        }
        return ResponseResult.success(tbUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult deleteUser(AppUserVo vo, String Authorization) {
        LogUtil.addInfoLog("(app)delete用户信息详情", "/user/app/delete", JSON.toJSON(vo));
        try {
            userMapper.updateById(TbUser.builder().id(vo.getId()).isDelete(1).build());
        } catch (Exception e) {
            LogUtil.addErrorLog("(app)delete用户信息详情error", "/user/delete", e.getMessage());
            throw new CustomException(201, e.getMessage());
        }
        return ResponseResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult addUser(AppUserVo vo) {
        LogUtil.addInfoLog("(app)添加用户信息详情", "/user/app/add", JSON.toJSON(vo));
        try {
            List<TbUser> tbWebUsers = userMapper.selectList(new QueryWrapper<TbUser>().lambda().eq(TbUser::getEmail, vo.getUsername()));
            if (!tbWebUsers.isEmpty())  return new ResponseResult(201,"username is exist");
            userMapper.insert(TbUser.builder()
                    .email(vo.getUsername())
                    .password(MD5Util.inputPassToDbPass(vo.getPassword()))
                    .birthday(StrUtil.isBlank(vo.getBirthday()) ? "-" : vo.getBirthday())
                    .lastName(StrUtil.isBlank(vo.getLastName()) ? "-" : vo.getLastName())
                    .firstName(StrUtil.isBlank(vo.getFirstName()) ? "-" : vo.getFirstName())
                    .sex(ObjectUtil.isNull(vo.getSex()) ? vo.getSex() : vo.getSex())
                    .avator(StrUtil.isBlank(vo.getAvator()) ? "" : fileUtils.getFileName(vo.getAvator()))
                    .build());
        } catch (Exception e) {
            LogUtil.addErrorLog("add用户信息详情error", "/user/app/add", e.getMessage());
            throw new CustomException(201, e.getMessage());
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult uploadUserImage(MultipartFile file) {
        LogUtil.addInfoLog("(app)用户头像上传", "/user/app/upload", null);
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
