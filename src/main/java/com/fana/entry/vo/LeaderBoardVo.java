package com.fana.entry.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("leader board entry")
public class LeaderBoardVo {
    private Long pageNum;
    private Long pageSize;
    private int id;

}
