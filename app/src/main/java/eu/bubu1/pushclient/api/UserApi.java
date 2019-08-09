package eu.bubu1.pushclient.api;


import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;

import eu.bubu1.pushclient.apimodels.Error;
import eu.bubu1.pushclient.apimodels.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @retrofit2.http.Path("id") String id, @retrofit2.http.Body User user
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
    @retrofit2.http.Path("id") String id
  );

  /**
   * Get a user.
   * 
   * @param id The id of the user to retrieve (required)
   * @return Call&lt;User&gt;
   */
  @GET("user/{id}")
  Call<User> userIdGet(
    @retrofit2.http.Path("id") String id
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
    @retrofit2.http.Body User user
  );

}
