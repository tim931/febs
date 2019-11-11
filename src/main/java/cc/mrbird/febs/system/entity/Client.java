package cc.mrbird.febs.system.entity;

import cc.mrbird.febs.common.annotation.IsMobile;
import cc.mrbird.febs.common.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author zt
 */
@Data
@TableName("t_client")
@Excel("客户信息表")
public class Client implements Serializable {

    private static final long serialVersionUID = -3625078116849731164L;

    // 客户级别：普通
    public static final String CLIENT_COMMON= "1";
    // 客户级别：中级
    public static final String CLIENT_MIDDLE = "2";
    // 客户级别：高级
    public static final String CLIENT_ADVANCED = "3";

    // 性别男
    //    public static final String SEX_MALE = "0";
    //    // 性别女
    public static final String SEX_FEMALE = "1";
    // 性别保密
    public static final String SEX_SECRECY= "2";

    /**
     * 客户 ID
     */
    @TableId(value = "CLIENT_ID", type = IdType.AUTO)
    private Long clientId;

    /**
     * 客户名称
     */
    @TableField("CLIENT_NAME")
    @ExcelField(value = "客户名称")
    private String clientName;

    /**
     * 客户手机
     */
    @TableField("CLIENT_PHONE")
    @ExcelField(value = "客户手机")
    private String clientPhone;

    /**
     * 客户邮箱
     */
    @TableField("CLIENT_EMAIL")
    @ExcelField(value = "邮箱")
    private String clientEmail;

    /**
     * 客户地址
     */
    @TableField("CLIENT_ADDRESS")
    @ExcelField(value = "客户地址")
    private String clientAddress;

    /**
     * 客户级别  客户级别 1普通、2中级、3高级
     */
    @TableField("CLIENT_LEVEL")
    @ExcelField(value = "级别", writeConverterExp = "1=普通,2=中级,3=高级")
    private Integer clientLevel;

    /**
     * 客户性别
     */
    @TableField("CLIENT_SEX")
    @ExcelField(value = "性别", writeConverterExp = "0=男,1=女,2=保密")
    private String clientSex;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)
    private Date createTime;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @JsonFormat(pattern = "yyyy年MM月dd日", timezone = "GMT+8")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    /**
     * 创建人
     */
    @TableField("CREATOR")
    @ExcelField(value = "创建人")
    private String creator;



}
