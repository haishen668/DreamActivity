package ltd.dreamcraft.dreamactivity.data;

import java.util.List;

public interface Rewardable {
    // 获取节点名
    String getSectionName();

    // 获取奖励需要在线的时间(单位:分钟)
    int getValue();

    // 获取成功领取奖励的信息
    String getSuccessMessage();

    // 获取奖励领取失败的信息
    String getFailedMessage();

    // 获取奖励已经领取的信息
    String getAlreadyMessage();

    // 获取需要执行的命令列表
    List<String> getCommands();

    // 设置节点名
    void setSectionName(String sectionName);

    // 设置奖励需要在线的时间(单位:分钟)
    void setValue(int value);

    // 设置成功领取奖励的信息
    void setSuccessMessage(String successMessage);

    // 设置奖励领取失败的信息
    void setFailedMessage(String failedMessage);

    // 设置奖励已经领取的信息
    void setAlreadyMessage(String alreadyMessage);

    // 设置需要执行的命令列表
    void setCommands(List<String> commands);
}
