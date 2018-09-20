package com.zero.oauth.client.http;

import java.io.InputStream;
import java.net.ConnectException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import com.zero.oauth.core.TestBase;
import com.zero.oauth.core.exceptions.OAuthHttpException;
import com.zero.oauth.core.properties.HeaderProperty;
import com.zero.oauth.core.type.HttpMethod;
import com.zero.oauth.core.utils.Strings;

public class JdkHttpClientTest extends TestBase {

    private static final String POSTMAN_DELAY_3 = "https://postman-echo.com/delay/3";
    private static final String POSTMAN_200 = "https://postman-echo.com/status/200";
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
    public void testDefaultConfig() {
        HttpClientConfig defaultCfg = HttpClientConfig.defaultConfig();
        Assert.assertNull(defaultCfg.getUserAgent());
        Assert.assertNull(defaultCfg.getProxy());
        Assert.assertTrue(defaultCfg.isFollowRedirect());
        Assert.assertEquals(60, defaultCfg.getConnectTimeout());
        Assert.assertEquals(0, defaultCfg.getReadTimeout());
        try {
            defaultCfg.addConfig("", "");
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof UnsupportedOperationException);
        }
        try {
            defaultCfg.getConfig("");
        } catch (Exception ex) {
            Assert.assertTrue(ex instanceof UnsupportedOperationException);
        }
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
            "{\"args\":{},\"data\":{},\"files\":{\"hello.txt\":\"data:application/octet-stream;base64,aGVsbG8K\"}," +
            "\"form\":{\"user\":\"test\"},\"headers\":{\"host\":\"postman-echo.com\",\"content-length\":\"319\"," +
            "\"accept\":\"text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\"," +
            "\"content-type\":\"multipart/form-data; boundary=--------------------------908611062647668001736400\"," +
            "\"user-agent\":\"JdkHttpClient\",\"x-forwarded-port\":\"443\",\"x-forwarded-proto\":\"https\"}," +
            "\"json\":null,\"url\":\"https://postman-echo.com/post\"}", responseData, JSONCompareMode.NON_EXTENSIBLE);
        Assert.assertFalse(response.getHeaderMap().isEmpty());
        Assert.assertEquals("application/json; charset=utf-8",
                            response.getHeaderMap().get(HeaderProperty.CONTENT_TYPE.getName()));
    }

    @Test(expected = UnknownHostException.class)
    public void test_unknown_host() throws Throwable {
        try {
            httpClient.execute("http://xxx", HttpMethod.GET, HttpData.builder().build());
        } catch (OAuthHttpException e) {
            throw e.getCause();
        }
    }

    @Test(expected = ConnectException.class)
    public void test_host_not_existed() throws Throwable {
        try {
            httpClient.execute("http://127.0.0.1:9999", HttpMethod.GET, HttpData.builder().build());
        } catch (OAuthHttpException e) {
            throw e.getCause();
        }
    }

    @Test(expected = SocketTimeoutException.class)
    public void test_read_timeout() throws Throwable {
        try {
            httpClient.getConfig().addConfig(JdkHttpClientConfig.READ_TIMEOUT, 1);
            httpClient.execute(POSTMAN_DELAY_3, HttpMethod.GET, HttpData.builder().build());
        } catch (OAuthHttpException e) {
            throw e.getCause();
        }
    }

    @Test
    public void test_asyncFuture() throws InterruptedException, ExecutionException {
        Future<HttpData> future = httpClient.asyncExecute(POSTMAN_200, HttpMethod.GET, HttpData.builder().build());
        waitToDone(future);
        HttpData httpData = future.get();
        Assert.assertTrue(httpData.isResponse());
        Assert.assertTrue(httpData.getStatus().isOk());
        Assert.assertEquals(200, httpData.getStatus().getCode());
    }

    @Test(expected = ConnectException.class)
    public void test_asyncFuture_Exception() throws Throwable {
        Future<HttpData> future = httpClient.asyncExecute("http://127.0.0.1:9999", HttpMethod.GET,
                                                          HttpData.builder().build());
        waitToDone(future);
        try {
            future.get();
        } catch (ExecutionException ex) {
            if (ex.getCause() instanceof OAuthHttpException) {
                throw ex.getCause().getCause();
            }
            throw ex;
        }
    }

    @Test
    public void test_asyncCallback() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        httpClient.asyncExecute(POSTMAN_200, HttpMethod.GET, HttpData.builder().build(), initCallback(lock, null));
        Assert.assertTrue(lock.await(10, TimeUnit.SECONDS));
    }

    @Test
    public void test_asyncCallback_TimeoutException() throws InterruptedException {
        CountDownLatch lock = new CountDownLatch(1);
        httpClient.getConfig().addConfig(JdkHttpClientConfig.READ_TIMEOUT, 1);
        httpClient.asyncExecute(POSTMAN_DELAY_3, HttpMethod.GET, HttpData.builder().build(),
                                initCallback(lock, SocketTimeoutException.class));
        Assert.assertTrue(lock.await(5, TimeUnit.SECONDS));
    }

    @Test
    public void test_asyncCompletable() throws InterruptedException, ExecutionException {
        CompletableFuture<HttpData> future = httpClient.asyncExecute(POSTMAN_200, HttpMethod.GET,
                                                                     HttpData.builder().build(), true);
        waitToDone(future);
        HttpData httpData = future.get();
        Assert.assertTrue(httpData.isResponse());
        Assert.assertTrue(httpData.getStatus().isOk());
        Assert.assertEquals(200, httpData.getStatus().getCode());
    }

    @Test
    public void test_asyncCompletable_ClientHandleException() throws Throwable {
        CompletableFuture<HttpData> future = httpClient.asyncExecute("http://xxx", HttpMethod.GET,
                                                                     HttpData.builder().build(), true);
        waitToDone(future);
        Assert.assertTrue(future.isDone());
        Assert.assertFalse(future.isCompletedExceptionally());
        future.thenAccept(Assert::assertNull);
        Assert.assertNull(future.get());
    }

    @Test
    public void test_asyncCompletable_ManualHandleException() throws Throwable {
        CompletableFuture<HttpData> future = httpClient.asyncExecute("http://xxx", HttpMethod.GET,
                                                                     HttpData.builder().build(), false);
        waitToDone(future);
        Assert.assertTrue(future.isDone());
        Assert.assertTrue(future.isCompletedExceptionally());
        future.exceptionally(throwable -> {
            Assert.assertTrue(throwable.getCause() instanceof OAuthHttpException);
            return null;
        });
    }

    private void waitToDone(Future<HttpData> future) throws InterruptedException {
        while (!future.isDone()) {
            System.out.println("Task is still not done...");
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }

    private HttpCallback initCallback(CountDownLatch lock, Class<? extends Exception> exceptionClass) {
        return new HttpCallback() {
            @Override
            public void onSuccess(HttpData response) {
                System.out.println("Callback success");
                Assert.assertTrue(response.isResponse());
                Assert.assertTrue(response.getStatus().isOk());
                lock.countDown();
            }

            @Override
            public void onFailed(OAuthHttpException exception) {
                System.out.println("Callback failed");
                Assert.assertEquals(exceptionClass, exception.getCause().getClass());
                lock.countDown();
            }
        };
    }

}
