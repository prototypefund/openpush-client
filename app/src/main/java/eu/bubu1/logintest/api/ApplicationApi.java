package eu.bubu1.logintest.api;

import java.util.List;

import eu.bubu1.logintest.apimodels.AppRegistration;
import eu.bubu1.logintest.apimodels.Application;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
          @Path("id") String id
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
          @Body AppRegistration appRegistration
  );

}
