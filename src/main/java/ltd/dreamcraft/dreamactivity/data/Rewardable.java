package ltd.dreamcraft.dreamactivity.data;

import java.util.List;

public interface Rewardable {
    // ��ȡ�ڵ���
    String getSectionName();

    // ��ȡ������Ҫ���ߵ�ʱ��(��λ:����)
    int getValue();

    // ��ȡ�ɹ���ȡ��������Ϣ
    String getSuccessMessage();

    // ��ȡ������ȡʧ�ܵ���Ϣ
    String getFailedMessage();

    // ��ȡ�����Ѿ���ȡ����Ϣ
    String getAlreadyMessage();

    // ��ȡ��Ҫִ�е������б�
    List<String> getCommands();

    // ���ýڵ���
    void setSectionName(String sectionName);

    // ���ý�����Ҫ���ߵ�ʱ��(��λ:����)
    void setValue(int value);

    // ���óɹ���ȡ��������Ϣ
    void setSuccessMessage(String successMessage);

    // ���ý�����ȡʧ�ܵ���Ϣ
    void setFailedMessage(String failedMessage);

    // ���ý����Ѿ���ȡ����Ϣ
    void setAlreadyMessage(String alreadyMessage);

    // ������Ҫִ�е������б�
    void setCommands(List<String> commands);
}
