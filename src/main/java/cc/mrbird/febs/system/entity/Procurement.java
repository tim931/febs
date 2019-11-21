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

import java.io.Serializable;
import java.util.Date;

/**
 * @author zt
 */
@Data
@TableName("t_procurement")
@Excel("采购商品表")
public class Procurement implements Serializable {

    /**
     * 采购Id
     */
    @TableId(value = "procurement_id", type = IdType.AUTO)
    private Integer procurementId;

    /**
     * 商品Id
     */
    @TableField("commodity_id")
    private String commodityId;


    /**
     * 商品价格
     */
    @TableField(exist = false) //框架方法扫描不会扫描加了这个注解的
    private Double commodityPrice;

    /**
     * 商品名称
     */
    @TableField(exist = false)
    private String commodityName;


    /**
     * 采购数量
     */
    @TableField("procurement_count")
    @ExcelField(value = "采购数量")
    private Integer procurementCount;

    /**
     * 采购金额
     */
    @TableField("procurement_money")
    @ExcelField(value = "采购金额")
    private Double procurementMoney;

    /**
     * 采购时间
     */
    @TableField("procurement_time")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    @ExcelField(value = "采购时间", writeConverter = TimeConverter.class)
    private Date procurementTime;

    /**
     * 采购人
     */
    @TableField("procurement_person")
    @ExcelField(value = "采购人")
    private String procurementPerson;

}
