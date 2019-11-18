package cc.mrbird.febs.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zt
 */
@Data
@TableName("t_car") //用来命名当前实体类对应数据库表的名字
@Excel("车辆信息导入导出") /*用来导入导出@Excel表*/
public class Car implements Serializable{

    private static final long serialVersionUID = 9114968824692462696L;

    /**
     * 车辆ID
     */
    @TableId(value = "CAR_ID" , type = IdType.AUTO)
    private Long carId;

    /**
     *车牌号
     */
    @TableField("LICENSE_PLATE")
    @ExcelField(value="车牌号", required = true,maxLength = 7,comment = "提示：必填，长度不能超过7个字符")
    private String licensePlate;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    /**  @ExcelField(value = "创建时间",required = false, writeConverter = TimeConverter.class,comment = "提示：创建时间不必填")*/
    private Date createTime;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    /**  @ExcelField(value = "修改时间",required = false, writeConverter = TimeConverter.class,comment = "提示：修改时间不必填")时间转换器 */
    private Date modifyTime;

    /**
     * 创建者
     */
    @TableField("CREATOR")
    @ExcelField(value="创建者",comment = "提示：创建人必填")
    private String creator;

}
