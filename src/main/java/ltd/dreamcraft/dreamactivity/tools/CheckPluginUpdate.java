package ltd.dreamcraft.dreamactivity.tools;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ltd.dreamcraft.dreamactivity.DreamActivity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.bukkit.Bukkit.getConsoleSender;

public class CheckPluginUpdate {
    /**
     * ������Ƿ���Ҫ���£�����API��ȡ���json����
     *
     * @param apiUrl API����
     * @return ������°汾�ͷ����ַ��� �����°汾&���ص�ַ�� û���°汾�ͷ���"NOTREQUIRED" �������󷵻�"ERROR"
     */
    public static String checkPluginUpdate(String apiUrl) {
        getConsoleSender().sendMessage("��b|> ��6���ڼ�����....");
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // ����API���ص�JSON����
            String apiResponse = response.toString();
            try {
                JsonParser parser = new JsonParser();
                JsonArray jsonArray = parser.parse(apiResponse).getAsJsonArray();

                // ����JSON���飬������Ϊ"DragonAuthMe"�Ĳ����Ϣ
                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject pluginInfo = jsonArray.get(i).getAsJsonObject();
                    String name = pluginInfo.get("name").getAsString();
                    if ("DreamActivity".equals(name)) {
                        String latestVersion = pluginInfo.get("version").getAsString(); // ��ȡ���°汾��
                        String downloadUrl = pluginInfo.get("url").getAsString(); // ��ȡ��������

                        // ����Ƿ����°汾����
                        if (isNeedUpdate(DreamActivity.in().getDescription().getVersion(), latestVersion)) {
                            // ���߿���̨����������°汾����Ҫ��ĸ���
                            getConsoleSender().sendMessage("��b|> ��c�����°汾��" + latestVersion);
                            getConsoleSender().sendMessage("��b|> ��c�°汾���ص�ַ��" + downloadUrl);
                            return latestVersion + "&" + downloadUrl;
                        } else {
                            getConsoleSender().sendMessage("��b|> ��aDreamActivity��e����Ѿ��ǡ�c���°汾��e������¡�");
                            // �������Ҫ���¾ͷ����ַ���
                            return "NOTREQUIRED";
                        }

                        // ֻ��Ҫ���һ����Ϊ"DragonAuthMe"�Ĳ����Ϣ������˳�ѭ��
                        // break;
                    }
                }
                return null;
            } catch (Exception e) {
                DreamActivity.in().getLogger().severe("����API���ص�JSON����ʱ��������" + e.getMessage());
                return "ERROR";
            }
        } catch (Exception e) {
            DreamActivity.in().getLogger().severe("����DreamActivity���ʱ��������" + e.getMessage());
            return "ERROR";
        }
    }

    /**
     * ������Ƿ���Ҫ����
     *
     * @param oldVersion �ɰ汾��
     * @param newVersion �°汾��
     * @return �����Ҫ���£����� true�����򷵻� false
     */
    public static boolean isNeedUpdate(String oldVersion, String newVersion) {
        // ���汾�Ű��յ�ŷָ�Ϊ���飬����������ֽ��бȽ�
        String[] version1Parts = oldVersion.split("\\.");
        String[] version2Parts = newVersion.split("\\.");

        // ��ȡ�汾�Ų��ֵ���󳤶ȣ���������Խ��
        int length = Math.max(version1Parts.length, version2Parts.length);

        // ������ֽ��бȽ�
        for (int i = 0; i < length; i++) {
            // ���汾�ŵ�ÿ������ת��Ϊ����
            int part1 = (i < version1Parts.length) ? Integer.parseInt(version1Parts[i]) : 0;
            int part2 = (i < version2Parts.length) ? Integer.parseInt(version2Parts[i]) : 0;

            // ����ɰ汾�ĵ�ǰ����С���°汾�ĵ�ǰ���֣����ʾ��Ҫ���£����� true
            if (part1 < part2) {
                return true;
            }
            // ����ɰ汾�ĵ�ǰ���ִ����°汾�ĵ�ǰ���֣����ʾ����Ҫ���£����� false
            else if (part1 > part2) {
                return false;
            }
        }

        // ����汾����ͬ������Ҫ���£����� false
        return false;
    }
}
