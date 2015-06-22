package cat.lafosca.smartcitizen.rest;

import com.google.gson.Gson;

import cat.lafosca.smartcitizen.BuildConfig;
import cat.lafosca.smartcitizen.commons.Constants;
import cat.lafosca.smartcitizen.controllers.SharedPreferencesController;
import cat.lafosca.smartcitizen.rest.api.AuthRestClient;
import cat.lafosca.smartcitizen.rest.api.RestClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
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
        //todo if user is not loged in?
        final String acces_token = SharedPreferencesController.getInstance().getUserToken();
        baseBuilder.setRequestInterceptor(new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Content-Type", "application/json");
                request.addHeader("Authorization", "Bearer "+acces_token);
            }
        });

        mAuthRestClient = baseBuilder.build().create(AuthRestClient.class);
    }

    private RestAdapter.Builder getBaseRestBuilder() {

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

        return builder;
    }

    public RestClient getRestClient() {
        return mRestClient;
    }

    public AuthRestClient getAuthRestClient() {
        return mAuthRestClient;
    }

}
