package eu.bubu1.pushclient.api;


import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;

import eu.bubu1.pushclient.apimodels.AppRegistration;
import eu.bubu1.pushclient.apimodels.Application;
import eu.bubu1.pushclient.apimodels.Error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ApplicationApi {
  /**
   * Get a list of all registered applications.
   * 
   * @return Call&lt;List&lt;Application&gt;&gt;
   */
  @GET("application")
  Call<List<Application>> applicationGet();
    

  /**
   * Delete an application.
   * 
   * @param id The id of the app to delete. (required)
   * @return Call&lt;Void&gt;
   */
  @DELETE("application/{id}")
  Call<Void> applicationIdDelete(
    @retrofit2.http.Path("id") String id
  );

  /**
   * Register a new application.
   * 
   * @param appRegistration Application to add (required)
   * @return Call&lt;Application&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("application")
  Call<Application> applicationPost(
    @retrofit2.http.Body AppRegistration appRegistration
  );

}
