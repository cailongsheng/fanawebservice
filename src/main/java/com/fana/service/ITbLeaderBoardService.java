package com.fana.service;

import com.fana.config.ResponseResult;
import com.fana.entry.pojo.TbLeaderBoard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fana.entry.vo.LeaderBoardVo;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * leader cause & friends projects 服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-02
 */
public interface ITbLeaderBoardService extends IService<TbLeaderBoard> {

    ResponseResult getLeaderBoardList(LeaderBoardVo vo);

    ResponseResult updateLeaderBoardList(LeaderBoardVo vo);

    ResponseResult addLeaderBoardList(LeaderBoardVo vo);

    ResponseResult uploadLeaderBoard(MultipartFile file);

    ResponseResult selectLeaderBoardDetail(LeaderBoardVo vo);
}
