package com.liezh.domain.constant;

/**
 * Created by Administrator on 2017/10/2.
 *  响应码  --  消息   枚举
 */
public enum ResponseEnum {

    SUCCESS(1000, "成功"),
    ERROR(10001, "未知错误"),

    SERVER_ERROR(10002, "服务异常"),
    RECORD_NOT_FOUND(10003, "记录不存在"),
    RECORD_EXIST(10004, "记录已存在"),

    INSERT_FAILURE(10005, "添加失败"),
    UPDATE_FAILURE(10006, "更新失败"),
    DELETE_FAILURE(10007, "删除失败"),
    QUERY_FAILURE(10008, "查询失败"),

    OPERATION_DISABLE(10009, "操作禁用"),
    ILLEGAL_ARGUMENT(10010, "参数错误"),

    USER_ACCOUNT_EXIST(20001, "账号名已经注册"),
    USER_MOBILE_EXIST(20002, "手机号已注册"),
    USER_EMAIL_EXIST(20003, "邮箱已注册"),
    USER_PASSWORD_ILLEGAL(20004, "用户密码验证出错"),
    USER_TARGET_NOT_FOUND(20005, "目标用户不存在"),
    USER_TARGET_EXIST(20006, "目标用户已关注"),
    USER_TARGET_IS_SELF(20007, "目标用户不能是自己"),
    USER_FOLLOW_FAILURE(20008, "关注失败"),
    USER_UNFOLLOW_FAILURE(20009, "取消关注失败"),
    USER_TOKEN_TIMEOUT(20010, "密码token过期"),
    USER_ROLE_MAPPING_FAILURE(20011, "用户角色映射失败"),

    ROLE__FAILURE(30001, "角色初始化失败"),
    ROLE_NOT_FOUND(30002, "角色不存在"),
    ROLE_NAME_EXIST(30003, "角色名已存在"),

    SUBJECT_NOT_FOUND(50001, "主题菜单不存在"),
    SUBJECT_CONTRIBUTE_FAILURE(50002, "投稿失败"),
    SUBJECT_MAX_CONTRIBUTE(50003, "投稿次数过多"),
    SUBJECT_CONTRIBUTE_RECORD_NOT_FOUND(50004, "没有投稿记录"),
    SUBJECT_CONTRIBUTE_PASS_FAILURE(50005, "主题菜单投稿审核通过失败"),
    SUBJECT_CONTRIBUTE_REJECT_FAILURE(50006, "主题菜单投搞审核拒绝失败"),

    RECIPE_NOT_FOUND(60001, "菜谱不存在"),

    NEED_LOGIN(90000, "需要登录"),
    PERMISSION_DENIED(90001, "权限不足"),

    ;


    private final  int code;
    private final  String desc;

    ResponseEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
