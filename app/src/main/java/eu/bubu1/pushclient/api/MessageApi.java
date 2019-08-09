package eu.bubu1.pushclient.api;


import retrofit2.Call;
import retrofit2.http.*;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.MultipartBody;

import eu.bubu1.pushclient.apimodels.Error;
import eu.bubu1.pushclient.apimodels.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @retrofit2.http.Body Message message
  );

}
