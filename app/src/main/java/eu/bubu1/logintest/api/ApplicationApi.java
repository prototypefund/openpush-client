package eu.bubu1.logintest.api;

import java.util.List;

import eu.bubu1.logintest.apimodels.Application;
import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.*;

public interface ApplicationApi {
  /**
   * Get a list of all registered applications.
   * 
   * @return Observable&lt;List&lt;Application&gt;&gt;
   */
  @GET("application")
  Observable<List<Application>> applicationGet();
    

  /**
   * Delete an application.
   * 
   * @param id The id of the app to delete. (required)
   * @return Completable
   */
  @DELETE("application/{id}")
  Completable applicationIdDelete(
          @retrofit2.http.Path("id") String id
  );

  /**
   * Register a new application.
   *
   * @param application Application to add (required)
   * @return Observable&lt;Application&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("application")
  Observable<Application> applicationPost(
          @retrofit2.http.Body Application application
  );

}
