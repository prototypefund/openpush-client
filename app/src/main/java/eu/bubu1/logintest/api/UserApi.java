package eu.bubu1.logintest.api;

import java.util.List;

import eu.bubu1.logintest.apimodels.User;
import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.*;

public interface UserApi {
  /**
   * Update a user.
   * 
   * @param id The id of the user to update (required)
   * @param user Updated user details. (required)
   * @return Observable&lt;User&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("user/{id}")
  Observable<User> apiUserUpdate(
          @retrofit2.http.Path("id") String id, @retrofit2.http.Body User user
  );

  /**
   * Return all users.
   *
   * @return Observable&lt;List&lt;User&gt;&gt;
   */
  @GET("user")
  Observable<List<User>> userGet();


  /**
   * Delete a user.
   *
   * @param id The id of the user to delete. (required)
   * @return Completable
   */
  @DELETE("user/{id}")
  Completable userIdDelete(
          @retrofit2.http.Path("id") String id
  );

  /**
   * Get a user.
   *
   * @param id The id of the user to retrieve (required)
   * @return Observable&lt;User&gt;
   */
  @GET("user/{id}")
  Observable<User> userIdGet(
          @retrofit2.http.Path("id") String id
  );

  /**
   * Add a user.
   *
   * @param user the user to add (required)
   * @return Observable&lt;User&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("user")
  Observable<User> userPost(
          @retrofit2.http.Body User user
  );

}
