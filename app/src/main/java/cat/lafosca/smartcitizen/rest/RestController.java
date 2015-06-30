package cat.lafosca.smartcitizen.rest;

import com.google.gson.Gson;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;

import cat.lafosca.smartcitizen.BuildConfig;
import cat.lafosca.smartcitizen.commons.Constants;
import cat.lafosca.smartcitizen.commons.SmartCitizenApp;
import cat.lafosca.smartcitizen.managers.SharedPreferencesManager;
import cat.lafosca.smartcitizen.rest.api.AuthRestClient;
import cat.lafosca.smartcitizen.rest.api.RestClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Created by ferran on 03/06/15.
 */
public class RestController {

    private static RestController sInstance;

    public static RestController getInstance() {
        if (sInstance == null)
            sInstance = new RestController();

        return sInstance;
    }

    private RestController() {
        setUpRestController();
    }

    private RestClient mRestClient;
    private AuthRestClient mAuthRestClient;

    public void setUpRestController() {


        RestAdapter.Builder baseBuilder = getBaseRestBuilder();

        mRestClient = baseBuilder.build().create(RestClient.class);

        //authentication
        final String acces_token = SharedPreferencesManager.getInstance().getUserToken();
        baseBuilder = createAuthRestBuilder(acces_token, baseBuilder);

        mAuthRestClient = baseBuilder.build().create(AuthRestClient.class);
    }

    private RestAdapter.Builder createAuthRestBuilder(final String accessToken, RestAdapter.Builder baseBuilder) {
        baseBuilder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Accept", "application/vnd.smartcitizen; version=0,application/json");
                request.addHeader("Authorization", "Bearer "+accessToken);
            }
        });

        return baseBuilder;
    }

    public void updateAuthRestController(String accessToken) {
        RestAdapter.Builder builder = getBaseRestBuilder();

        builder = createAuthRestBuilder(accessToken, builder);
        mAuthRestClient = builder.build().create(AuthRestClient.class);
    }

    private RestAdapter.Builder getBaseRestBuilder() {

        RestAdapter.LogLevel logLevel = (BuildConfig.DEBUG) ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE;

        File cacheDir = SmartCitizenApp.getInstance().getCacheDir();
        Cache cache = new Cache(cacheDir, 1024 * 1024); // 1 MiB
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setCache(cache);

        RestAdapter.Builder builder =
                new RestAdapter.Builder()
                .setEndpoint(Constants.URL_BASE)
                        .setConverter(new GsonConverter(new Gson()))
                        .setClient(new OkClient(okHttpClient))
                        .setLogLevel(logLevel);
        /*builder.setErrorHandler(new ErrorHandler() {
            @Override
            public Throwable handleError(RetrofitError cause) {
                if (cause.getResponse() != null && cause.getResponse().getStatus() == 401) {
                }
                return DEFAULT.handleError(cause);
            }
        });*/
        builder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Accept", "application/vnd.smartcitizen; version=0,application/json");
            }
        });

        return builder;
    }

    public RestClient getRestClient() {
        return mRestClient;
    }

    public AuthRestClient getAuthRestClient() {
        return mAuthRestClient;
    }

}
