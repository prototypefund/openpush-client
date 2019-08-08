package eu.bubu1.pushclient.api;

import java.util.List;

import eu.bubu1.pushclient.apimodels.Client;
import eu.bubu1.pushclient.apimodels.ClientRegistration;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

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
          @Path("id") String id
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
          @Path("id") String id, @Body Client client
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
          @Body ClientRegistration clientRegistration
  );

}
