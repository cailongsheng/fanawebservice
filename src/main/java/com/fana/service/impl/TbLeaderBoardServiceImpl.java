package com.fana.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbLeaderBoard;
import com.fana.entry.vo.IPageVo;
import com.fana.entry.vo.LeaderBoardVo;
import com.fana.mapper.TbLeaderBoardMapper;
import com.fana.service.ITbLeaderBoardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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

    @Override
    public ResponseResult getLeaderBoardList(LeaderBoardVo vo) {
        IPage<TbLeaderBoard> boardIPage = leaderBoardMapper.selectPage(new Page<>(vo.getPageNum(), vo.getPageSize()),
                new QueryWrapper<TbLeaderBoard>().lambda().eq(TbLeaderBoard::getIsDelete,0)
        );
        IPageVo iPageVo = new IPageVo();
        iPageVo.setPageNum(boardIPage.getCurrent());
        iPageVo.setPageSize(boardIPage.getSize());
        iPageVo.setTotal(boardIPage.getTotal());
        iPageVo.setList(boardIPage.getRecords());
        return ResponseResult.success(iPageVo);
    }

    @Override
    public ResponseResult updateLeaderBoardList(LeaderBoardVo vo) {

        return null;
    }

    @Override
    public ResponseResult addLeaderBoardList(LeaderBoardVo vo) {
        return null;
    }

}
