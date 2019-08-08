package eu.bubu1.pushclient.api;

import java.util.List;

import eu.bubu1.pushclient.apimodels.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {
  /**
   * Update a user.
   * 
   * @param id The id of the user to update (required)
   * @param user Updated user details. (required)
   * @return Call&lt;User&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("user/{id}")
  Call<User> apiUserUpdate(
          @Path("id") String id, @Body User user
  );

  /**
   * Return all users.
   *
   * @return Call&lt;List&lt;User&gt;&gt;
   */
  @GET("user")
  Call<List<User>> userGet();


  /**
   * Delete a user.
   *
   * @param id The id of the user to delete. (required)
   * @return Call&lt;Void&gt;
   */
  @DELETE("user/{id}")
  Call<Void> userIdDelete(
          @Path("id") String id
  );

  /**
   * Get a user.
   *
   * @param id The id of the user to retrieve (required)
   * @return Call&lt;User&gt;
   */
  @GET("user/{id}")
  Call<User> userIdGet(
          @Path("id") String id
  );

  /**
   * Add a user.
   *
   * @param user the user to add (required)
   * @return Call&lt;User&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("user")
  Call<User> userPost(
          @Body User user
  );

}
