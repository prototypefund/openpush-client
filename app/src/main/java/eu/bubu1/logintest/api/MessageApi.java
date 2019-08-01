package eu.bubu1.logintest.api;

import eu.bubu1.logintest.apimodels.Message;
import io.reactivex.Completable;
import retrofit2.http.*;

public interface MessageApi {
  /**
   * Subscribe to messages delivered via SSE (Server Sent Events) here
   * 
   * @return Completable
   */
  @GET("subscribe")
  Completable apiMessageSubscribe();
    

  /**
   * Post a message.
   * 
   * @param message Message to post. (required)
   * @return Completable
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("message")
  Completable messagePost(
          @retrofit2.http.Body Message message
  );

}
