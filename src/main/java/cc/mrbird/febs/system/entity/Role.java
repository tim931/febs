package cc.mrbird.febs.system.entity;

import cc.mrbird.febs.common.converter.TimeConverter;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wuwenze.poi.annotation.Excel;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author MrBird
 */
/* @Data 是lombok包下的一个注解提供了getXxx()、
   setXxx()方法、equals()、hashCode()、toString()*/
@Data
@TableName("t_role") /*指向数据库对应的表*/
@Excel("角色信息表") /*用来导入导出@Excel表*/

public class Role implements Serializable {

    /*Serializable用来表明类的不同版本间的兼容性
    Java的序列化机制是通过在运行时判断类的serialVersionUID来验证版本一致性的
    在进行反序列化时，JVM会把传来的字节流中的serialVersionUID与本地相应实体（类）
    的serialVersionUID进行比较，如果相同就认为是一致的，可以进行反序列化，
    否则就会出现序列化版本不一致的异常*/

    private static final long serialVersionUID = -4493960686192269860L;
    /**
     * 角色ID
     */
    @TableId(value = "ROLE_ID", type = IdType.AUTO)
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField("ROLE_NAME")
    @ExcelField(value = "角色名称")
    @NotBlank(message = "{required}")
    @Size(max = 10, message = "{noMoreThan}")
    private String roleName;

    /**
     * 角色描述
     */
    @TableField("REMARK")
    @ExcelField(value = "角色描述")
    @Size(max = 50, message = "{noMoreThan}")
    private String remark;

    /**
     * 创建时间
     */
    @TableField("CREATE_TIME")
    @ExcelField(value = "创建时间", writeConverter = TimeConverter.class)/*时间转换器*/
    private Date createTime;

    @TableField(exist = false)
    private String createTimeFrom;
    @TableField(exist = false)
    private String createTimeTo;

    /**
     * 修改时间
     */
    @TableField("MODIFY_TIME")
    @ExcelField(value = "修改时间", writeConverter = TimeConverter.class)
    private Date modifyTime;

    /**
     * 角色对应的菜单（按钮） id
     */
    private transient String menuIds;
}
