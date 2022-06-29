package com.fana.entry.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiUserMoneyVo {

    private Integer id;

    /**
     * 关联机构id
     */
    private Integer charityId;

    /**
     * 用户邮箱
     */
    private String username;

    /**
     * 用户名id
     */
    private Integer userId;

    /**
     * 捐款金额
     */
    private BigDecimal money;

    /**
     * 机构名称
     */
    private String charityName;


    /**
     * 当前页
     */
    private long pageNum;

    /**
     * 当前条数
     */
    private long pageSize;

    /**
     * 条件
     */
    private String search;

  /**
     * 删除
     */
    private String isDelete;



//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")//序列化
//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")//反序列化
    private LocalDateTime updateAt;


//    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")//序列化
//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")//反序列化
    private LocalDateTime createAt;
}
