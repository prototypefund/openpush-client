package eu.bubu1.pushclient.api;


import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;

import eu.bubu1.pushclient.apimodels.Client;
import eu.bubu1.pushclient.apimodels.ClientRegistration;
import eu.bubu1.pushclient.apimodels.Error;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ClientApi {
  /**
   * Delete current client.
   * 
   * @return Call&lt;Void&gt;
   */
  @DELETE("client")
  Call<Void> apiClientDeleteSelf();
    

  /**
   * Return all clients.
   * 
   * @return Call&lt;List&lt;Client&gt;&gt;
   */
  @GET("client")
  Call<List<Client>> clientGet();
    

  /**
   * Delete a client.
   * 
   * @param id The id of the client to delete (required)
   * @return Call&lt;Void&gt;
   */
  @DELETE("client/{id}")
  Call<Void> clientIdDelete(
    @retrofit2.http.Path("id") String id
  );

  /**
   * Update a client.
   * 
   * @param id The id of the client to update (required)
   * @param client Updated client details. (required)
   * @return Call&lt;Client&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("client/{id}")
  Call<Client> clientIdPut(
    @retrofit2.http.Path("id") String id, @retrofit2.http.Body Client client
  );

  /**
   * Create a new client.
   * 
   * @param clientRegistration Client to add (required)
   * @return Call&lt;Client&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("client")
  Call<Client> clientPost(
    @retrofit2.http.Body ClientRegistration clientRegistration
  );

}
