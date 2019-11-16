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
@TableName("t_commodity")
@Excel("商品信息表")
public class Commodity implements Serializable {

    /**
     * 商品id
     */
    @TableId(value = "commodity_id", type = IdType.AUTO)
    private Long commodityId;

    /**
     * 商品类型
     */
    @TableField("commodity_type")
    @ExcelField(value = "商品类型")
    private String commodityType;

    /**
     * 商品名称
     */
    @TableField("commodity_name")
    @ExcelField(value = "商品名称")
    private String commodityName;

    /**
     * 商品价格
     */
    @TableField("commodity_price")
    @ExcelField(value = "商品价格")
    private Double commodityPrice;

    /**
     * 商品数量
     */
    @TableField("commodity_quantity")
    @ExcelField(value = "商品数量")
    private Integer commodityQuantity;

    /**
     * 商品颜色
     */
    @TableField("commodity_colour")
    @ExcelField(value = "商品颜色")
    private String commodityColour;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;


}
