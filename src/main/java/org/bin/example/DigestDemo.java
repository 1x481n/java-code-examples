package org.bin.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cdimascio.dotenv.Dotenv;
import org.apache.commons.codec.digest.DigestUtils;
import org.bin.example.utils.CommonUtils;

import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;


/**
 * 消息摘要demo
 *
 * @author 1x481n
 */
public class DigestDemo {

    private static final String OPEN_API_DOMAIN = "http://0.0.0.0:8080";

    private static final Dotenv DOT_ENV= Dotenv.load();

    private static final String APP_ID = DOT_ENV.get("QT_APP_ID");

    private static final String APP_SECRET = DOT_ENV.get("QT_APP_SECRET");


    public static void main(String[] args) throws IOException {
        Map<String, String> bizParams = mockBizParams();

        System.out.println("======== Request ========");

        StringBuilder assessUrl = new StringBuilder(OPEN_API_DOMAIN + "/api/open/oauth/access");
        assessUrl.append("?").append(buildQuery(bizParams));

        URL url = new URL(assessUrl.toString());
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        System.out.println("GET " + assessUrl);

        System.out.println("======== Response ========");

        int httpStatusCode = con.getResponseCode();
        String responseMessage = con.getResponseMessage();
        String contentType = con.getContentType();
        System.out.println(httpStatusCode + " " + responseMessage);
        System.out.println("Content-Type：" + contentType);

        Reader streamReader;
        if (httpStatusCode >= HttpURLConnection.HTTP_MULT_CHOICE) {
            streamReader = new InputStreamReader(con.getErrorStream());
        } else {
            streamReader = new InputStreamReader(con.getInputStream());
        }

        BufferedReader in = new BufferedReader(streamReader);
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        con.disconnect();

        parseResponseContent(contentType, content);

    }

    /**
     * 模拟业务参数
     *
     * @return 业务参数Map
     */
    public static Map<String, String> mockBizParams() {
        Map<String, String> bizParams = new HashMap<>();
        bizParams.put("username", "1x481n");
        bizParams.put("realname", "1x481n");
        bizParams.put("email", "1x481n@gmail.com");

        System.out.println("bizParams：" + bizParams);

        return bizParams;
    }

    /**
     * 按规则构建请求参数
     *
     * @param bizParams 业务参数Map
     * @return 请求参数字符串
     */
    public static String buildQuery(Map<String, String> bizParams) {

        Long timestamp = System.currentTimeMillis() / 1000;

        Map<String, String> queryParams = new HashMap<>(bizParams);

        boolean first = true;
        StringBuilder queryStringBuilder = new StringBuilder("");
        for (Map.Entry<String, String> bizParam : queryParams.entrySet()) {
            queryStringBuilder.append(first ? "" : "&").append(bizParam.getKey()).append("=").append(bizParam.getValue());
            first = false;
        }

        queryStringBuilder.append("&app_id=" + APP_ID);
        queryStringBuilder.append("&timestamp=").append(timestamp);
        queryStringBuilder.append("&digest=").append(generateMsgDigest(bizParams, timestamp));

        return queryStringBuilder.toString();
    }

    /**
     * 生成消息摘要
     *
     * @param bizParams 业务参数
     * @param timestamp 秒级时间戳
     * @return 消息摘要字符串
     */
    public static String generateMsgDigest(Map<String, String> bizParams, Long timestamp) {
        String plantextStr = APP_ID;
        String sha1Str = APP_SECRET;

        // 转换为树形图，红黑树结构天然对key进行排序
        TreeMap<String, String> bizParamSorted = new TreeMap<>(bizParams);

        System.out.println("bizParamSorted：" + bizParamSorted);

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> bizParam : bizParamSorted.entrySet()) {
            if (!first) {
                sb.append(",");
            }
            sb.append(bizParam.getValue());
            first = false;
        }


        sb.append(",").append(timestamp);

        // 业务参数按key正序排序后,以英文逗号拼接value，time（秒）时间戳拼到最后
        String md5Str = sb.toString();

        System.out.printf("plantextStr=%s｜md5Str=%s｜sha1Str=%s%s", plantextStr, md5Str, sha1Str, CommonUtils.NEW_LINE);

        // 摘要生成公式
        String digest = Base64.getEncoder().encodeToString(
                DigestUtils.sha1Hex(
                        plantextStr + DigestUtils.md5Hex(md5Str).toUpperCase() + DigestUtils.sha1Hex(sha1Str).toUpperCase()
                ).toUpperCase().getBytes(StandardCharsets.UTF_8)
        );

        System.out.println("digest=" + digest);

        return digest;
    }


    /**
     * 解析相应内容
     *
     * @param contentType 相应内容类型
     * @param content     相应内容
     * @throws IOException 非受检的IO异常
     */
    public static void parseResponseContent(String contentType, StringBuilder content) throws IOException {
        if (contentType.contains("application/json")) {
            ObjectMapper objectMapper = new ObjectMapper();
            System.out.println(content + CommonUtils.NEW_LINE);
            System.out.println(objectMapper.readValue(content.toString(), Map.class));
        }

        if (contentType.contains("text/html")) {
            File htmFile = new File("tmp/access.html");
            BufferedWriter bw = new BufferedWriter(new FileWriter(htmFile));
            bw.write(content.toString());
            bw.close();
            //Desktop.getDesktop().browse(URI.create("http://www.google.com"));
            Desktop.getDesktop().browse(htmFile.toURI());
        }
    }
}
