package ltd.dreamcraft.dreamactivity.data;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;


public class AccumulateReward implements Rewardable {
    //节点名
    private String SectionName;
    //需要的在线时间
    private int value;
    //成功领取的信息
    private String SuccessMessage;
    //失败领取的信息 (一般是累计在线时间不足)
    private String FailedMessage;
    //已经领取过的信息
    private String AlreadyMessage;
    //需要执行的命令列表
    private List<String> commands;

    public AccumulateReward(ConfigurationSection section) {
        this.SectionName = section.getName();
        this.value = section.getInt("value");
        this.SuccessMessage = section.getString("Success");
        this.FailedMessage = section.getString("Failed");
        this.commands = section.getStringList("commands");
    }

    /**
     * 获取节点名
     *
     * @return String 类型的节点名称
     */
    public String getSectionName() {
        return SectionName;
    }

    /**
     * 传入一个字符串,将节点名设置为该字符串
     *
     * @param sectionName 节点名
     */
    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }

    /**
     * 获取奖励需要累积领取次数
     *
     * @return int 类型的累积领取次数
     */
    public int getValue() {
        return value;
    }

    /**
     * 传入一个整数,将在线时间设置为这个整数
     *
     * @param value 需要在线时间(分钟)
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * 获取成功领取奖励的信息
     *
     * @return String 类型的信息
     */
    public String getSuccessMessage() {
        return SuccessMessage;
    }

    /**
     * 设置成功领取奖励的信息
     *
     * @param successMessage 信息内容
     */
    public void setSuccessMessage(String successMessage) {
        SuccessMessage = successMessage;
    }

    /**
     * 获取奖励领取失败的信息
     *
     * @return String 类型的信息
     */
    public String getFailedMessage() {
        return FailedMessage;
    }

    /**
     * 设置奖励领取失败的信息
     *
     * @param failedMessage 信息内容
     */
    public void setFailedMessage(String failedMessage) {
        FailedMessage = failedMessage;
    }

    /**
     * 获取奖励已经领取的信息
     *
     * @return String 类型的信息
     */
    public String getAlreadyMessage() {
        return AlreadyMessage;
    }

    /**
     * 设置奖励已经领取的信息
     *
     * @param alreadyMessage 信息内容
     */
    public void setAlreadyMessage(String alreadyMessage) {
        AlreadyMessage = alreadyMessage;
    }

    /**
     * 获取需要执行的命令列表
     *
     * @return List<String> 类型
     */
    public List<String> getCommands() {
        return commands;
    }

    /**
     * 设置需要执行的命令列表
     *
     * @param commands 指令列表
     */
    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
