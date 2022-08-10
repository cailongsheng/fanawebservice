package com.fana.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.generator.config.IFileCreate;
import com.fana.config.ResponseResult;
import com.fana.config.Status;
import com.fana.entry.pojo.TbCharity;
import com.fana.entry.pojo.TbClass;
import com.fana.entry.pojo.TbLeaderBoard;
import com.fana.entry.vo.IPageVo;
import com.fana.entry.vo.LeaderBoardListVo;
import com.fana.entry.vo.LeaderBoardVo;
import com.fana.exception.CustomException;
import com.fana.mapper.TbCharityMapper;
import com.fana.mapper.TbClassMapper;
import com.fana.mapper.TbLeaderBoardMapper;
import com.fana.service.ITbLeaderBoardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fana.utils.FileUtils;
import com.fana.utils.LogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * leader cause & friends projects 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-02
 */
@Service
public class TbLeaderBoardServiceImpl extends ServiceImpl<TbLeaderBoardMapper, TbLeaderBoard> implements ITbLeaderBoardService {
    @Resource
    TbLeaderBoardMapper leaderBoardMapper;
    @Resource
    TbClassMapper classMapper;
    @Resource
    FileUtils fileUtils;
    @Resource
    TbCharityMapper charityMapper;
    @Value("${fana.ip}")
    private String ip;

    @Override
    public ResponseResult getLeaderBoardList(LeaderBoardVo vo) {
        String path = ip + "leader/";
        LogUtil.addInfoLog("get leader board list", "/leader/board/list", vo.toString());
        QueryWrapper<TbLeaderBoard> queryWrapper = new QueryWrapper<>();
//        if (ObjectUtil.isNotEmpty(vo.getIsDelete()))
            queryWrapper.ne("is_delete", 1);
        if (ObjectUtil.isNotEmpty(vo.getCategory()))
            queryWrapper.eq("category", vo.getCategory());
        IPage<TbLeaderBoard> boardIPage = leaderBoardMapper.selectPage(new Page<>(vo.getPageNum(), vo.getPageSize()), queryWrapper);
        List<LeaderBoardListVo> list = new ArrayList<>();
        boardIPage.getRecords().forEach(leader -> {
            LeaderBoardListVo leaderBoardListVo = new LeaderBoardListVo();
            TbCharity tbCharity = charityMapper.selectById(leader.getCharityId());
            if (ObjectUtil.isNotNull(tbCharity)) {
                leaderBoardListVo.setCharityName(tbCharity.getCharity());
                if(StrUtil.isNotBlank(tbCharity.getImageUrl())) {
                    leaderBoardListVo.setImageUrl(ip + "charity/" + tbCharity.getImageUrl());
                }
                TbClass tbClass = classMapper.selectById(tbCharity.getClasss());
                    //categoryName, imageUrl
                if(ObjectUtil.isNotEmpty(tbClass))  leaderBoardListVo.setCategoryName(tbClass.getClassType());
            }
            leader.setActivityImageUrl(path + leader.getActivityImageUrl());
            leader.setCategory(leader.getCategory());
            BeanUtil.copyProperties(leader,leaderBoardListVo);
            list.add(leaderBoardListVo);
        });
        IPageVo iPageVo = new IPageVo();
        iPageVo.setPageNum(boardIPage.getCurrent());
        iPageVo.setPageSize(boardIPage.getSize());
        iPageVo.setTotal(boardIPage.getTotal());
        iPageVo.setList(list);
        LogUtil.returnInfoLog("get leader board list", "/leader/board/list", iPageVo.toString());
        return ResponseResult.success(iPageVo);
    }

    @Override
    public ResponseResult updateLeaderBoardList(LeaderBoardVo vo) {
        LogUtil.addInfoLog("修改 leader board", "/leader/board/update", JSON.toJSON(vo));
        try {
            TbLeaderBoard tbLeaderBoard = leaderBoardMapper.selectById(vo.getId());
            if (ObjectUtil.isNull(tbLeaderBoard)) return new ResponseResult(200, "data is null");
            leaderBoardMapper.updateById(TbLeaderBoard.builder()
                    .id(tbLeaderBoard.getId())
                    .isDelete(ObjectUtil.isNull(vo.getIsDelete()) ? 0 : vo.getIsDelete())
                    .activityImageUrl(StrUtil.isBlank(vo.getActivityImageUrl()) ? tbLeaderBoard.getActivityImageUrl() : fileUtils.getFileName(vo.getActivityImageUrl()))
                    .activityName(StrUtil.isBlank(vo.getActivityName()) ? tbLeaderBoard.getActivityName() : vo.getActivityName())
                    .donateGoal(ObjectUtil.isEmpty(vo.getDonateGoal()) ? tbLeaderBoard.getDonateGoal() : vo.getDonateGoal())
                    .donationAmount(ObjectUtil.isEmpty(vo.getDonationAmount()) ? tbLeaderBoard.getDonationAmount() : vo.getDonationAmount())
                    .charityId(ObjectUtil.isEmpty(vo.getCharityId()) ? tbLeaderBoard.getCharityId() : vo.getCharityId())
                    .category(ObjectUtil.isNull(vo.getCategory()) ? tbLeaderBoard.getCategory() : vo.getCategory())
                    .build());
        } catch (Exception e) {
            LogUtil.addErrorLog("修改 leader board error", "/leader/board/update", e.getMessage());
            throw new CustomException(201, e.getMessage());
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult addLeaderBoardList(LeaderBoardVo vo) {
        LogUtil.addInfoLog("添加leader board", "/leader/board/add", JSON.toJSON(vo));
        if (ObjectUtil.isNull(vo.getCharityId())) return new ResponseResult(200, "Please bind the charity");
        try {
            leaderBoardMapper.insert(TbLeaderBoard.builder()
                    .activityName(StrUtil.isBlank(vo.getActivityName()) ? "-" : vo.getActivityName())
                    .activityImageUrl(StrUtil.isBlank(vo.getActivityImageUrl()) ? "-" : fileUtils.getFileName(vo.getActivityImageUrl()))
                    .donateGoal(ObjectUtil.isNull(vo.getDonateGoal()) ? null : vo.getDonateGoal())
                    .charityId(vo.getCharityId())
                    .category(ObjectUtil.isNull(vo.getCategory()) ? -1 : vo.getCategory())
                    .build());
        } catch (Exception e) {
            LogUtil.addErrorLog("添加leader boarderror", "/leader/board/add", e.getMessage());
            throw new CustomException(201, e.getMessage());
        }
        return ResponseResult.success();
    }

    @Override
    public ResponseResult uploadLeaderBoard(MultipartFile file) {
        LogUtil.addInfoLog("leader board 图片上传", "/leader/board/upload", null);
        if (file == null) {
            return new ResponseResult(Status.PARAMETER_ERROR.code, "The file did not fill in  ");
        }
        String upload = null;
        try {
            upload = fileUtils.uploadFile(file, "leader");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(upload)) {
            throw new CustomException(Status.IMAGE_UPLOAD_FAILED.getCode(), Status.IMAGE_UPLOAD_FAILED.getMessage());
        }
        return ResponseResult.success(upload);
    }

    @Override
    public ResponseResult selectLeaderBoardDetail(LeaderBoardVo vo) {
        LogUtil.addInfoLog("获取 leader board 详情", "/leader/board/select", null);
        try {
            TbLeaderBoard tbLeaderBoard = leaderBoardMapper.selectById(vo.getId());
            if (ObjectUtil.isNull(tbLeaderBoard)) return ResponseResult.success();
            TbCharity tbCharity = charityMapper.selectById(tbLeaderBoard.getCharityId());
            LeaderBoardListVo leaderBoardListVo = new LeaderBoardListVo();
            if (ObjectUtil.isNotNull(tbCharity)) {
                leaderBoardListVo.setCharityName(tbCharity.getCharity());
                if (StrUtil.isNotBlank(tbCharity.getImageUrl())) {
                    leaderBoardListVo.setImageUrl(ip + "charity/" + tbCharity.getImageUrl());
                }
                TbClass tbClass = classMapper.selectById(tbCharity.getClasss());
                //categoryName, imageUrl
                if (ObjectUtil.isNotEmpty(tbClass)) {
                    leaderBoardListVo.setCategoryName(tbClass.getClassType());
                }
                BeanUtil.copyProperties(tbCharity,leaderBoardListVo);
            }
            return ResponseResult.success(JSON.toJSON(leaderBoardListVo));
        } catch (Exception e) {
            LogUtil.addErrorLog("获取leader board error", "/leader/board/select", e.getMessage());
            throw new CustomException(201, e.getMessage());
        }
    }

}
