package cc.mrbird.febs.system.entity;

import cc.mrbird.febs.common.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.util.Date;

/**
 * @author zt
 */

@Data
@TableName("t_sell")
@Excel("销售记录表")
public class Sell {

    /**
     * 销售id
     */
    @TableId(value = "sell_id", type = IdType.AUTO)
    private Integer sellId;

    /**
     * 销售时间
     */
    @TableField("sell_time")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    @ExcelField(value = "销售时间", writeConverter = TimeConverter.class)
    private Date sellTime;

    /**
     * 销售时间转换
     * */
    @TableField(exist = false)
    private String sellData;

    /**
     * 销售单价
     */
    @TableField("sell_uniValence")
    @ExcelField(value = "销售单价")
    private Double sellUniValence;

    /**
     * 销售数量
     */
    @TableField("sell_count")
    @ExcelField(value = "销售数量")
    private Integer sellCount;

    /**
     * 销售总金额
     */
    @TableField("sell_totalMoney")
    @ExcelField(value = "销售总金额")
    private Double sellTotalMoney;

    /**
     * 销售商品（商品id）
     */
    @TableField("commodity_id")
    private Long commodityId;

    /**
     * 销售人
     */
    @TableField("sell_person")
    @ExcelField(value = "销售人")
    private String sellPerson;

    /**
     * 客户（客户id）
     */
    @TableField("client_id")
    private Long clientId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;

    /**
     * 创建人（用户id）
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 修改人（用户id）
     */
    @TableField("modify_user_id")
    private Long modifyUserId;

    /**
     * 修改时间
     */
    @TableField("modify_time")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    /**
     * 商品名称
     */
    @TableField(exist = false)
    @ExcelField(value = "商品名称")
    private String commodityName;

    /**
     * 创建人
     */
    @ExcelField(value = "创建人")
    @TableField(exist = false)
    private String createName;

    /**
     * 修改人
     */
    @ExcelField(value = "修改人")
    @TableField(exist = false)
    private String modifyName;

    /**
     * 客户
     */
    @ExcelField(value = "客户")
    @TableField(exist = false)
    private String clientName;

}
