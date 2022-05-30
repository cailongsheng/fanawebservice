package com.fana.entry.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IPageVo {
    private long pageNum;
    private long pageSize;
    private long total;
    private List list;

}
