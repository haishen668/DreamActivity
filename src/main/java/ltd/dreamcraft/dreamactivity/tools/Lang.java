package ltd.dreamcraft.dreamactivity.tools;

import java.util.ArrayList;
import java.util.List;

public class Lang {
    /**
     * ����һ��ǰ׺Ϊ[DreamAuthMe] [ERROR]Ϊǰ׺����Ϣ
     *
     * @param s �������Ϣ
     * @return ���ش��д���ǰ׺����Ϣ�����ҽ����滻Ϊ&
     */
    public static String error(String s) {
        String error = "��f[��eDreamAuthMe��f] ��f[��cERROR��f] ��f- ��7" + s;
        return error.replaceAll("&", "��");
    }

    /**
     * ����һ��ǰ׺Ϊ[DreamAuthMe] [SUCCESS]Ϊǰ׺����Ϣ
     *
     * @param s �������Ϣ
     * @return ���ش��гɹ�ǰ׺����Ϣ�����ҽ����滻Ϊ&
     */
    public static String success(String s) {
        String success = "��f[��eDreamAuthMe��f] ��f[��aSUCCESS��f] ��f- ��7" + s;
        return success.replaceAll("&", "��");
    }

    /**
     * ����һ��ǰ׺Ϊ[DreamAuthMe] [WARN]Ϊǰ׺����Ϣ
     *
     * @param s �������Ϣ
     * @return ���ش��о���ǰ׺����Ϣ�����ҽ����滻Ϊ&
     */
    public static String warn(String s) {
        String warn = "��f[��eDreamAuthMe��f] ��f[��eWARN��f] ��f- ��7" + s;
        return warn.replaceAll("&", "��");
    }

    public static String prefix(String s) {
        return "��f[��eDreamAuthMe��f] " + s.replaceAll("&", "��");
    }

    public static String ChatColor(String message) {
        return message.replaceAll("&", "��");
    }

    public static List<String> ChatColor(List<String> message) {
        List<String> list = new ArrayList();

        for (String s : message) {
            list.add(s.replaceAll("&", "��"));
        }

        return list;
    }
}
