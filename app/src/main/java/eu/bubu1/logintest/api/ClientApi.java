package eu.bubu1.logintest.api;

import java.util.List;

import eu.bubu1.logintest.apimodels.Client;
import eu.bubu1.logintest.apimodels.ClientRegistration;
import io.reactivex.Completable;
import io.reactivex.Observable;
import retrofit2.http.*;

public interface ClientApi {
  /**
   * Return all clients.
   * 
   * @return Observable&lt;List&lt;Client&gt;&gt;
   */
  @GET("client")
  Observable<List<Client>> clientGet();
    

  /**
   * Delete a client.
   * 
   * @param id The id of the client to delete (required)
   * @return Completable
   */
  @DELETE("client/{id}")
  Completable clientIdDelete(
          @retrofit2.http.Path("id") String id
  );

  /**
   * Update a client.
   *
   * @param id The id of the client to update (required)
   * @param client Updated client details. (required)
   * @return Observable&lt;Client&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @PUT("client/{id}")
  Observable<Client> clientIdPut(
          @retrofit2.http.Path("id") String id, @retrofit2.http.Body Client client
  );

  /**
   * Create a new client.
   *
   * @param clientRegistration Client to add (required)
   * @return Observable&lt;Client&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("client")
  Observable<Client> clientPost(
          @retrofit2.http.Body ClientRegistration clientRegistration
  );

}
