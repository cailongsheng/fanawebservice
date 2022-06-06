package com.fana.controller;


import com.fana.config.ResponseResult;
import com.fana.entry.vo.LeaderBoardVo;
import com.fana.service.ITbLeaderBoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * <p>
 * leader cause & friends projects 前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2022-06-02
 */
@RestController
@RequestMapping("/leader/board")
@Api("leader board 模块")
public class TbLeaderBoardController {

    @Resource
    ITbLeaderBoardService leaderBoardService;


    @ApiOperation("leader board list")
    @PostMapping("/list")
    public ResponseResult getLeaderBoardList(@RequestBody LeaderBoardVo vo){
        return leaderBoardService.getLeaderBoardList(vo);
    }

    @ApiOperation("update leader board date")
    @PostMapping("/update")
    public ResponseResult updateLeaderBoard(@RequestBody LeaderBoardVo vo){
        return leaderBoardService.updateLeaderBoardList(vo);
    }

    @ApiOperation("add leader board date")
    @PostMapping("/add")
    public ResponseResult addLeaderBoard(@RequestBody LeaderBoardVo vo){
        return leaderBoardService.addLeaderBoardList(vo);
    }

    @ApiOperation(" leader board date detail ")
    @PostMapping("/select")
    public ResponseResult selectLeaderBoardDetail(@RequestBody LeaderBoardVo vo){
        return leaderBoardService.selectLeaderBoardDetail(vo);
    }

    @ApiOperation("upload leader board date")
    @PostMapping("/upload")
    public ResponseResult uploadLeaderBoard(MultipartFile file){
        return leaderBoardService.uploadLeaderBoard(file);
    }

}
