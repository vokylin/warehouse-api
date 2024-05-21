package com.ruoyi.common.utils.http;


public class HttpUtil {

    public static final int DEFAULT_CONNECT_TIMEOUT = 30000;

    public static final int DEFAULT_SOCKET_TIMEOUT = 30000;

    /**
     * Post请求
     *
     * @param url  url
     * @param json json
     * @return {@link String}
     */
    public static String postJson(String url, String json) {
//        String result = null;
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        CloseableHttpResponse httpResponse = null;
//        HttpPost httpPost = new HttpPost(url);
//
//        try {
//            StringEntity stringEntity = new StringEntity(json, StandardCharsets.UTF_8);
//            stringEntity.setContentEncoding("UTF-8");
//            stringEntity.setContentType("application/json");
//            httpPost.setEntity(stringEntity);
//            // 设置请求和传输超时时间
//            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(DEFAULT_SOCKET_TIMEOUT)
//                    .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT).build();
//            httpPost.setConfig(requestConfig);
//            httpResponse = httpClient.execute(httpPost);
//            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                HttpEntity entity = httpResponse.getEntity();
//                result = EntityUtils.toString(entity, "utf-8");
//            }
//            return result;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (null != httpClient) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (null != httpResponse) {
//                try {
//                    httpResponse.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return null;
    }


    /**
     * Get请求
     *
     * @param url url
     * @return {@link String}
     */
    public static String executGet(String url) {
//        String result = null;
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        CloseableHttpResponse httpResponse = null;
//        try {
//            HttpGet httpGet = new HttpGet(url);
//            httpResponse = httpClient.execute(httpGet);
//            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//                HttpEntity entity = httpResponse.getEntity();
//                result = EntityUtils.toString(entity, StandardCharsets.UTF_8);
//            }
//            return result;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (null != httpClient) {
//                try {
//                    httpClient.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (null != httpResponse) {
//                try {
//                    httpResponse.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return null;
    }
}
