package ltd.dreamcraft.dreamactivity.data;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;


public class AccumulateReward implements Rewardable {
    //�ڵ���
    private String SectionName;
    //��Ҫ������ʱ��
    private int value;
    //�ɹ���ȡ����Ϣ
    private String SuccessMessage;
    //ʧ����ȡ����Ϣ (һ�����ۼ�����ʱ�䲻��)
    private String FailedMessage;
    //�Ѿ���ȡ������Ϣ
    private String AlreadyMessage;
    //��Ҫִ�е������б�
    private List<String> commands;

    public AccumulateReward(ConfigurationSection section) {
        this.SectionName = section.getName();
        this.value = section.getInt("value");
        this.SuccessMessage = section.getString("Success");
        this.FailedMessage = section.getString("Failed");
        this.commands = section.getStringList("commands");
    }

    /**
     * ��ȡ�ڵ���
     *
     * @return String ���͵Ľڵ�����
     */
    public String getSectionName() {
        return SectionName;
    }

    /**
     * ����һ���ַ���,���ڵ�������Ϊ���ַ���
     *
     * @param sectionName �ڵ���
     */
    public void setSectionName(String sectionName) {
        SectionName = sectionName;
    }

    /**
     * ��ȡ������Ҫ�ۻ���ȡ����
     *
     * @return int ���͵��ۻ���ȡ����
     */
    public int getValue() {
        return value;
    }

    /**
     * ����һ������,������ʱ������Ϊ�������
     *
     * @param value ��Ҫ����ʱ��(����)
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * ��ȡ�ɹ���ȡ��������Ϣ
     *
     * @return String ���͵���Ϣ
     */
    public String getSuccessMessage() {
        return SuccessMessage;
    }

    /**
     * ���óɹ���ȡ��������Ϣ
     *
     * @param successMessage ��Ϣ����
     */
    public void setSuccessMessage(String successMessage) {
        SuccessMessage = successMessage;
    }

    /**
     * ��ȡ������ȡʧ�ܵ���Ϣ
     *
     * @return String ���͵���Ϣ
     */
    public String getFailedMessage() {
        return FailedMessage;
    }

    /**
     * ���ý�����ȡʧ�ܵ���Ϣ
     *
     * @param failedMessage ��Ϣ����
     */
    public void setFailedMessage(String failedMessage) {
        FailedMessage = failedMessage;
    }

    /**
     * ��ȡ�����Ѿ���ȡ����Ϣ
     *
     * @return String ���͵���Ϣ
     */
    public String getAlreadyMessage() {
        return AlreadyMessage;
    }

    /**
     * ���ý����Ѿ���ȡ����Ϣ
     *
     * @param alreadyMessage ��Ϣ����
     */
    public void setAlreadyMessage(String alreadyMessage) {
        AlreadyMessage = alreadyMessage;
    }

    /**
     * ��ȡ��Ҫִ�е������б�
     *
     * @return List<String> ����
     */
    public List<String> getCommands() {
        return commands;
    }

    /**
     * ������Ҫִ�е������б�
     *
     * @param commands ָ���б�
     */
    public void setCommands(List<String> commands) {
        this.commands = commands;
    }
}
