package com.zero.oauth.client.http;

import java.io.InputStream;
import java.net.Proxy;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.properties.HeaderProperty;
import com.zero.oauth.core.type.HttpMethod;
import com.zero.oauth.core.utils.Strings;

public class JdkHttpClientTest extends TestBase {

    private static final String POSTMAN_GET = "https://postman-echo.com/get";
    private static final String POSTMAN_POST = "https://postman-echo.com/post";
    private static final String POSTMAN_PUT = "https://postman-echo.com/put";
    private static final String POSTMAN_PATCH = "https://postman-echo.com/patch";
    private static final String POSTMAN_DELETE = "https://postman-echo.com/delete";
    private JdkHttpClient httpClient;

    @Before
    public void setUp() {
        httpClient = new JdkHttpClient(
            new JdkHttpClientConfig().addConfig(JdkHttpClientConfig.USER_AGENT, "JdkHttpClient")
                                     .addConfig(JdkHttpClientConfig.PROXY, Proxy.NO_PROXY));
    }

    @Test
    public void test_get() throws JSONException {
        HttpData response = httpClient.execute(POSTMAN_GET, HttpMethod.GET,
                                               HttpData.builder().query("foo1=bar1&foo2=bar2").build());
        Assert.assertTrue(response.getStatus().isOk());
        Assert.assertEquals("OK", response.getStatus().getMessage());
        String responseData = Strings.requireNotBlank(Strings.convertToString(response.getStreamBody()));
        JSONAssert.assertEquals(
            "{\"args\":{\"foo1\":\"bar1\",\"foo2\":\"bar2\"},\"headers\":{\"host\":\"postman-echo.com\"," +
            "\"accept\":\"text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\"," +
            "\"user-agent\":\"JdkHttpClient\",\"x-forwarded-port\":\"443\",\"x-forwarded-proto\":\"https\"}," +
            "\"url\":\"https://postman-echo.com/get?foo1=bar1&foo2=bar2\"}", responseData,
            JSONCompareMode.NON_EXTENSIBLE);
        Assert.assertFalse(response.getHeaderMap().isEmpty());
        Assert.assertEquals("application/json; charset=utf-8",
                            response.getHeaderMap().get(HeaderProperty.CONTENT_TYPE.getName()));
    }

    @Test
    public void test_post_json() throws JSONException {
        HttpData requestData = HttpData.builder().query("foo1=bar1&foo2=bar2").strBody("{\"hey\": \"json\"}").addHeader(
            "Content-Type", "application/json").build();
        HttpData response = httpClient.execute(POSTMAN_POST, HttpMethod.POST, requestData);
        Assert.assertTrue(response.getStatus().isOk());
        Assert.assertEquals("OK", response.getStatus().getMessage());
        String responseData = Strings.requireNotBlank(Strings.convertToString(response.getStreamBody()));
        System.out.println(responseData);
        JSONAssert.assertEquals("{\"args\":{\"foo1\":\"bar1\",\"foo2\":\"bar2\"}," +
                                "\"data\":{\"hey\":\"json\"},\"files\":{},\"form\":{}," +
                                "\"headers\":{\"host\":\"postman-echo.com\",\"content-length\":\"15\"," +
                                "\"accept\":\"text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\"," +
                                "\"content-type\":\"application/json\",\"user-agent\":\"JdkHttpClient\"," +
                                "\"x-forwarded-port\":\"443\",\"x-forwarded-proto\":\"https\"}," +
                                "\"json\":{\"hey\":\"json\"},\"url\":\"https://postman-echo.com/post?foo1=bar1&foo2=bar2\"}",
                                responseData, JSONCompareMode.NON_EXTENSIBLE);
        Assert.assertFalse(response.getHeaderMap().isEmpty());
        Assert.assertEquals("application/json; charset=utf-8",
                            response.getHeaderMap().get(HeaderProperty.CONTENT_TYPE.getName()));
    }

    @Test
    public void test_post_form_urlencoded() throws JSONException {
        HttpData requestData = HttpData.builder().query("foo1=bar1&foo2=bar2").strBody(
            "Post data x-www-form-urlencoded").addHeader("Content-Type", "application/x-www-form-urlencoded").build();
        HttpData response = httpClient.execute(POSTMAN_POST, HttpMethod.POST, requestData);
        Assert.assertTrue(response.getStatus().isOk());
        Assert.assertEquals("OK", response.getStatus().getMessage());
        String responseData = Strings.requireNotBlank(Strings.convertToString(response.getStreamBody()));
        System.out.println(responseData);
        JSONAssert.assertEquals("{\"args\":{\"foo1\":\"bar1\",\"foo2\":\"bar2\"}," +
                                "\"data\":\"\",\"files\":{},\"form\":{\"Post data x-www-form-urlencoded\":\"\"}," +
                                "\"headers\":{\"host\":\"postman-echo.com\",\"content-length\":\"31\"," +
                                "\"accept\":\"text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\"," +
                                "\"content-type\":\"application/x-www-form-urlencoded\"," +
                                "\"user-agent\":\"JdkHttpClient\"," +
                                "\"x-forwarded-port\":\"443\",\"x-forwarded-proto\":\"https\"}," +
                                "\"json\":{\"Post data x-www-form-urlencoded\":\"\"}," +
                                "\"url\":\"https://postman-echo.com/post?foo1=bar1&foo2=bar2\"}", responseData,
                                JSONCompareMode.NON_EXTENSIBLE);
        Assert.assertFalse(response.getHeaderMap().isEmpty());
        Assert.assertEquals("application/json; charset=utf-8",
                            response.getHeaderMap().get(HeaderProperty.CONTENT_TYPE.getName()));
    }

    @Test
    public void test_put() throws JSONException {
        HttpData requestData = HttpData.builder().strBody("PUT").addHeader("Content-Type", "application/json").build();
        HttpData response = httpClient.execute(POSTMAN_PUT, HttpMethod.PUT, requestData);
        Assert.assertTrue(response.getStatus().isOk());
        Assert.assertEquals("OK", response.getStatus().getMessage());
        String responseData = Strings.requireNotBlank(Strings.convertToString(response.getStreamBody()));
        System.out.println(responseData);
        JSONAssert.assertEquals("{\"args\":{},\"data\":\"PUT\",\"files\":{},\"form\":{}," +
                                "\"headers\":{\"host\":\"postman-echo.com\",\"content-length\":\"3\"," +
                                "\"accept\":\"text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\"," +
                                "\"content-type\":\"application/json\",\"user-agent\":\"JdkHttpClient\"," +
                                "\"x-forwarded-port\":\"443\",\"x-forwarded-proto\":\"https\"}," +
                                "\"json\":null,\"url\":\"https://postman-echo.com/put\"}", responseData,
                                JSONCompareMode.NON_EXTENSIBLE);
        Assert.assertFalse(response.getHeaderMap().isEmpty());
        Assert.assertEquals("application/json; charset=utf-8",
                            response.getHeaderMap().get(HeaderProperty.CONTENT_TYPE.getName()));
    }

    @Test
    public void test_delete() throws JSONException {
        HttpData requestData = HttpData.builder().addHeader("Content-Type", "application/json").build();
        HttpData response = httpClient.execute(POSTMAN_DELETE, HttpMethod.DELETE, requestData);
        Assert.assertTrue(response.getStatus().isOk());
        Assert.assertEquals("OK", response.getStatus().getMessage());
        String responseData = Strings.requireNotBlank(Strings.convertToString(response.getStreamBody()));
        System.out.println(responseData);
        JSONAssert.assertEquals(
            "{\"args\":{},\"data\":{},\"files\":{},\"form\":{}," + "\"headers\":{\"host\":\"postman-echo.com\"," +
            "\"accept\":\"text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\"," +
            "\"content-type\":\"application/json\",\"user-agent\":\"JdkHttpClient\"," +
            "\"x-forwarded-port\":\"443\",\"x-forwarded-proto\":\"https\"}," +
            "\"json\":null,\"url\":\"https://postman-echo.com/delete\"}", responseData, JSONCompareMode.NON_EXTENSIBLE);
        Assert.assertFalse(response.getHeaderMap().isEmpty());
        Assert.assertEquals("application/json; charset=utf-8",
                            response.getHeaderMap().get(HeaderProperty.CONTENT_TYPE.getName()));
    }

    @Test
    public void test_patch() throws JSONException {
        HttpData requestData = HttpData.builder().strBody("PATCH").addHeader("Content-Type", "application/json")
                                       .build();
        HttpData response = httpClient.execute(POSTMAN_PATCH, HttpMethod.PATCH, requestData);
        Assert.assertTrue(response.getStatus().isOk());
        Assert.assertEquals("OK", response.getStatus().getMessage());
        String responseData = Strings.requireNotBlank(Strings.convertToString(response.getStreamBody()));
        System.out.println(responseData);
        JSONAssert.assertEquals("{\"args\":{},\"data\":\"PATCH\",\"files\":{},\"form\":{}," +
                                "\"headers\":{\"host\":\"postman-echo.com\",\"content-length\":\"5\"," +
                                "\"accept\":\"text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\"," +
                                "\"content-type\":\"application/json\",\"user-agent\":\"JdkHttpClient\"," +
                                "\"x-forwarded-port\":\"443\",\"x-forwarded-proto\":\"https\"}," +
                                "\"json\":null,\"url\":\"https://postman-echo.com/patch\"}", responseData,
                                JSONCompareMode.NON_EXTENSIBLE);
        Assert.assertFalse(response.getHeaderMap().isEmpty());
        Assert.assertEquals("application/json; charset=utf-8",
                            response.getHeaderMap().get(HeaderProperty.CONTENT_TYPE.getName()));
    }

    @Test
    public void test_error() throws JSONException {
        HttpData requestData = HttpData.builder().addHeader("Content-Type", "application/json").build();
        HttpData response = httpClient.execute("https://postman-echo.com/status/404", HttpMethod.GET, requestData);
        Assert.assertTrue(response.getStatus().isError());
        Assert.assertEquals("Not Found", response.getStatus().getMessage());
        String responseData = Strings.requireNotBlank(Strings.convertToString(response.getStreamBody()));
        System.out.println(responseData);
        JSONAssert.assertEquals("{\"status\":404}", responseData, JSONCompareMode.NON_EXTENSIBLE);
        Assert.assertFalse(response.getHeaderMap().isEmpty());
        Assert.assertEquals("application/json; charset=utf-8",
                            response.getHeaderMap().get(HeaderProperty.CONTENT_TYPE.getName()));
    }

    @Test
    public void test_upload_file() throws JSONException {
        InputStream is = JdkHttpClientTest.class.getClassLoader().getResourceAsStream("hello.txt");
        MultipartData multipartData = new MultipartData().addFile("file", "hello.txt", is).addData("user", "test");
        multipartData.setBoundary("--------------------------908611062647668001736400");
        HttpData requestData = HttpData.builder().multipartData(multipartData).build();
        HttpData response = httpClient.execute(POSTMAN_POST, HttpMethod.POST, requestData);
        String responseData = Strings.requireNotBlank(Strings.convertToString(response.getStreamBody()));
        System.out.println(responseData);
        Assert.assertTrue(response.getStatus().isOk());
        Assert.assertEquals("OK", response.getStatus().getMessage());
        JSONAssert.assertEquals(
            "{\"args\":{},\"data\":{},\"files\":{\"hello.txt\":\"data:application/octet-stream;base64,aGVsbG8NCg==\"}," +
            "\"form\":{\"user\":\"test\"},\"headers\":{\"host\":\"postman-echo.com\",\"content-length\":\"320\"," +
            "\"accept\":\"text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\"," +
            "\"content-type\":\"multipart/form-data; boundary=--------------------------908611062647668001736400\"," +
            "\"user-agent\":\"JdkHttpClient\",\"x-forwarded-port\":\"443\",\"x-forwarded-proto\":\"https\"}," +
            "\"json\":null,\"url\":\"https://postman-echo.com/post\"}", responseData, JSONCompareMode.NON_EXTENSIBLE);
        Assert.assertFalse(response.getHeaderMap().isEmpty());
        Assert.assertEquals("application/json; charset=utf-8",
                            response.getHeaderMap().get(HeaderProperty.CONTENT_TYPE.getName()));
    }

}
