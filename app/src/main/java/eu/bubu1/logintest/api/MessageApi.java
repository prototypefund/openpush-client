package eu.bubu1.logintest.api;

import eu.bubu1.logintest.apimodels.Message;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MessageApi {
  /**
   * Subscribe to messages delivered via SSE (Server Sent Events) here
   * 
   * @return Call&lt;Void&gt;
   */
  @GET("subscribe")
  Call<Void> apiMessageSubscribe();
    

  /**
   * Post a message.
   * 
   * @param message Message to post. (required)
   * @return Call&lt;Void&gt;
   */
  @Headers({
    "Content-Type:application/json"
  })
  @POST("message")
  Call<Void> messagePost(
          @Body Message message
  );

}
