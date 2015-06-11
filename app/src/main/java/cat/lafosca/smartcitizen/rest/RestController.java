package cat.lafosca.smartcitizen.rest;

import com.google.gson.Gson;

import cat.lafosca.smartcitizen.BuildConfig;
import cat.lafosca.smartcitizen.commons.Constants;
import cat.lafosca.smartcitizen.rest.api.RestClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by ferran on 03/06/15.
 */
public class RestController {

    private static RestClient sRestClient;

    public static void init() {

        RestAdapter.LogLevel logLevel = (BuildConfig.DEBUG) ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE;
        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(Constants.URL_BASE);
        builder.setConverter(new GsonConverter(new Gson()));
        builder.setLogLevel(logLevel);
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
            }
        });
        sRestClient = builder.build().create(RestClient.class);
    }

    public static RestClient getRestClient() {
        return sRestClient;
    }

}
